package lk.ac.iit.RealTimeTicket.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TicketPool {

    private final BlockingQueue<String> ticketQueue;

    // Constructor to initialize the pool with a set number of tickets
    public TicketPool(Integer ticketPoolSize) {
        this.ticketQueue = new LinkedBlockingQueue<>(ticketPoolSize);
        for (int i = 1; i <= ticketPoolSize; i++) {
            ticketQueue.offer("Ticket-" + i);
        }
    }

    // Method to acquire a ticket, blocking if none are available
    public String acquireTicket() throws InterruptedException {
        return ticketQueue.take();
    }

    // Method to release a ticket back to the pool
    public void releaseTicket(String ticket) throws InterruptedException {
        ticketQueue.put(ticket);
    }

    // Method to check available tickets
    public int availableTickets() {
        return ticketQueue.size();
    }
}