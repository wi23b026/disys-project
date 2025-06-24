package at.fhtechnikum.communityrestapi;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy_data")
public class EnergyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setCurrentEnergy(float currentEnergy) {
        this.currentEnergy = currentEnergy;
    }
    public void setGridPortion(float gridPortion){
        this.gridPortion = gridPortion;
    }
    public void setCommunityProduced(float communityProduced) {
         this.communityProduced = communityProduced;
    }
    public void setCommunityUsed(float communityUsed){
        this.communityUsed = communityUsed;
    }
    public void setGridUsed(float gridUsed) {
        this.gridUsed = gridUsed;
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