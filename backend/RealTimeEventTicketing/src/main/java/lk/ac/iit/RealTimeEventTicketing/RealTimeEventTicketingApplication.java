package lk.ac.iit.RealTimeEventTicketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@EnableAsync
public class RealTimeEventTicketingApplication implements CommandLineRunner {

    @Autowired
    ConfigLoader configLoader;


    public static void main(String[] args) throws IOException {
        SpringApplication.run(RealTimeEventTicketingApplication.class, args);

    }

    public void run(String... args) throws Exception {
        System.out.println(configLoader.getAppConfig().toString());


    }


}
