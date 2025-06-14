package pe.upc.cleanview.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CleanViewPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(CleanViewPlatformApplication.class, args);
    }

}
