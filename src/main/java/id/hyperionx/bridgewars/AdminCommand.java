package id.hyperionx.bridgewars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    private final Main plugin;

    public AdminCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bridgefight.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /bfadmin start <arena>");
                    return true;
                }
                Arena arena = plugin.getGameManager().getArena(args[1]);
                if (arena == null) {
                    sender.sendMessage("§cArena not found!");
                    return true;
                }
                // force start logic
                sender.sendMessage("§aForce starting arena " + args[1] + "!");
                break;
            case "stop":
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /bfadmin stop <arena>");
                    return true;
                }
                arena = plugin.getGameManager().getArena(args[1]);
                if (arena == null) {
                    sender.sendMessage("§cArena not found!");
                    return true;
                }
                arena.stopGame();
                sender.sendMessage("§aStopped arena " + args[1] + "!");
                break;
            case "reload":
                plugin.getGameManager().loadArenas();
                sender.sendMessage("§aReloaded arenas!");
                break;
            case "create":
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /bfadmin create <arena>");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cThis command must be used by a player!");
                    return true;
                }
                Player player = (Player) sender;
                Arena newArena = new Arena(args[1], plugin);
                plugin.getGameManager().addArena(newArena);
                player.sendMessage("§aCreated arena " + args[1] + "!");
                break;
            case "list":
                sender.sendMessage("§6Arenas:");
                for (Arena a : plugin.getGameManager().getArenas()) {
                    sender.sendMessage("§7- " + a.getName() + " (" + a.getState() + ")");
                }
                break;
            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§6BridgeFight Admin Commands:");
        sender.sendMessage("§7/bfadmin start <arena> §f- Force start an arena");
        sender.sendMessage("§7/bfadmin stop <arena> §f- Stop an arena");
        sender.sendMessage("§7/bfadmin reload §f- Reload arenas");
        sender.sendMessage("§7/bfadmin create <arena> §f- Create a new arena");
        sender.sendMessage("§7/bfadmin list §f- List all arenas");
    }
}