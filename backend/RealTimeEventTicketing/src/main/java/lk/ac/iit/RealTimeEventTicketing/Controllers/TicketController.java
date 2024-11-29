package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.TicketPoolService;
import lk.ac.iit.RealTimeEventTicketing.Service.TicketService;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketPurchaseRequest;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketReleaseRequest;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketReserveRequest;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    private final TicketPoolService ticketPoolService;

    private TicketRepo ticketRepo;
    private TicketPurchaseRequest request;
    private TicketReserveRequest reserveRequest;

    private Ticket ticket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors


    @Autowired
    public TicketController(TicketService ticketService, TicketPoolService ticketPoolService, TicketRepo ticketRepo) {
        this.ticketService = ticketService;
        this.ticketPoolService = ticketPoolService;
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/vendor/{vendorId}/add")
    public ResponseEntity<String> addTicket(@PathVariable Long vendorId, @RequestBody TicketReleaseRequest releaseRequest) {
        try {
            // Validate the ticketsPerRelease parameter
            if (releaseRequest.getTicketsPerRelease() <= 0) {
                return new ResponseEntity<>("Invalid number of tickets per release.", HttpStatus.BAD_REQUEST);
            }

            // Submit the task to executorService to handle it in a separate thread
            executorService.submit(() -> ticketService.releaseTickets(vendorId, releaseRequest));

            return new ResponseEntity<>("Ticket release process started.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while releasing tickets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/vendor/{vendorId}/events/{eventId}/add")
//    public ResponseEntity<String> addTicket(@PathVariable Long vendorId, @PathVariable Long eventId,
//                                            @RequestBody TicketReleaseRequest releaseRequest) {
//        try {
//            // Validate the ticketsPerRelease parameter
//            if (releaseRequest.getTicketsPerRelease() <= 0) {
//                return new ResponseEntity<>("Invalid number of tickets per release.", HttpStatus.BAD_REQUEST);
//            }
//
//            // Submit the task to executorService to handle it in a separate thread
//            executorService.submit(() -> ticketService.releaseTickets(vendorId, eventId, releaseRequest));
//
//            return new ResponseEntity<>("Ticket release process started.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error while releasing tickets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }

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

    @GetMapping("/available")
    public ResponseEntity<Map<String, Object>> getAvailableTickets() {
        Map<String, Object> response = ticketPoolService.getAvailableTickets();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reserve/{ticketId}")
    public ResponseEntity<String> reserveTicket(@PathVariable Long ticketId, @RequestBody TicketReserveRequest request) {
        try {
            Ticket ticket = ticketPoolService.reserveTicket(ticketId, request.getCustomerId());
            if (ticket != null) {
                return ResponseEntity.ok("Ticket " + ticketId + " reserved successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket reservation failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to finalize the sale and update the ticket as sold
    @PostMapping("/finalize/{ticketId}")
    public ResponseEntity<String> finalizeSale(@PathVariable Long ticketId, @RequestBody TicketPurchaseRequest request) {
        try {
            ticketPoolService.finalizeSale(ticketId, request.getCustomerId());
            return ResponseEntity.ok("Ticket " + ticketId + " has been sold.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get all tickets in the pool
//    @GetMapping("/all")
//    public ResponseEntity<List<Ticket>> getAllTickets() {
//        return ResponseEntity.ok(ticketPoolService.getAllTickets());
//    }

    // You can call this method to notify customers when new tickets are added

}
