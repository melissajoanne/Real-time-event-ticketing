package lk.ac.iit.RealTimeTicket;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private TicketSystemConfig ticketSystemConfig;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ticketSystemConfig.configureSystem();  // Start configuration process
    }
}
