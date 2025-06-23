package at.fhtechnikum.echoservice;

import at.fhtechnikum.echomsg.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EchoService {
    private final RabbitTemplate rabbitTemplate;

    private final EchoMessageRepository echoMessageRepository;
    private final UsageRepository usageRepository;

    @Autowired
    public EchoService(RabbitTemplate rabbitTemplate, EchoMessageRepository echoMessageRepository, UsageRepository usageRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.echoMessageRepository = echoMessageRepository;
        this.usageRepository = usageRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.INPUT_QUEUE)
    public void handleUsage(@Payload EchoMessage message) {
        System.out.println("Received message:" + message + " -  Type: " + message.getType());

        saveMessage(message);

        LocalDateTime hour = message.getDatetime().withMinute(0).withSecond(0).withNano(0);
        Usage usage = usageRepository.findByHour(hour).orElseGet(() -> new Usage(hour, 0, 0, 0));

        double currentProduced = usage.getCommunityProduced();
        double currentUsed = usage.getCommunityUsed();
        double currentGridUsed = usage.getGridUsed();

        if ("PRODUCER".equals(message.getType()) && "COMMUNITY".equals(message.getAssociation())) {
            double newCommunityProduced = currentProduced + message.getKwh();
            usage.setCommunityProduced(newCommunityProduced);
        } else if ("USER".equals(message.getType()) && "COMMUNITY".equals(message.getAssociation())) {
            double newCommunityUsed = currentUsed + message.getKwh();
            usage.setCommunityUsed(newCommunityUsed);

            double excess = usage.getCommunityProduced() - newCommunityUsed;
            if (excess > 0) {
                usage.setGridUsed(currentGridUsed + excess);
            }
        }

        usageRepository.save(usage);
        System.out.println("Sending usage: " + usage);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.UPDATE_ROUTING_KEY,
                usage
        );
    }

    public EchoMessage saveMessage(EchoMessage message) {
        System.out.println("Saving message: " + message);
        return echoMessageRepository.save(message);
    }
}