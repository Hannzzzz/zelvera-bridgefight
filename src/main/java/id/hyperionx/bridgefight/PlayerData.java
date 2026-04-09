package id.hyperionx.bridgefight;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int wins = 0;
    private int losses = 0;
    private int kills = 0;
    private int deaths = 0;
    private int gamesPlayed = 0;
    private boolean visible = true; // Player visibility toggle

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        wins++;
        gamesPlayed++;
    }

    public int getLosses() {
        return losses;
    }

    public void addLoss() {
        losses++;
        gamesPlayed++;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        deaths++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public double getKDRatio() {
        return deaths == 0 ? kills : (double) kills / deaths;
    }

    public double getWinRate() {
        return gamesPlayed == 0 ? 0 : (double) wins / gamesPlayed * 100;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
    }
}