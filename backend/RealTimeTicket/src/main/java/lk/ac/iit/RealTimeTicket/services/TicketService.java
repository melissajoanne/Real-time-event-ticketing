package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.model.TicketPool;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketPool ticketPool;

    // Initialize the TicketPool with a specified number of tickets
    public TicketService() {
        this.ticketPool = new TicketPool(10); // You can set the number of tickets here
    }

    public String acquireTicket() throws InterruptedException {
        return ticketPool.acquireTicket();
    }

    public void releaseTicket(String ticket) throws InterruptedException {
        ticketPool.releaseTicket(ticket);
    }

    public int getAvailableTickets() {
        return ticketPool.availableTickets();
    }
}
