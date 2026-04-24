package id.hyperionx.bridgefight.listeners;

//import java.net.http.WebSocket.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements org.bukkit.event.Listener {
    


@EventHandler
public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    player.getInventory().clear(); // Clear inventory on join

    ItemStack item = new ItemStack(Material.COMPASS);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName("§aSelect Mode");
    player.sendMessage("§aWelcome to BridgeFight!");
    item.setItemMeta(meta);

    player.getInventory().setItem(0,item);
}
}