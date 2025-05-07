package at.fhtechnikum.communityrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/energy")
public class EnergyDataController {

    private final EnergyDataRepository energyDataRepository;

    @Autowired
    public EnergyDataController(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    @GetMapping
    public ArrayList<EnergyData> getAllEnergyData() {
        return new ArrayList<>(this.energyDataRepository.getAllEnergyData());
    }

    @GetMapping("/current")
    public EnergyData getCurrentEnergy() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        EnergyData currentData = energyDataRepository.getByDate(now);

        if (currentData == null) {
            throw new RuntimeException("No current data available.");
        }

        return currentData;
    }

    @GetMapping("/historical")
    public ArrayList<EnergyData> getHistoricalEnergyData(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {

        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        List<EnergyData> allData = energyDataRepository.getAllEnergyData();

        ArrayList<EnergyData> filteredData = allData.stream()
                .filter(data -> data.getDate() != null
                        && !data.getDate().isBefore(startDate)
                        && !data.getDate().isAfter(endDate))
                .collect(Collectors.toCollection(ArrayList::new));

        if (filteredData.isEmpty()) {
            throw new RuntimeException("No data for the specified period.");
        }

        return filteredData;
    }
}

