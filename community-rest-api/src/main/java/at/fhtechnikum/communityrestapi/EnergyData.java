package at.fhtechnikum.communityrestapi;

import java.time.LocalDateTime;

public class EnergyData {
    private LocalDateTime date;
    private float currentEnergy;
    private float gridPortion;
    private float communityProduced;
    private float communityUsed;
    private float gridUsed;

    public EnergyData(float currentEnergy, float gridPortion, float communityProduced, float communityUsed, float gridUsed) {
        this.currentEnergy = currentEnergy;
        this.gridPortion = gridPortion;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

    public float getCurrentEnergy() {
        return currentEnergy;
    }
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
}
