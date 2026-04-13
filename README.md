# BridgeFight Plugin

A professional BridgeFight minigame plugin made for Zelvera Network. Supports Minecraft 1.8.8+ with Java 8 compatibility.

## Features

- Fast bridge combat with quick rounds
- Duel-focused arena gameplay
- Player stats tracking for wins, kills, deaths, and K/D ratio
- Admin tools for arena control and management
- Arena creation and configuration from in-game commands
- **Advanced UI**: Floating holograms in lobbies (requires HolographicDisplays plugin)
- **Interactive NPCs**: Clickable NPCs for mode selection and teleportation (requires Citizens plugin)
- **Inventory Menus**: Mode selection via inventory GUI
- Team-based gameplay with colored teams

## Compatibility

- **Minecraft Versions**: 1.8.8+
- **Server Software**: Spigot, Paper, and compatible forks
- **Java Version**: Java 8+

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

### Quick Start
1. Download the latest compiled JAR from `target/bridgefight-1.0.0.jar` or build it yourself
2. Place the JAR in your server's `plugins/` folder
3. Restart the server
4. The plugin will automatically load

### From Source
```bash
mvn clean package
```
The compiled JAR will be created at: `target/bridgefight-1.0.0.jar`

## Commands

### Player Commands
- `/bridgefight join <arena>` - Join an arena
- `/bridgefight leave` - Leave the current game
- `/bridgefight stats` - View your match statistics
- Aliases: `/bf`

### Admin Commands
- `/bfadmin start <arena>` - Force start an arena
- `/bfadmin stop <arena>` - Stop an arena
- `/bfadmin reload` - Reload plugin configuration
- `/bfadmin create <arena>` - Create a new arena at your current location
- `/bfadmin list` - List all available arenas

## Configuration

Arenas are stored in `plugins/BridgeFight/arenas/` directory.

### Creating an Arena
1. Stand at the lobby location where players will spawn
2. Run `/bfadmin create <arena_name>`
3. The arena configuration will be generated and saved
4. Configure additional settings by editing the generated arena file if needed

## Building

This plugin uses Maven for building and dependency management.

### Requirements
- Maven 3.8+
- Java 8+ JDK

### Build Command
```bash
mvn clean package
```

### Output
- **Compiled JAR**: `target/bridgefight-1.0.1.jar`
- **Original JAR**: `target/original-bridgefight-1.0.1.jar`

The shaded JAR (`bridgefight-1.0.0.jar`) is ready for deployment on your server.

## Dependencies

### Required
- Spigot/Paper API 1.8.8 (or compatible Bukkit API for 1.8.8+)

### Optional (for Advanced Features)
- **HolographicDisplays** (for floating lobby holograms)
- **Citizens** (for interactive NPCs)

**Note**: The plugin builds and runs without these optional dependencies. Advanced features are disabled by default.

## Advanced Features

By default, the plugin runs without HolographicDisplays and Citizens support. These features are opt-in:

### Enabling Holograms and NPCs

1. **Install the plugins on your server**:
   - Download and install [HolographicDisplays](https://dev.bukkit.org/projects/holographicdisplays)
   - Download and install [Citizens](https://dev.bukkit.org/projects/citizens)

2. **Update the source code**:
   - Uncomment the imports at the top of `LobbyManager.java`
   - Uncomment the field declarations
   - Uncomment the `setupHologram()` and `setupNPCs()` methods
   - Uncomment the `ClickableTrait` inner class

3. **Rebuild the plugin**:
   ```bash
   mvn clean package
   ```

4. **Deploy and restart** - The previous JAR will be replaced

Once enabled, players will see:
- Floating holographic text in the lobby
- Interactive NPCs for mode selection and teleportation

## Usage Notes

- **Permissions**: Admin commands require `bridgefight.admin` permission
- **Configuration**: All arena data is automatically saved and persists through server restarts
- **Compatibility**: Fully compatible with 1.8.8 servers and newer Bukkit-based servers
- **Performance**: Lightweight plugin with minimal server impact
- **Features**: Core gameplay works out-of-the-box; optional UI features require additional plugins

## Troubleshooting

- **Plugin won't load**: Ensure you're using Java 8+ and a compatible Spigot/Paper server
- **Commands not working**: Check permissions and that the plugin is loaded with `/plugins`
- **Arenas not saving**: Check `plugins/BridgeFight/` folder permissions

## License & Credits

This plugin is provided as-is and may be modified freely for personal and server use.

**Repository**: [zelvera-bridgefight](https://github.com/Hannzzzz/zelvera-bridgefight)
