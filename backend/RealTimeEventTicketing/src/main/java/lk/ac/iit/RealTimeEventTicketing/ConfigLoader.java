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
        String filePath = "/Users/melzjoanne/Documents/GitHub/Real-time-ticket/CLI/CLI/ticketSystemConfig.json";

        // JSON file into the AppConfig object
        ObjectMapper objectMapper = new ObjectMapper();
        this.appConfig = objectMapper.readValue(new File(filePath), Config.class);
    }

    public Config getAppConfig() {
        return appConfig;
    }
}
