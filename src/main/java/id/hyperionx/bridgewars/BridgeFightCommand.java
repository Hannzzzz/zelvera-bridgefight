package id.hyperionx.bridgewars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BridgeFightCommand implements CommandExecutor {

    private final Main plugin;

    public BridgeFightCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "join":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /bf join <arena>");
                    return true;
                }
                plugin.getGameManager().joinGame(player, args[1]);
                break;
            case "leave":
                plugin.getGameManager().leaveGame(player);
                player.sendMessage("§aLeft the game!");
                break;
            case "stats":
                PlayerData data = plugin.getGameManager().getPlayerData(player.getUniqueId());
                player.sendMessage("§6Your Stats:");
                player.sendMessage("§7Wins: §f" + data.getWins());
                player.sendMessage("§7Losses: §f" + data.getLosses());
                player.sendMessage("§7Games Played: §f" + data.getGamesPlayed());
                player.sendMessage("§7Kills: §f" + data.getKills());
                player.sendMessage("§7Deaths: §f" + data.getDeaths());
                player.sendMessage("§7K/D Ratio: §f" + String.format("%.2f", data.getKDRatio()));
                player.sendMessage("§7Win Rate: §f" + String.format("%.1f", data.getWinRate()) + "%");
                break;
            case "shop":
                // Open shop GUI
                player.sendMessage("§aShop feature coming soon!");
                break;
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§6BridgeFight Commands:");
        player.sendMessage("§7/bf join <arena> §f- Join an arena");
        player.sendMessage("§7/bf leave §f- Leave the current game");
        player.sendMessage("§7/bf stats §f- View your statistics");
        player.sendMessage("§7/bf shop §f- Open the shop");
    }
}