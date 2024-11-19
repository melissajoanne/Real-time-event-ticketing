package lk.ac.iit.RealTimeTicket;


//import lk.ac.iit.RealTimeTicket.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Main //implements CommandLineRunner
 {
    @Autowired
    private TicketSystemConfig ticketSystemConfig;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    public void run(String... args) throws Exception {
        ticketSystemConfig.configureSystem();  // Start configuration process
    }


}