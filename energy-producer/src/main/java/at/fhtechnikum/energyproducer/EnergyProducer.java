package at.fhtechnikum.energyproducer;

import at.fhtechnikum.echomsg.EchoMessage;
import at.fhtechnikum.echoservice.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 5000) // alle 5 Sekunden
    public void sendEnergyProduction() throws JsonProcessingException {
        EchoMessage msg = new EchoMessage("PRODUCER", "COMMUNITY", getRandomKwh(), LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "input", json);
    }

    private double getRandomKwh() {
        return 0.002 + (0.005 - 0.002) * Math.random();
    }
}