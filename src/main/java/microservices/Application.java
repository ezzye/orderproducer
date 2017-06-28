package microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

/**
 * Created by ellioe03 on 27/06/2017.
 */
@SpringBootApplication
@ComponentScan

public class Application {
    static ConfigurableApplicationContext context;
    public static void main(String[] args) {
         context = SpringApplication.run(Application.class, args);


//        SpringApplication.run(Application.class, args);
    }
}
