package lk.ac.iit.RealTimeEventTicketing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

@Component
public class ConfigLoader {
    private final Config appConfig;

    public ConfigLoader() throws IOException {
        // Path to your JSON file
        String filePath = "ticketSystemConfig.json";

        // Use Jackson to read the JSON file into the AppConfig object
        ObjectMapper objectMapper = new ObjectMapper();
        this.appConfig = objectMapper.readValue(new File(filePath), Config.class);
    }

    @Bean(name="treadPool")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(appConfig.getTotalTickets());
        executor.setQueueCapacity(appConfig.getMaxTicketCapacity());
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }

    public Config getAppConfig() {
        return appConfig;
    }
}