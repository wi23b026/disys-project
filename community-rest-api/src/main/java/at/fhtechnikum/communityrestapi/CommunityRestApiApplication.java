package at.fhtechnikum.communityrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "at.fhtechnikum")
@ComponentScan(basePackages = "at.fhtechnikum")
@EntityScan(basePackages = "at.fhtechnikum")
public class CommunityRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityRestApiApplication.class, args);
    }

}