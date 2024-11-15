package lk.ac.iit.RealTimeTicket.model;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TicketPool {

    private final BlockingQueue<Ticket> ticketQueue;

    // Constructor to initialize the pool with a set number of tickets
    public TicketPool(Integer ticketPoolSize) {
        this.ticketQueue = new LinkedBlockingQueue<>(ticketPoolSize);
    }

    // Method to acquire a ticket, blocking if none are available
    public Ticket acquireTicket() throws InterruptedException {
        return ticketQueue.take();
    }

    // Method to release a ticket back to the pool
    public void releaseTicket(Ticket ticket) throws InterruptedException {
        ticketQueue.put(ticket);
    }

    // Method to get remaining capacity
    public int getRemainingCapacity() throws InterruptedException {
        return ticketQueue.remainingCapacity();
    }

    // Method to check available tickets
    public int availableTickets() {
        return ticketQueue.size();
    }

    public Queue<Ticket> getTicketQueue() { return ticketQueue; }


}