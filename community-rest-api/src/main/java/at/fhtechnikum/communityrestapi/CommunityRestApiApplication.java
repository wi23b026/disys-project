package at.fhtechnikum.communityrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "at.fhtechnikum.communityrestapi",
        "at.fhtechnikum.echoservice",
        "at.fhtechnikum.currentpercentageservice"
})
public class CommunityRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityRestApiApplication.class, args);
    }

}