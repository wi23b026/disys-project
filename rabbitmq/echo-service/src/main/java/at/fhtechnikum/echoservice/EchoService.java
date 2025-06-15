package at.fhtechnikum.echoservice;

import at.fhtechnikum.echomsg.EchoMessage;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "echo.input", ackMode = "MANUAL")
    public void processText(@Payload byte[] messageBytes,
                            Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = new String(messageBytes);
        EchoMessage message = objectMapper.readValue(jsonString, EchoMessage.class);

        System.out.println("=== USAGE SERVICE ===");
        System.out.println("Received: " + message);
        System.out.println("======================\n");

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "update",
                "New usage data for " + message.getDatetime()
        );

        channel.basicAck(deliveryTag, false);
    }
}