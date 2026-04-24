package id.hyperionx.bridgefight.listeners;

//import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import id.hyperionx.bridgefight.GameManager;
import id.hyperionx.bridgefight.LobbyManager;
import id.hyperionx.bridgefight.PlayerData;

public class MenuListener implements Listener {

    private final GameManager gameManager;
    private final LobbyManager lobbyManager;

    public MenuListener(GameManager gameManager, LobbyManager lobbyManager) {
        this.gameManager = gameManager;
        this.lobbyManager = lobbyManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getAmount() == 0) return;

        // Main Lobby Menu
        if (title.equals("§6§lBridgeFight Lobby")) {
            handleMainMenu(player, event.getSlot());
        }
        // Game Mode Selection Menu
        else if (title.equals("§b§lSelect Game Mode")) {
            handleGameModeMenu(player, event.getSlot());
        }
        // Settings Menu
        else if (title.equals("§e§lSettings")) {
            handleSettingsMenu(player, event.getSlot());
        }
        // Stats Menu
        else if (title.equals("§c§lYour Statistics")) {
            handleStatsMenu(player, event.getSlot());
        }
    }

    private void handleMainMenu(Player player, int slot) {
        switch (slot) {
            case 2: // Play
                lobbyManager.openGameModeMenu(player);
                break;
            case 4: // Settings
                lobbyManager.openSettingsMenu(player);
                break;
            case 6: // Stats
                lobbyManager.openStatsMenu(player);
                break;
            case 8: // Quit
                player.closeInventory();
                player.sendMessage("§cMenu closed.");
                break;
        }
    }

    private void handleGameModeMenu(Player player, int slot) {
        switch (slot) {
            case 0: // Classic Duel
                player.sendMessage("§aJoining Classic Duel!");
                player.closeInventory();
                break;
            case 2: // Archer Duel
                player.sendMessage("§aJoining Archer Duel!");
                player.closeInventory();
                break;
            case 4: // Explosive Mode
                player.sendMessage("§aJoining Explosive Mode!");
                player.closeInventory();
                break;
            case 6: // Team Battle
                player.sendMessage("§aJoining Team Battle!");
                player.closeInventory();
                break;
            case 8: // Back
                lobbyManager.openMainMenu(player);
                break;
        }
    }

    private void handleSettingsMenu(Player player, int slot) {
        PlayerData data = gameManager.getPlayerData(player.getUniqueId());
        switch (slot) {
            case 0: // Toggle Visibility
                data.toggleVisibility();
                player.sendMessage("§aVisibility: " + (data.isVisible() ? "ON" : "OFF"));
                lobbyManager.openSettingsMenu(player);
                break;
            case 2: // Difficulty
                player.sendMessage("§eDifficulty setting updated.");
                lobbyManager.openSettingsMenu(player);
                break;
            case 4: // Sound Settings
                player.sendMessage("§eSounds toggled.");
                lobbyManager.openSettingsMenu(player);
                break;
            case 8: // Back
                lobbyManager.openMainMenu(player);
                break;
        }
    }

    private void handleStatsMenu(Player player, int slot) {
        switch (slot) {
            case 8: // Back
                lobbyManager.openMainMenu(player);
                break;
        }
    }
}