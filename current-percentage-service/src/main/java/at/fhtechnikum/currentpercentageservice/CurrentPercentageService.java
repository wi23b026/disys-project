package at.fhtechnikum.currentpercentageservice;

import at.fhtechnikum.echoservice.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentPercentageService {
    CurrentPercentageRepository currentPercentageRepository;

    @Autowired
    public CurrentPercentageService(CurrentPercentageRepository currentPercentageRepository) {
        this.currentPercentageRepository = currentPercentageRepository;
    }

    @RabbitListener(queues = "echo.update.queue")
    public void handleUpdateNotice(@Payload Usage usage) {
        System.out.println("Update received: " + usage);

        double communityUsed = usage.getCommunityUsed();
        double communityProduced = usage.getCommunityProduced();
        double gridUsed = usage.getGridUsed();

        double communityDepleted = calcCommunityDepleted(communityProduced, communityUsed);
        double totalUsed = communityUsed + gridUsed;
        double gridPortion = calcGridPortion(gridUsed, totalUsed);

        LocalDateTime hour = usage.getHour();
        if (hour != null) {
            hour = hour.withMinute(0).withSecond(0).withNano(0);
        } else {
            System.out.println("Warning: usage.getHour() is null!");
            hour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        }

        CurrentPercentage current = new CurrentPercentage(hour, communityDepleted, gridPortion);
        currentPercentageRepository.save(current);

        System.out.println("Saved: " + current);
    }

    public double calcCommunityDepleted(double communityProduced, double communityUsed) {
        double communityDepleted;
        if (communityProduced <= 0) {
            communityDepleted = 100.0;
        } else {
            communityDepleted = (communityUsed / communityProduced) * 100;
            if (communityDepleted > 100.0) {
                communityDepleted = 100.0;
            }
        }
        return communityDepleted;
    }

    public double calcGridPortion(double gridUsed, double totalUsed) {
        double gridPortion;
        if (totalUsed <= 0) {
            gridPortion = 0.0;
        } else {
            gridPortion = (gridUsed / totalUsed) * 100;
        }
        return gridPortion;
    }
}
