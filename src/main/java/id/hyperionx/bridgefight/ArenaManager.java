package id.hyperionx.bridgefight;

//import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaManager {

    private final GameManager gameManager;

    public ArenaManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void joinArena(Player player, String arenaName) {
        Arena arena = gameManager.getArena(arenaName);

        if (arena == null) {
            // fallback ke arena available
            arena = gameManager.findAvailableArena();

            if (arena == null) {
                player.sendMessage("§cNo available arenas!");
                return;
            }
        }

        if (arena.getState() != GameState.LOBBY) {
            player.sendMessage("§cGame already started!");
            return;
        }

        arena.addPlayer(player);
    }
}