package id.hyperionx.bridgefight.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import id.hyperionx.bridgefight.Arena;
import id.hyperionx.bridgefight.Main;
import id.hyperionx.bridgefight.PlayerData;

public class GameListener implements Listener {

    private final Main plugin;

    public GameListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        // Check if player is in a game
        for (Arena arena : plugin.getGameManager().getArenas()) {
            if (arena.hasPlayer(player)) {
                // Handle death in game
                PlayerData data = plugin.getGameManager().getPlayerData(player.getUniqueId());
                data.addDeath();

                if (killer != null && arena.hasPlayer(killer)) {
                    PlayerData killerData = plugin.getGameManager().getPlayerData(killer.getUniqueId());
                    killerData.addKill();
                    killer.sendMessage("§aYou killed " + player.getName() + "!");
                }

                // Respawn logic
                event.setDeathMessage(null); // Remove default death message
                // Schedule respawn
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getGameManager().leaveGame(player);
    }
}