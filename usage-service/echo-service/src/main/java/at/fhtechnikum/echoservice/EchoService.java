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
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EchoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.INPUT_QUEUE)
    public void handleUsage(
            @Payload EchoMessage message,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {

        // 1) LOG the incoming USER/PRODUCER message
        System.out.println("Received usage msg → " + message);

        // 2) (later) update your hourly-aggregation DB here...

        // 3) PUBLISH an “update available” notice
        //    we’ll just forward the same message for now:
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.UPDATE_ROUTING_KEY,
                message
        );

        // 4) ACK so RabbitMQ can drop the original
        channel.basicAck(tag, false);
    }
}