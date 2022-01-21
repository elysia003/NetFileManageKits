package nas.vedio;

import nas.vedio.bean.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(Config.class)
@EnableScheduling
public class NasVedioBApplication {

    public static void main(String[] args) {
        SpringApplication.run(NasVedioBApplication.class, args);
    }

}
