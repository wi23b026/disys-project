package at.fhtechnikum.echoservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"at.fhtechnikum.echoservice", "at.fhtechnikum.echomsg"})
@EnableJpaRepositories(basePackages = "at.fhtechnikum.echoservice")
@EntityScan(basePackages = {"at.fhtechnikum.echoservice", "at.fhtechnikum.echomsg"})
@EnableRabbit
public class EchoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceApplication.class, args);
    }

}