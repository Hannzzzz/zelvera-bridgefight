package id.hyperionx.bridgewars;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // startup logic plugin
        getLogger().info("BridgeWars plugin enabled!");

        // initialize game manager
        gameManager = new GameManager(this);

        //  registers commands
        getCommand("bridgewars").setExecutor(new BridgeWarsCommand(this));
        getCommand("bwadmin").setExecutor(new AdminCommand(this));

        // registers events
        getServer().getPluginManager().registerEvents(new GameListener(this), this);

        // loads configurations
        saveDefaultConfig();
        gameManager.loadArenas();
    }

    @Override
    public void onDisable() {
        // plugin shutdown logic
        getLogger().info("BridgeWars plugin disabled!");

        if (gameManager != null) {
            gameManager.shutdown();
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}