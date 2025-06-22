package at.fhtechnikum.currentpercentageservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CurrentPercentageService {
    @Autowired
    UsageRepository UsageRepository;

    @Autowired
    CurrentPercentageRepository CurrentPercentageRepository;

    @RabbitListener(queues = "echo.output")
    public void handleEnergyUpdate(String message) {
        LocalDateTime currentHour = LocalDateTime.now().withMinute(0).withSecond(0);
        Optional<Usage> usage = UsageRepository.findByHour(currentHour);

        if (usage.isPresent()) {
            double totalUsed = usage.get().getCommunityUsed() + usage.get().getGridUsed();
            double gridPortion = (usage.get().getGridUsed() / totalUsed) * 100.0;

            double communityDepletion = 100.0;
            if (usage.get().getCommunityProduced() > usage.get().getCommunityUsed()) {
                communityDepletion = (usage.get().getCommunityUsed() / usage.get().getCommunityProduced()) * 100.0;
            }

            CurrentPercentage current = new CurrentPercentage();
            current.setHour(currentHour);
            current.setGridPortion(Math.round(gridPortion * 100.0) / 100.0);
            current.setCommunityDepleted(Math.round(communityDepletion * 100.0) / 100.0);

            CurrentPercentageRepository.save(current);
        }
    }
}
