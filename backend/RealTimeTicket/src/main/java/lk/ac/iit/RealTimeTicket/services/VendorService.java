package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.model.Vendor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
public class VendorService {
    @Autowired
    private TicketSystemService ticketSystemService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(VendorService.class);


    @Async
    public void releaseTickets(String vendorName) {
        while (ticketSystemService.getTicketsAvailable() > 0) {
                    ticketSystemService.releaseTicket(vendorName);
                    try {
                        Thread.sleep((int) (2000)); // Simulate variable processing time
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
        }
    }

    @Async
    public void createVendor(Vendor vendor) {
        vendor.run();
    }
}
