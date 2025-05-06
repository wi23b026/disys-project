package at.fhtechnikum.communityrestapi;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class EnergyDataRepository {
    private final Map<LocalDateTime, EnergyData> energyData;

    public EnergyDataRepository() {
        this.energyData = new HashMap<>();
    }

    public static Date getDate() {
        return new Date();
    }

    public List<EnergyData> getAllEnergyData() {
        return new ArrayList<>(energyData.values());
    }

    public void addEnergyData(EnergyData energyData) {
        this.energyData.put(energyData.getDate(), energyData);
    }

    public EnergyData getDate(LocalDateTime date) {
        return energyData.get(date);
    }
}
