package id.hyperionx.bridgefight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameManager {

    private final Main plugin;
    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public GameManager(Main plugin) {
        this.plugin = plugin;
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
                Arena arena = new Arena(arenaName, plugin, config);
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
            if (arena.getState() == GameState.WAITING) {
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