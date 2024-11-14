package lk.ac.iit.RealTimeTicket.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonFileUtility {
    private final ObjectMapper objectMapper;

    public JsonFileUtility(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void saveObjectAsJsonFile(Object object, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), object);
    }
}
