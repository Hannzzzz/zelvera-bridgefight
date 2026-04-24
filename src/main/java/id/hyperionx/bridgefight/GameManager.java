package id.hyperionx.bridgefight;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameManager {

    private final Main plugin;
    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public GameManager(Main plugin) {
    this.plugin = plugin;

    FileConfiguration config = plugin.getConfig();

    ConfigurationSection arenasSection = config.getConfigurationSection("arenas");

    for (String arenaName : arenasSection.getKeys(false)) {
        Arena arena = new Arena(arenaName, plugin, this, arenasSection.getConfigurationSection(arenaName));

        arenas.put(arenaName, arena);
    }
}

    public void loadArenas() {
        File arenasFolder = new File(plugin.getDataFolder(), "arenas");
        if (!arenasFolder.exists()) {
            arenasFolder.mkdirs();
        }

        File[] files = arenasFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                String arenaName = file.getName().replace(".yml", "");
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                Arena arena = new Arena(arenaName, plugin, this, config);
                arenas.put(arenaName, arena);
            }
        }

        plugin.getLogger().info("Loaded " + arenas.size() + " arenas.");
    }

    public void saveArena(Arena arena) {
        File arenasFolder = new File(plugin.getDataFolder(), "arenas");
        File file = new File(arenasFolder, arena.getName() + ".yml");
        try {
            arena.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save arena " + arena.getName() + ": " + e.getMessage());
        }
    }

    public void tryStartGame(Arena arena) {
        if (arena.getPlayers().size() >= 2) {
            startCountdown(arena);
        }
    }

    public void startCountdown(Arena arena) {
        arena.setState(GameState.STARTING_COUNTDOWN);

        new BukkitRunnable() {
            int time = 10;
            public void run() {
                if (time == 0) {
                    startGame(arena);
                    cancel();
                    return;
                }

                for (UUID uuid : arena.getPlayers()) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null) {
                        p.sendMessage("Starting in " + time + " seconds...");
                    }
                }
                
                time--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void startGame(Arena arena) {
        arena.setState(GameState.IN_PROGRESS);
        // Teleport players, give kits, etc.
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                p.sendMessage("Game started!");
            }
        }
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }

    public void addArena(Arena arena) {
        arenas.put(arena.getName(), arena);
        saveArena(arena);
    }

    public void removeArena(String name) {
        arenas.remove(name);
        File file = new File(plugin.getDataFolder(), "arenas/" + name + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerData.computeIfAbsent(uuid, k -> new PlayerData(uuid));
    }

    public void shutdown() {
        for (Arena arena : arenas.values()) {
            arena.stopGame();
        }
    }

    public Arena findAvailableArena() {
        for (Arena arena : arenas.values()) {
            if (arena.getState() == GameState.LOBBY) {
                return arena;
            }
        }
        return null;
    }

    public void joinGame(Player player, String arenaName) {
        Arena arena = arenas.get(arenaName);
        if (arena == null) {
            player.sendMessage("§cArena not found!");
            return;
        }

        arena.addPlayer(player);
    }

    public void leaveGame(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.hasPlayer(player)) {
                arena.removePlayer(player);
                break;
            }
        }
    }
}