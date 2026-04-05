# BridgeFight Plugin

A professional BridgeFight plugin for Zelvera MC.

## Features

- Team-based gameplay (2-4 teams)
- Bridge building mechanics
- Duel arenas and bridge combat
- Player statistics tracking
- Admin commands for management
- Configurable arenas

## Installation

1. Download the plugin JAR file
2. Place it in the server's `plugins` folder
3. Restart the server
4. Configure arenas using admin commands

## Commands

### Player Commands
- `/bf join <arena>` - Join an arena
- `/bf leave` - Leave the current game
- `/bf stats` - View your statistics
- `/bf shop` - Open the shop (coming soon)

### Admin Commands
- `/bfadmin start <arena>` - Force start an arena
- `/bfadmin stop <arena>` - Stop an arena
- `/bfadmin reload` - Reload arenas
- `/bfadmin create <arena>` - Create a new arena
- `/bfadmin list` - List all arenas

## Configuration

Arenas are stored in `plugins/BridgeFight/arenas/`

To create an arena:
1. Use `/bfadmin create <name>` while standing where you want the lobby
2. Edit the generated YAML file to set team spawns and nexus locations

## Building

This plugin uses Maven for building.

```bash
mvn clean package
```

The JAR will be in `target/BridgeFight-1.0.0.jar`

## Dependencies

- Spigot 1.8.8 or higher
- Java 8+

## Troubleshooting

- **Plugin not loading**: Check server logs for errors
- **Commands not working**: Ensure you have the correct permissions
- **Arena not found**: Use `/bfadmin list` to see available arenas

## License

This plugin is provided as-is. Feel free to modify and distribute.
