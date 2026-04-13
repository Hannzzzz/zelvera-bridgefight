package id.hyperionx.bridgefight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaManager {
    


public void joinArena(Player player, String arenaName) {
    Arena arena = gameManager.getArena(arenaName);
    if (arena == null) {
        player.sendMessage("§cArena not found!");
        return;
    }

    if (arena.getState() != GameState.LOBBY) {
        player.sendMessage("§cGame Already Started!");
        return;    
    }

    arena.addPlayer(player);
    };

private final GameManager gameManager;
public ArenaManager(GameManager gameManager) {
    this.gameManager = gameManager;
}
}