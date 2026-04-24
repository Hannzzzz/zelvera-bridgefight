package id.hyperionx.bridgefight;

import org.bukkit.ChatColor;
import org.bukkit.Location;
//import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private final String name;
    private final ChatColor color;
    private final byte woolData;
    private final Set<Player> players = new HashSet<>();
    private Location spawnLocation;
    private Location nexusLocation;
    private boolean nexusAlive = true;

    public Team(String name, ChatColor color, byte woolData) {
        this.name = name;
        this.color = color;
        this.woolData = woolData;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public byte getWoolData() {
        return woolData;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public int getSize() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getNexusLocation() {
        return nexusLocation;
    }

    public void setNexusLocation(Location nexusLocation) {
        this.nexusLocation = nexusLocation;
    }

    public boolean isNexusAlive() {
        return nexusAlive;
    }

    public void destroyNexus() {
        this.nexusAlive = false;
    }

    public String getColoredName() {
        return color + name;
    }
}