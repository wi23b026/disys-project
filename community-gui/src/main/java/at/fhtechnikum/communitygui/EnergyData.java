package at.fhtechnikum.communitygui;

import java.time.LocalDateTime;

public class EnergyData {
    private LocalDateTime date;
    private float currentEnergy;
    private float gridPortion;
    private float communityProduced;
    private float communityUsed;
    private float gridUsed;

    public float getGridPortion() {
        return gridPortion;
    }

    public float getCommunityProduced() {
        return communityProduced;
    }
    public float getCommunityUsed() {
        return communityUsed;
    }

    public float getGridUsed() {
        return gridUsed;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public float getCurrentEnergy() {
        return currentEnergy;
    }
}
