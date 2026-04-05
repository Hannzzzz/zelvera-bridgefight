# BridgeFight Plugin

A polished BridgeFight plugin for Zelvera MC.

## Features

- Fast bridge combat with quick rounds
- Duel-focused arena gameplay
- Player stats tracking for wins, kills, deaths, and K/D
- Admin tools for arena control and reloading
- Arena creation from in-game location
- Smooth PaperMC support with modern Bukkit API compatibility

## Installation

1. Build the plugin JAR with Maven
2. Place the JAR in your PaperMC server's `plugins` folder
3. Restart the server
4. Configure arenas using the admin commands

## Commands

### Player Commands
- `/bf join <arena>` - Join an arena
- `/bf leave` - Leave the current game
- `/bf stats` - View your match statistics
- `/bf shop` - Open the shop (coming soon)

### Admin Commands
- `/bfadmin start <arena>` - Force start an arena
- `/bfadmin stop <arena>` - Stop an arena
- `/bfadmin reload` - Reload arenas
- `/bfadmin create <arena>` - Create a new arena from your location
- `/bfadmin list` - List all arenas

## Configuration

Arenas are stored in `plugins/BridgeFight/arenas/`

To create an arena:
1. Run `/bfadmin create <name>` while standing in the lobby location
2. Edit the generated arena data file if needed

## Building

This plugin uses Maven.

```bash
mvn clean package
```

The JAR will be in `target/BridgeFight-1.0.0.jar`

## Dependencies

- PaperMC 1.20.x or compatible
- Java 8+

## Notes

- The plugin is built to run on PaperMC and is fully compatible with Bukkit-style servers.
- If you want BridgeFight-specific behavior, use `/bf` and `/bfadmin` commands.

## License

This plugin is provided as-is and may be modified freely.
