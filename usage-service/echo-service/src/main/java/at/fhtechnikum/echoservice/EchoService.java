package at.fhtechnikum.echoservice;

import at.fhtechnikum.echomsg.EchoMessage;
import at.fhtechnikum.echomsg.EchoMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EchoService {
    private final RabbitTemplate rabbitTemplate;

    private final EchoMessageRepository repository;

    @Autowired
    public EchoService(RabbitTemplate rabbitTemplate, EchoMessageRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConfig.INPUT_QUEUE)
    public void handleUsage(@Payload EchoMessage message) {
        System.out.println("Received message:" + message + " -  Type: " + message.getType());

        saveMessage(message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.UPDATE_ROUTING_KEY,
                message
        );
    }

    public EchoMessage saveMessage(EchoMessage message) {
        System.out.println("Saving message: " + message);
        return repository.save(message);
    }
}