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

    @Autowired
    private TicketService ticketService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(VendorService.class);


    @Async
    public void releaseTickets(Long vendorName) throws InterruptedException {
        while (ticketService.getAvailableTickets() > 0) {
            System.out.println("ticketService.getAvailableTickets()"+ticketService.getAvailableTickets());

            String acquiredTicket = ticketService.acquireTicket();
            System.out.println("vendorName : "+vendorName +" -> acquiredTicket : "+acquiredTicket);
            try {
                Thread.sleep((int) (2000)); // Simulate variable processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
