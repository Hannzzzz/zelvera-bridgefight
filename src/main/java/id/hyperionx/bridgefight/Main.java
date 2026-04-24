package id.hyperionx.bridgefight;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import id.hyperionx.bridgefight.listeners.GameListener;
import id.hyperionx.bridgefight.listeners.InteractListener;
import id.hyperionx.bridgefight.listeners.PlayerJoinListener;

public class Main extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // startup logic plugin
        getLogger().info("BridgeFight plugin enabled!");


        GameManager gameManager = new GameManager(this); // Initialize game manager
        ArenaManager arenaManager = new ArenaManager(gameManager);

        // registers commands
        getCommand("bridgefight").setExecutor(new BridgeFightCommand(this));
        getCommand("bfadmin").setExecutor(new AdminCommand(this));

        // registers events
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        // getServer().getPluginManager().registerEvents(new MenuListener(), this); // Uncomment when Citizens is installed

        // registers listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(arenaManager), this);

        // Register Citizens trait if available
         if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
             try {
                 Class.forName("net.citizensnpcs.api.CitizensAPI").getMethod("getTraitFactory").invoke(null);
             } catch (Exception e) {
                 getLogger().warning("Failed to register Citizens trait: " + e.getMessage());
             }
         }

        // loads configurations
        gameManager.loadArenas();
    }

    @Override
    public void onDisable() {
        // plugin shutdown logic
        getLogger().info("BridgeFight plugin disabled!");

        if (gameManager != null) {
            gameManager.shutdown();
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}