package at.fhtechnikum.communityrestapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class CommunityRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityRestApiApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(EnergyDataRepository repository) {
		return args -> {
			repository.initializeDummyData();
		};
	}
}
