package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Ticket addTicket (Ticket ticket) {
        return ticketRepo.save(ticket);
    }
    public Ticket updateTicket (Ticket ticket) {
        return ticketRepo.save(ticket);
    }
    public void deleteTicket (Ticket ticket) {
        ticketRepo.delete(ticket);
    }
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
    public Ticket getTicketById(Long ticketId) {
        return ticketRepo.findById(ticketId).orElse(null);
    }


}
