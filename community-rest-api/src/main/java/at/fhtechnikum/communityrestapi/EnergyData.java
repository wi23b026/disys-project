package at.fhtechnikum.communityrestapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EnergyData {
    @Id
    private LocalDateTime date;

    private float currentEnergy;
    private float gridPortion;
    private float communityProduced;
    private float communityUsed;
    private float gridUsed;

    public EnergyData() { }

    public EnergyData(LocalDateTime date, float currentEnergy, float gridPortion, float communityProduced, float communityUsed, float gridUsed) {
        this.date = date;
        this.currentEnergy = currentEnergy;
        this.gridPortion = gridPortion;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

    public LocalDateTime setDate(LocalDateTime date) {
        return this.date = date;
    }
    public float setCurrentEnergy(float currentEnergy) {
        return this.currentEnergy = currentEnergy;
    }
    public float setGridPortion(float gridPortion){
        return this.gridPortion = gridPortion;
    }
    public float setCommunityProduced(float communityProduced) {
        return this.communityProduced = communityProduced;
    }
    public float setCommunityUsed(float communityUsed){
        return this.communityUsed = communityUsed;
    }
    public float setGridUsed(float gridUsed) {
        return this.gridUsed = gridUsed;
    }

    public LocalDateTime getDate() {
        return date;
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
}