package at.fhtechnikum.communityrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EnergyDataController {
    private final EnergyDataRepository energyDataRepository;

    @Autowired
    public EnergyDataController(EnergyDataRepository EnergyDataRepository) {
        this.energyDataRepository = EnergyDataRepository;
    }

    @GetMapping("/energy")
    public ArrayList<EnergyData> getAllEnergyData() {
        return new ArrayList<EnergyData>(this.energyDataRepository.getAllEnergyData());
    }

    @GetMapping("/energy/current")
    public EnergyData getCurrentEnergy() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        EnergyData currentData = energyDataRepository.getDate(now);

        if (currentData == null) {
            throw new RuntimeException("No current data available.");
        }

        return currentData;
    }

    @GetMapping("/energy/historical")
    public ArrayList<EnergyData> getHistoricalEnergyData(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {

        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        List<EnergyData> allData = energyDataRepository.getAllEnergyData();

        ArrayList<EnergyData> filteredData = (ArrayList<EnergyData>) allData.stream()
                .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
                .toList();

        if (filteredData.isEmpty()) {
            throw new RuntimeException("No data for the specified period.");
        }

        return filteredData;
    }
}
