package software.engineering.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * This is entry point to the program
 */
@SpringBootApplication
public class Application {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    /**
     * Main method launch server
     *
     * @param args no args expected
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
