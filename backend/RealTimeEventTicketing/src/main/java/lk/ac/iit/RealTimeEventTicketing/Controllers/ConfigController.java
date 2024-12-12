package lk.ac.iit.RealTimeEventTicketing.Controllers;


import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.dto.ConfigUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private Config config;


    @PostMapping("/update")
    public ResponseEntity< Object> updateConfig(@RequestBody Config config) {
        config.setTotalTickets(config.getTotalTickets());
        config.setTicketReleaseRate(config.getTicketReleaseRate());
        config.setCustomerRetrievalRate(config.getCustomerRetrievalRate());
        config.setMaxTicketCapacity(config.getMaxTicketCapacity());

        return ResponseEntity.ok(config);
    }




}