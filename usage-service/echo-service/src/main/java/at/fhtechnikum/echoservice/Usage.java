package at.fhtechnikum.echoservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="usage")
public class Usage {
    @Id
    private LocalDateTime hour;
    @Column
    private double communityProduced;
    @Column
    private double communityUsed;
    @Column
    private double gridUsed;

    public Usage() {}

    public Usage(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed) {
        this.hour = hour;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }
    public double getCommunityUsed() {
        return communityUsed;
    }
    public double getGridUsed() {
        return gridUsed;
    }
    public LocalDateTime getHour() {
        return hour;
    }

    public void setCommunityProduced(double communityProduced) {
        this.communityProduced = communityProduced;
    }
    public void setCommunityUsed(double communityUsed) {
        this.communityUsed = communityUsed;
    }
    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }
    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}