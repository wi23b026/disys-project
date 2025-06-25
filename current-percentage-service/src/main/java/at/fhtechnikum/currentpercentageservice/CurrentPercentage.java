package at.fhtechnikum.currentpercentageservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="current_percentage")
public class CurrentPercentage {
    @Id
    private LocalDateTime hour;
    @Column
    private double communityDepleted;
    @Column
    private double gridPortion;

    public CurrentPercentage() {}

    public CurrentPercentage(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
    public void setCommunityDepleted(double communityDepleted) {
        this.communityDepleted = communityDepleted;
    }
    public void setGridPortion(double gridPortion) {
        this.gridPortion = gridPortion;
    }

    public LocalDateTime getHour() {
        return hour;
    }
    public double getCommunityDepleted() {
        return communityDepleted;
    }
    public double getGridPortion() {
        return gridPortion;
    }
}
