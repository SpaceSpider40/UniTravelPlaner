package uni.unitravelplaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UniTravelPlanerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniTravelPlanerApplication.class, args);
    }

}
