package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Event;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.EventRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepo ticketRepo;

    private final VendorRepo vendorRepo;

    private final EventRepo eventRepo;

    private EventService eventService;


    @Autowired
    public TicketService(TicketRepo ticketRepo, VendorRepo vendorRepo, EventRepo eventRepo) {
        this.ticketRepo = ticketRepo;
        this.vendorRepo = vendorRepo;
        this.eventRepo = eventRepo;
    }

    //to handle the business logic of vendor releasing tickets
    public String addTicket(Ticket ticket) {
        try {
            ticketRepo.save(ticket);
            return "Ticket added successfully";
        } catch (Exception e) {
            logger.error("Error adding ticket", e);
            return "An error occurred while adding ticket";
        }
    }

    public String findById(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            return "Ticket not found";
        }
        return ticketOpt.get().toString();
    }

    public String updateTicket(Long ticketId, Ticket ticket) {
        Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            return "Ticket not found";
        }
        Ticket existingTicket = ticketOpt.get();
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setType(ticket.getType());
        ticketRepo.save(existingTicket);
        return "Ticket updated successfully";
    }

    public String deleteTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            return "Ticket not found";
        }
        ticketRepo.deleteById(ticketId);
        return "Ticket deleted successfully";
    }

    public List<Ticket> findAllTickets() {
        return ticketRepo.findAll();
    }
}


