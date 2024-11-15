package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.Repo.VendorRepo;
import lk.ac.iit.RealTimeTicket.model.Customer;
import lk.ac.iit.RealTimeTicket.model.Ticket;
import lk.ac.iit.RealTimeTicket.model.Vendor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.UUID;

@Service
public class VendorService {


    @Autowired
    private TicketService ticketService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(VendorService.class);


    @Async
    public void acquireTicket(Long vendorName) throws InterruptedException {
        while (ticketService.getAvailableTickets() > 0) {
            System.out.println("ticketService.getAvailableTickets()"+ticketService.getAvailableTickets());

            Ticket acquiredTicket = ticketService.acquireTicket();
            System.out.println("vendorName : "+vendorName +" -> acquiredTicket : "+ acquiredTicket.getTicketId());
            try {
                Thread.sleep((int) (2000)); // Simulate variable processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Async
    public void releaseTickets(Vendor vendor) throws InterruptedException {
        int ticketsPerRelease = 0;
        while (ticketService.getRemainingCapacity() > 0) {
            Ticket ticket = new Ticket(vendor);
            ticketService.releaseTicket(ticket);

            if (vendor.getTicketsPerRelease() == ticketsPerRelease) {
                break;
            }
            ticketsPerRelease ++;
            int availTickets = ticketService.getAvailableTickets();
            System.out.println("vendorName : "+ticket.getVendor().getName() +" -> Available Tickets : "+ availTickets);
            try {
                Thread.sleep((int) (2000)); // Simulate variable processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
//
//        ticketService.getTicketQueue().stream().forEach(ticket ->
//                System.out.println("Ticket ID: " + ticket.toString()+ " vendor :" + ticket.getVendor().getName()));
    }

}
