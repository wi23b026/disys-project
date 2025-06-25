package at.fhtechnikum.communityrestapi;

import at.fhtechnikum.currentpercentageservice.CurrentPercentageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import at.fhtechnikum.currentpercentageservice.CurrentPercentageService;
import at.fhtechnikum.echoservice.UsageRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/energy")
public class EnergyDataController {

    private final EnergyDataRepository energyDataRepository;
    private final UsageRepository usageRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    @Autowired
    public EnergyDataController(EnergyDataRepository energyDataRepository, UsageRepository usageRepository, CurrentPercentageRepository currentPercentageRepository) {
        this.energyDataRepository = energyDataRepository;
        this.currentPercentageRepository = currentPercentageRepository;
        this.usageRepository = usageRepository;
    }

    @GetMapping
    public ArrayList<EnergyData> getAllEnergyData() {
        return new ArrayList<>(this.energyDataRepository.findAll());
    }

    @GetMapping("/current")
    public Optional<EnergyData> getCurrentEnergy() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        Optional<EnergyData> currentData = energyDataRepository.findByDate(now);

        if (currentData.isEmpty()) {
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

        ArrayList<EnergyData> data = energyDataRepository.findByDateBetween(startDate, endDate);

        if (data.isEmpty()) {
            throw new RuntimeException("No data for the specified period.");
        }

        return data;
    }
}