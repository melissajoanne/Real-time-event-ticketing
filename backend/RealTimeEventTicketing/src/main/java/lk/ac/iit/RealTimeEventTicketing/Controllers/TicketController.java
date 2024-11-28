package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.TicketPoolService;
import lk.ac.iit.RealTimeEventTicketing.Service.TicketService;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketReleaseRequest;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    private final TicketPoolService ticketPoolService;

    private TicketRepo ticketRepo;

    private Ticket ticket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors


    @Autowired
    public TicketController(TicketService ticketService, TicketPoolService ticketPoolService, TicketRepo ticketRepo) {
        this.ticketService = ticketService;
        this.ticketPoolService = ticketPoolService;
        this.ticketRepo = ticketRepo;
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

//    @PostMapping("/vendor/{vendorId}/events/{eventId}/add")
//    public ResponseEntity<String> addTicket(@PathVariable Long vendorId, @PathVariable Long eventId,
//                                            @RequestBody TicketReleaseRequest releaseRequest) {
//        try {
//            if (releaseRequest.getTicketsPerRelease() <= 0) {
//                return new ResponseEntity<>("Invalid number of tickets per release.", HttpStatus.BAD_REQUEST);
//            }
//
//            // Get the vendor's ticket release configuration from the database, if necessary
//            // For example: Long ticketReleaseInterval = vendorService.getTicketReleaseInterval(vendorId);
//
//            for (int i = 0; i < releaseRequest.getTicketsPerRelease(); i++) {
//                Ticket ticket = new Ticket();
//                ticket.setVendorId(vendorId);
//                ticket.setEventId(eventId);
//                ticket.setStatus("available");
//                ticket.setType(releaseRequest.getTicketType());  // Use the type from the request
//                ticket.setPrice(releaseRequest.getTicketPrice());  // Use the price from the request
//
//                ticketService.addTicket(ticket); // Ensure this method is implemented correctly
//            }
//
//            return new ResponseEntity<>("Tickets successfully released.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error while releasing tickets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    @PostMapping("/vendor/{vendorId}/events/{eventId}/add")
    public ResponseEntity<String> addTicket(@PathVariable Long vendorId, @PathVariable Long eventId,
                                            @RequestBody TicketReleaseRequest releaseRequest) {
        try {
            // Validate the ticketsPerRelease parameter
            if (releaseRequest.getTicketsPerRelease() <= 0) {
                return new ResponseEntity<>("Invalid number of tickets per release.", HttpStatus.BAD_REQUEST);
            }

            // Submit the task to executorService to handle it in a separate thread
            executorService.submit(() -> ticketService.releaseTickets(vendorId, eventId, releaseRequest));

            return new ResponseEntity<>("Ticket release process started.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while releasing tickets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    private void releaseTickets(Long vendorId, Long eventId, TicketReleaseRequest releaseRequest) {
//        try {
//            // Ensure thread-safety by synchronizing ticket release
//            synchronized (this) {
//                // Increment vendor count (each vendor releasing tickets)
//                ticketService.incrementVendorCountForEvent(eventId);
//
//                // Add the specified number of tickets
//                for (int i = 0; i < releaseRequest.getTicketsPerRelease(); i++) {
//                    Ticket ticket = new Ticket();
//                    ticket.setVendorId(vendorId);
//                    ticket.setEventId(eventId);
//                    ticket.setStatus("available");
//                    ticket.setType(releaseRequest.getTicketType());
//                    ticket.setPrice(releaseRequest.getTicketPrice());
//
//                    // Save the ticket to the database (ensure unique ticket ID)
//                    ticketService.releaseTickets(vendorId, eventId, releaseRequest);
//                }
//
//                // Decrement vendor count after ticket release
//                ticketService.decrementVendorCountForEvent(eventId);
//            }
//
//        } catch (Exception e) {
//            // Handle error and ensure vendor count is decremented in case of failure
//            ticketService.decrementVendorCountForEvent(eventId);
//            throw new RuntimeException("Error while releasing tickets: " + e.getMessage(), e);
//        }
//    }

    @GetMapping("/find/{ticketId}")
public ResponseEntity<String> findTicketById(@PathVariable Long ticketId) {
    return new ResponseEntity<>(ticketService.findById(ticketId), HttpStatus.OK);
}

@PutMapping("/update/{ticketId}")
public ResponseEntity<String> updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
    return new ResponseEntity<>(ticketService.updateTicket(ticketId, ticket), HttpStatus.OK);
}

@DeleteMapping("/delete/{ticketId}")
public ResponseEntity<String> deleteTicket(@PathVariable Long ticketId) {
    return new ResponseEntity<>(ticketService.deleteTicket(ticketId), HttpStatus.OK);
}
}

