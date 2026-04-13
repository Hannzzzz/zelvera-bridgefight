package id.hyperionx.bridgefight;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Arena {

    private final String name;
    private final List<Team> teams = new ArrayList<>();
    private final Set<UUID> players = new HashSet<>();
    private final Map<UUID, Team> playerTeams = new HashMap<>();
    private Location lobbyLocation;
    private int maxPlayers = 8;
    private int minPlayers = 2;
    private int countdown = 30;
    private final Main plugin;
    private final GameManager gameManager;
    private LobbyManager lobbyManager;

    private GameState state;
    public GameState getState1() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;  
    }

    public Arena(String name, Main plugin2) {
        this.name = name;
        this.plugin = null; // Will be set when created
        this.gameManager = null;
        loadFromConfig(plugin2);
    }

    private void loadFromConfig(Main plugin2) {
        throw new UnsupportedOperationException("Unimplemented method 'loadFromConfig'");
    }

    public Arena(String name, Main plugin, FileConfiguration config) {
        this.name = name;
        this.plugin = plugin;
        this.gameManager = null;
        loadFromConfig(config);
    }

    public Arena(String name, Main plugin, GameManager gameManager) {
        this.name = name;
        this.plugin = plugin;
        this.gameManager = gameManager;
        // Default teams
        teams.add(new Team("Red", ChatColor.RED, (byte)14));
        teams.add(new Team("Blue", ChatColor.BLUE, (byte)11));
        teams.add(new Team("Green", ChatColor.GREEN, (byte)5));
        teams.add(new Team("Yellow", ChatColor.YELLOW, (byte)4));
    }

    public Arena(String name, Main plugin, GameManager gameManager, FileConfiguration config) {
        this.name = name;
        this.plugin = plugin;
        this.gameManager = gameManager;
        loadFromConfig(config);
    }

    private void loadFromConfig(FileConfiguration config) {
        // Load locations, teams, etc.
        // Implementation omitted for brevity
        if (lobbyLocation != null) {
            this.lobbyManager = new LobbyManager(plugin, gameManager, lobbyLocation);
        }
    }

    public void save(File file) throws IOException {
        YamlConfiguration config = new YamlConfiguration();
        // Save locations, teams, etc.
        config.save(file);
    }

    public String getName() {
        return name;
    }

    public GameState getState() {
        return state;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player.getUniqueId());
    }

    public void addPlayer(Player player) {
        if (state != GameState.LOBBY) {
            player.sendMessage("§cGame is already in progress!");
            return;
        }

        if (players.size() >= maxPlayers) {
            player.sendMessage("§cArena is full!");
            return;
        }

        players.add(player.getUniqueId());
        assignTeam(player);

        player.sendMessage("§aJoined arena " + name + "!");

        if (players.size() >= minPlayers && countdown == 30) {
            startCountdown();
        }

        updateScoreboard();
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        Team team = playerTeams.remove(player.getUniqueId());
        if (team != null) {
            team.removePlayer(player);
        }

        player.teleport(lobbyLocation != null ? lobbyLocation : Bukkit.getWorlds().get(0).getSpawnLocation());
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        if (players.size() < minPlayers && state == GameState.STARTING_COUNTDOWN) {
            stopCountdown();
        }

        updateScoreboard();
    }

    private void assignTeam(Player player) {
        Team smallestTeam = teams.stream()
                .min(Comparator.comparingInt(Team::getSize))
                .orElse(teams.get(0));

        smallestTeam.addPlayer(player);
        playerTeams.put(player.getUniqueId(), smallestTeam);
    }

    private void startCountdown() {
        state = GameState.STARTING_COUNTDOWN;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (countdown <= 0) {
                    startGame();
                    cancel();
                    return;
                }

                if (players.size() < minPlayers) {
                    stopCountdown();
                    cancel();
                    return;
                }

                broadcast("§eGame starting in " + countdown + " seconds!");
                countdown--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void stopCountdown() {
        state = GameState.LOBBY;
        countdown = 30;
        broadcast("§cNot enough players! Countdown stopped.");
    }

    private void startGame() {
        state = GameState.IN_PROGRESS;
        broadcast("§aGame started! Good luck!");

        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                // Teleport to team spawn
                Team team = playerTeams.get(uuid);
                if (team != null && team.getSpawnLocation() != null) {
                    player.teleport(team.getSpawnLocation());
                }
                // Give kit
                giveKit(player);
            }
        }
    }

    public void stopGame() {
        state = GameState.LOBBY;
        countdown = 30;

        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                removePlayer(player);
            }
        }

        players.clear();
        playerTeams.clear();
    }

    private void giveKit(Player player) {
        player.getInventory().clear();
        // Basic kit: sword, pickaxe, blocks
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.WOODEN_SWORD));
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.WOODEN_PICKAXE));
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.WHITE_WOOL, 64));
        player.getInventory().setHelmet(new org.bukkit.inventory.ItemStack(Material.LEATHER_HELMET));
        player.getInventory().setChestplate(new org.bukkit.inventory.ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS));
        player.getInventory().setBoots(new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS));
    }

    private void broadcast(String message) {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(message);
            }
        }
    }

    private void updateScoreboard() {
        // Implement scoreboard update
    }

    // Getters and setters
    public List<Team> getTeams() {
        return teams;
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public void shutdown() {
        if (lobbyManager != null) {
            lobbyManager.destroy();
        }
    }
}
