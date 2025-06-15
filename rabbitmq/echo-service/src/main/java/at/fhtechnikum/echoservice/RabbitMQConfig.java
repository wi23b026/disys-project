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

    public static final String INPUT_QUEUE = "echo.input";
    public static final String OUTPUT_QUEUE = "echo.output";
    public static final String EXCHANGE_NAME = "echo.processing.exchange";

    @Bean
    public DirectExchange textProcessingExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue inputQueue() {
        return QueueBuilder.durable(INPUT_QUEUE).build();
    }

    @Bean
    public Queue outputQueue() {
        return QueueBuilder.durable(OUTPUT_QUEUE).build();
    }

    @Bean
    public Binding inputBinding() {
        return BindingBuilder.bind(inputQueue()).to(textProcessingExchange()).with("echo.input");
    }

    @Bean
    public Binding outputBinding() {
        return BindingBuilder.bind(outputQueue()).to(textProcessingExchange()).with("echo.output");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }
}