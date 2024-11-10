package lk.ac.iit.RealTimeTicket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    private TicketSystemService ticketSystemService;

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
}
