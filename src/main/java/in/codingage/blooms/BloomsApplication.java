package in.codingage.blooms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//annotation
@EnableJpaRepositories(basePackages = "in.codingage.blooms.repository")
@SpringBootApplication
public class BloomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloomsApplication.class, args);
        System.out.println("Blooms application started successfully");
	}

}
