package at.fhtechnikum.echoservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class EchoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceApplication.class, args);
    }

}