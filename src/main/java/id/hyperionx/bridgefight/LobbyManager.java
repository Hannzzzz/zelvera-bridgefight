package id.hyperionx.bridgefight;

// import com.gmail.filoghost.holographicdisplays.api.Hologram;
// import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
// import net.citizensnpcs.api.CitizensAPI;
// import net.citizensnpcs.api.npc.NPC;
// import net.citizensnpcs.api.trait.Trait;
// import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class LobbyManager {

    private final JavaPlugin plugin;
    private final GameManager gameManager;
    // private Hologram lobbyHologram;
    // private NPC modeSelectorNPC;
    // private NPC backToSpawnNPC;

    public LobbyManager(JavaPlugin plugin, GameManager gameManager, Location lobbyLocation) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        // setupHologram(lobbyLocation);
        // setupNPCs(lobbyLocation);
    }

    // private void setupHologram(Location location) {
    //     if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
    //         lobbyHologram = HologramsAPI.createHologram(plugin, location.clone().add(0, 2, 0));
    //         lobbyHologram.appendTextLine("§6BridgeFight Lobby");
    //         lobbyHologram.appendTextLine("§7Click NPCs to join modes!");
    //         lobbyHologram.appendTextLine("§eWelcome to fast-paced duels!");
    //     }
    // }

    // private void setupNPCs(Location location) {
    //     if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
    //         // Mode selector NPC
    //         modeSelectorNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Mode Selector");
    //         modeSelectorNPC.spawn(location.clone().add(2, 0, 0));
    //         modeSelectorNPC.addTrait(new ClickableTrait(this::openModeMenu));
    //
    //         // Back to spawn NPC
    //         backToSpawnNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§cBack to Spawn");
    //         backToSpawnNPC.spawn(location.clone().add(-2, 0, 0));
    //         backToSpawnNPC.addTrait(new ClickableTrait(this::teleportToSpawn));
    //     }
    // }

    private void openModeMenu(Player player) {
        openGameModeMenu(player);
    }

    // ===== MAIN LOBBY MENU =====
    public void openMainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9, "§6§lBridgeFight Lobby");
        menu.setItem(2, createItem(Material.DIAMOND_SWORD, "§b§lPlay", "§7Click to select a game mode"));
        menu.setItem(4, createItem(Material.ITEM_FRAME, "§e§lSettings", "§7Adjust your preferences"));
        menu.setItem(6, createItem(Material.BOOK, "§c§lStatistics", "§7View your stats"));
        menu.setItem(8, createItem(Material.BARRIER, "§7§lQuit", "§7Close this menu"));
        player.openInventory(menu);
    }

    // ===== GAME MODE SELECTION MENU =====
    public void openGameModeMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9, "§b§lSelect Game Mode");
        menu.setItem(0, createItem(Material.DIAMOND_SWORD, "§b§lClassic Duel", "§71v1 bridge combat", "§7Fast rounds, skill-based"));
        menu.setItem(2, createItem(Material.BOW, "§e§lArcher Duel", "§7Ranged bridge fights", "§7Long-range combat"));
        menu.setItem(4, createItem(Material.TNT, "§c§lExplosive Mode", "§7TNT-enabled chaos", "§7Explosive gameplay"));
        menu.setItem(6, createItem(Material.WHITE_WOOL, "§a§lTeam Battle", "§7Team-based competition", "§7Coordinate with teammates"));
        menu.setItem(8, createItem(Material.ARROW, "§7§lBack", "§7Return to main menu"));
        player.openInventory(menu);
    }

    // ===== SETTINGS MENU =====
    public void openSettingsMenu(Player player) {
        PlayerData data = gameManager.getPlayerData(player.getUniqueId());
        Inventory menu = Bukkit.createInventory(null, 9, "§e§lSettings");

        // Visibility toggle
        String visibilityStatus = data.isVisible() ? "§a✓ ON" : "§c✗ OFF";
        menu.setItem(0, createItem(Material.ENDER_EYE, "§6§lPlayer Visibility", "§7Status: " + visibilityStatus, "§7Click to toggle"));

        // Difficulty placeholder
        menu.setItem(2, createItem(Material.REDSTONE, "§6§lDifficulty", "§7Coming soon"));

        // Sound settings placeholder
        menu.setItem(4, createItem(Material.NOTE_BLOCK, "§6§lSound Settings", "§7Coming soon"));

        // Back button
        menu.setItem(8, createItem(Material.ARROW, "§7§lBack", "§7Return to main menu"));
        player.openInventory(menu);
    }

    // ===== STATS MENU =====
    public void openStatsMenu(Player player) {
        PlayerData data = gameManager.getPlayerData(player.getUniqueId());
        Inventory menu = Bukkit.createInventory(null, 9, "§c§lYour Statistics");

        // Create stat items - these are decorative display items
        menu.setItem(0, createItem(Material.GOLD_BLOCK, "§6§lWins", "§f" + data.getWins()));
        menu.setItem(1, createItem(Material.IRON_BLOCK, "§7§lLosses", "§f" + data.getLosses()));
        menu.setItem(3, createItem(Material.REDSTONE, "§c§lDeaths", "§f" + data.getDeaths()));
        menu.setItem(4, createItem(Material.EMERALD, "§a§lKills", "§f" + data.getKills()));
        menu.setItem(5, createItem(Material.DIAMOND, "§b§lK/D Ratio", "§f" + String.format("%.2f", data.getKDRatio())));
        menu.setItem(6, createItem(Material.BEACON, "§e§lWin Rate", "§f" + String.format("%.1f", data.getWinRate()) + "%"));
        menu.setItem(7, createItem(Material.ENCHANTED_BOOK, "§5§lGames Played", "§f" + data.getGamesPlayed()));

        // Back button
        menu.setItem(8, createItem(Material.ARROW, "§7§lBack", "§7Return to main menu"));
        player.openInventory(menu);
    }

    private void teleportToSpawn(Player player) {
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.sendMessage("§aTeleported back to spawn!");
    }

    private ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(java.util.Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createItem(Material material, String name, String... lores) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(java.util.Arrays.asList(lores));
        item.setItemMeta(meta);
        return item;
    }

    public void destroy() {
        // if (lobbyHologram != null) lobbyHologram.delete();
        // if (modeSelectorNPC != null) modeSelectorNPC.destroy();
        // if (backToSpawnNPC != null) backToSpawnNPC.destroy();
    }

    // private static class ClickableTrait implements Trait {
    //     private final Consumer<Player> action;
    //
    //     public ClickableTrait(Consumer<Player> action) {
    //         this.action = action;
    //     }
    //
    //     @EventHandler
    //     public void onClick(NPCRightClickEvent event) {
    //         action.accept(event.getClicker());
    //     }
    //
    //     @Override
    //     public void run() {}
    //
    //     @Override
    //     public void onAttach() {}
    //
    //     @Override
    //     public void onRemove() {}
    //
    //     @Override
    //     public void onSpawn() {}
    //
    //     @Override
    //     public void onDespawn() {}
    // }
}
