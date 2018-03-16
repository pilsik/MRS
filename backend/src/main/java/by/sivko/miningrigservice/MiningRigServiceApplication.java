package by.sivko.miningrigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class MiningRigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiningRigServiceApplication.class, args);
	}
}
