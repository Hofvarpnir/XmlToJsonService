package software.engineering.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is entry point to the program
 */
@SpringBootApplication
public class Application {
    /**
     * Main method launch server
     *
     * @param args no args expected
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
