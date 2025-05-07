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

    public List<EnergyData> getAllEnergyData() {
        return new ArrayList<>(energyData.values());
    }

    public void addEnergyData(EnergyData energyData) {
        if (energyData.getDate() == null) {
            throw new IllegalArgumentException("EnergyData must have a date.");
        }
        this.energyData.put(energyData.getDate(), energyData);
    }


    public EnergyData getByDate(LocalDateTime date) {
        return energyData.get(date);
    }


    public void initializeDummyData() {
        addEnergyData(new EnergyData(
                LocalDateTime.now().withMinute(0).withSecond(0).withNano(0),
                0.8f,
                0.2f,
                50f,
                45f,
                5f
        ));

        addEnergyData(new EnergyData(
                LocalDateTime.now().minusHours(1).withMinute(0).withSecond(0).withNano(0),
                0.9f,
                0.15f,
                40f,
                35f,
                5f
        ));
    }
}

