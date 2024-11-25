package lk.ac.iit.RealTimeEventTicketing.Service;


import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketPoolService {

//    // Thread-safe queue to store tickets
//    private Queue<Ticket> ticketQueue = new ConcurrentLinkedQueue<>();
//
//    // Add a ticket to the queue
//    public void addTicket(Ticket ticket) {
//        ticketQueue.add(ticket);
//        System.out.println("Ticket added: " + ticket.getTicketId());
//    }
//
//    // Remove a ticket from the queue
//    public Ticket removeTicket() {
//        Ticket ticket = ticketQueue.poll();
//        if (ticket != null) {
//            System.out.println("Ticket removed: " + ticket.getTicketId());
//        } else {
//            System.out.println("No tickets available to remove.");
//        }
//        return ticket;
//    }
//
//    // Get all available tickets
//    public Queue<Ticket> getTickets() {
//        return ticketQueue;
//    }
}