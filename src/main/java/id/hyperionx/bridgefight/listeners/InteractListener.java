package id.hyperionx.bridgefight.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import id.hyperionx.bridgefight.ArenaManager;

import org.bukkit.entity.Player;

public class InteractListener implements org.bukkit.event.Listener {

private final ArenaManager arenaManager;

public InteractListener(ArenaManager arenaManager) {
    this.arenaManager = arenaManager;
}

@EventHandler
public void onClick(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    if (e.getItem() == null) return;
    if (e.getItem().getType() == Material.COMPASS) {
        arenaManager.joinArena(player, "arena1");

        }
    if (e.getItem().getType() == Material.COMPASS) {
    e.setCancelled(true);
    arenaManager.joinArena(player, "arena1");
}
    }
}