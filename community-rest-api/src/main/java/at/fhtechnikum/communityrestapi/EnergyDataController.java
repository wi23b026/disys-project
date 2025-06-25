package at.fhtechnikum.communityrestapi;

import at.fhtechnikum.currentpercentageservice.CurrentPercentage;
import at.fhtechnikum.currentpercentageservice.CurrentPercentageRepository;
import at.fhtechnikum.echoservice.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EnergyData> getCurrentEnergy() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        Optional<Usage> usageOptional = usageRepository.findByHour(now);
        Optional<CurrentPercentage> percentageOptional = currentPercentageRepository.findByHour(now);

        if (usageOptional.isEmpty() || percentageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usage usage = usageOptional.get();
        CurrentPercentage percentage = percentageOptional.get();

        float communityProduced = (float) usage.getCommunityProduced();
        float communityUsed = (float) usage.getCommunityUsed();

        float currentCommunityPoolPercentage = 0.0f;
        if (communityProduced > 0) {
            currentCommunityPoolPercentage = ((communityProduced - communityUsed) / communityProduced) * 100;
        }


        EnergyData combinedEnergyData = new EnergyData(
                now,
                currentCommunityPoolPercentage,
                (float) percentage.getGridPortion(),
                (float) usage.getCommunityProduced(),
                (float) usage.getCommunityUsed(),
                (float) usage.getGridUsed()
        );

        return ResponseEntity.ok(combinedEnergyData);
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