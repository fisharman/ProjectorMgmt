package ProjectorMgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    Database database (){
        return new Database();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
