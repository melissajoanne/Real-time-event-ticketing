package lk.ac.iit.RealTimeTicket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Integer ticketPoolSize() {
        return 5;  // or fetch from a properties file or environment
    }
}
