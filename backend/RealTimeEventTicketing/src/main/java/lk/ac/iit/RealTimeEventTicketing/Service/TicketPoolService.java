package lk.ac.iit.RealTimeEventTicketing.Service;


import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketPoolService {

    private final List<Ticket> pool = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addTicketToPool(Ticket ticket) {
        if (pool.stream().noneMatch(existingTicket -> existingTicket.getTicketId().equals(ticket.getTicketId()))) {
            pool.add(ticket);
            System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");
        } else {
            throw new IllegalStateException("Duplicate ticket detected: " + ticket.getTicketId());
        }
        System.out.println(pool);
    }

        public synchronized List<Ticket> getAllTickets() {
            return new ArrayList<>(pool);
        }


    public synchronized boolean removeTicket(Long ticketId) {
        return pool.removeIf(ticket -> ticket.getTicketId().equals(ticketId));
    }

    public int getPoolSize() {
        return pool.size();
    }
}
