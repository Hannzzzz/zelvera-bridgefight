# BridgeFight Plugin

A polished BridgeFight plugin for Zelvera MC.

## Features

- Fast bridge combat with quick rounds
- Duel-focused arena gameplay
- Player stats tracking for wins, kills, deaths, and K/D
- Admin tools for arena control and reloading
- Arena creation from in-game location
- Smooth PaperMC support with modern Bukkit API compatibility
- **Advanced UI**: Floating holograms in lobbies (requires HolographicDisplays)
- **Interactive NPCs**: Clickable NPCs for mode selection and teleportation (requires Citizens)
- **Mode Menus**: Inventory-based selection for different BridgeFight modes

## Code Structure

- **Main.java**: Plugin entry point and command registration
- **GameManager.java**: Manages arenas and player sessions
- **Arena.java**: Handles individual arena logic and LobbyManager integration
- **BridgeFightCommand.java**: Player command handler
- **AdminCommand.java**: Admin command handler
- **GameListener.java**: Event handling for gameplay
- **LobbyManager.java**: Manages floating holograms and clickable NPCs (commented out until plugins are installed)
- **MenuListener.java**: Processes inventory clicks for mode selection
- **PlayerData.java**: Player statistics
- **Team.java**: Team management
- **GameState.java**: Arena state enumeration

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
- **Optional for Advanced Features**:
  - HolographicDisplays (for floating lobby holograms)
  - Citizens (for clickable NPCs)

## Enabling Advanced Features

To activate holograms and NPCs:

1. Install HolographicDisplays and Citizens plugins on your server
2. Uncomment the dependencies in `pom.xml`
3. Uncomment the hologram/NPC code in `LobbyManager.java`
4. Uncomment the event registration in `Main.java`
5. Rebuild and redeploy the plugin

## Notes

- The plugin is built to run on PaperMC and is fully compatible with Bukkit-style servers.
- If you want BridgeFight-specific behavior, use `/bf` and `/bfadmin` commands.
- Advanced UI features enhance the lobby experience but are not required for core gameplay.

## License

This plugin is provided as-is and may be modified freely.
