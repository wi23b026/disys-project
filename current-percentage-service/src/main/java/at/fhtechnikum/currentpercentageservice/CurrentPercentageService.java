package at.fhtechnikum.currentpercentageservice;

import at.fhtechnikum.echomsg.EchoMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CurrentPercentageService {
    UsageRepository usageRepository;
    CurrentPercentageRepository currentPercentageRepository;

    @Autowired
    public CurrentPercentageService(UsageRepository usageRepository, CurrentPercentageRepository currentPercentageRepository) {
        usageRepository = this.usageRepository;
        currentPercentageRepository = this.currentPercentageRepository;
    }

    @RabbitListener(queues = "echo.update.queue")
    public void handleUpdateNotice(@Payload EchoMessage message) {
        System.out.println("Update received: " + message);

        calcCurrentPercentage();
    }

    public void calcCurrentPercentage() {

    }
}
