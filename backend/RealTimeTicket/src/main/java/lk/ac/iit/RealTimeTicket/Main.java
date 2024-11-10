package lk.ac.iit.RealTimeTicket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        TicketSystemConfig config = new TicketSystemConfig();
        config.configureSystem();
    }
}