package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.model.Ticket;
import lk.ac.iit.RealTimeTicket.model.TicketPool;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class TicketService {

    private final TicketPool ticketPool;

    // Initialize the TicketPool with a specified number of tickets
    public TicketService() {
        this.ticketPool = new TicketPool(15); // You can set the number of tickets here
    }

    public Ticket acquireTicket() throws InterruptedException {
        return ticketPool.acquireTicket();

    }

    public void releaseTicket(Ticket ticket) throws InterruptedException {
        ticketPool.releaseTicket(ticket);
    }

    // Method to get remaining capacity
    public int getRemainingCapacity() throws InterruptedException {
        return ticketPool.getRemainingCapacity();
    }

    public int getAvailableTickets() {
        return ticketPool.availableTickets();
    }

    public Queue<Ticket> getTicketQueue() { return ticketPool.getTicketQueue(); }


}
