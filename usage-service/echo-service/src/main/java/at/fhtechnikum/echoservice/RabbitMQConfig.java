package at.fhtechnikum.echoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "echo.processing.exchange";
    public static final String INPUT_QUEUE = "echo.input.queue";
    public static final String OUTPUT_QUEUE = "echo.output.queue";
    public static final String UPDATE_QUEUE = "echo.update.queue";
    public static final String INPUT_ROUTING_KEY = "echo.input";
    public static final String OUTPUT_ROUTING_KEY = "echo.output";
    public static final String UPDATE_ROUTING_KEY = "echo.update";

    // —the central exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // —the queue where USER/PRODUCER messages arrive
    @Bean
    public Queue inputQueue() {
        return new Queue(INPUT_QUEUE, true);
    }

    // —the queue where we’ll publish “update” notices
    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE, true);
    }

    // —bind inputQueue to exchange with INPUT_ROUTING_KEY
    @Bean
    public Binding bindInput() {
        return BindingBuilder
                .bind(inputQueue())
                .to(exchange())
                .with(INPUT_ROUTING_KEY);
    }

    // —bind updateQueue to exchange with UPDATE_ROUTING_KEY
    @Bean
    public Binding bindUpdate() {
        return BindingBuilder
                .bind(updateQueue())
                .to(exchange())
                .with(UPDATE_ROUTING_KEY);
    }

    // —JSON ⇄ POJO converter
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

    // —attach converter to RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate tpl = new RabbitTemplate(cf);
        tpl.setMessageConverter(messageConverter());
        return tpl;
    }

}