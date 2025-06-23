package at.fhtechnikum.currentpercentageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CurrentPercentageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrentPercentageServiceApplication.class, args);
	}

}
