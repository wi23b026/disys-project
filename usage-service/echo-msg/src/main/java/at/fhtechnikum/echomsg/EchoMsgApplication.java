package at.fhtechnikum.echomsg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"at.fhtechnikum.echomsg"})
@EnableJpaRepositories("at.fhtechnikum.echomsg")
public class EchoMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoMsgApplication.class, args);
    }

}
