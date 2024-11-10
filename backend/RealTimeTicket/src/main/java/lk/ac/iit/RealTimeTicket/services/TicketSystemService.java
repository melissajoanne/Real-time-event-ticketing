package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.TicketSystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TicketSystemService {
    private AtomicInteger ticketsAvailable;
    private AtomicInteger totalTickets;

    @Autowired
    private TicketSystemConfig ticketConfig;

    public TicketSystemService() {
        this.ticketsAvailable = new AtomicInteger(10); // Initialize with 10 tickets
        this.totalTickets = new AtomicInteger(10); // Initialize with 10 tickets

    }

    public boolean releaseTicket(String vendorName) {
        int currentTickets;
        do {
            currentTickets = ticketsAvailable.get();
            if (currentTickets <= 0) {
                System.out.println(vendorName + " tried to release a ticket but none are available.");
                return false;
            }
        } while (!ticketsAvailable.compareAndSet(currentTickets, currentTickets - 1));

        System.out.println(vendorName + " successfully released a ticket. Tickets left: " + ticketsAvailable.get());
        return true;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable.get();
    }
}
