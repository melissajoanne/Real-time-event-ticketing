package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.TicketPoolService;
import lk.ac.iit.RealTimeEventTicketing.Service.TicketService;
import lk.ac.iit.RealTimeEventTicketing.dto.*;
import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    private VendorRepo vendorRepo;

    private Ticket ticket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors


    @Autowired
    public TicketController(TicketService ticketService, TicketPoolService ticketPoolService, TicketRepo ticketRepo, VendorRepo vendorRepo) {
        this.ticketService = ticketService;
        this.ticketPoolService = ticketPoolService;
        this.ticketRepo = ticketRepo;
        this.vendorRepo = vendorRepo;
    }

//    @PostMapping("/vendor/{vendorId}/add")
//    public ResponseEntity<String> addTicket(@PathVariable Long vendorId, @RequestBody TicketReleaseRequest releaseRequest) {
//        try {
//            // Validate the ticketsPerRelease parameter
//            if (releaseRequest.getTicketsPerRelease() <= 0) {
//                return new ResponseEntity<>("Invalid number of tickets per release.", HttpStatus.BAD_REQUEST);
//            }
//
//            // Check if the vendor exists in the database
//            Optional<Vendor> vendor = vendorRepo.findById(vendorId);
//            if (!vendor.isPresent()) {
//                return new ResponseEntity<>("Vendor with ID " + vendorId + " not found.", HttpStatus.NOT_FOUND);
//            }
//
//            // Submit the task to executorService to handle it in a separate thread
//            executorService.submit(() -> ticketService.releaseTickets(vendorId, releaseRequest));
//
//            return new ResponseEntity<>("Ticket release process started.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error while releasing tickets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/vendor/release")
    public ResponseEntity<MessageResponse> addTicket(
            @RequestHeader("x-vendor-id") Long vendorId,
            @RequestBody TicketReleaseRequest releaseRequest) {
        try {
            if (releaseRequest.getTicketsPerRelease() <= 0) {
                // Return structured error response
                return new ResponseEntity<>(new MessageResponse("error", "Invalid number of tickets per release."), HttpStatus.BAD_REQUEST);
            }

            Optional<Vendor> vendor = vendorRepo.findById(vendorId);
            if (!vendor.isPresent()) {
                // Return structured error response
                return new ResponseEntity<>(new MessageResponse("error", "Vendor with ID " + vendorId + " not found."), HttpStatus.NOT_FOUND);
            }

            // Start the ticket release process asynchronously
            executorService.submit(() -> ticketService.releaseTickets(vendorId, releaseRequest));

            // Return a structured success response
            return new ResponseEntity<>(new MessageResponse("success", "Ticket release process started."), HttpStatus.OK);
        } catch (Exception e) {
            // Return structured error response for exception
            return new ResponseEntity<>(new MessageResponse("error", "Error while releasing tickets: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/count-available")
    public ResponseEntity<Integer> getCountTickets() {
        int availableTickets = ticketPoolService.countAvailableTickets();
        return new ResponseEntity<>(availableTickets, HttpStatus.OK);
    }


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


//    @PostMapping("/reserve/{ticketId}")
//    public ResponseEntity<String> reserveTicket(@PathVariable Long ticketId, @RequestBody TicketReserveRequest request) {
//        try {
//            Ticket ticket = ticketPoolService.reserveTicket(ticketId, request.getCustomerId());
//            if (ticket != null) {
//                return ResponseEntity.ok("Ticket " + ticketId + " reserved successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket reservation failed.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    // Endpoint to finalize the sale and update the ticket as sold
//    @PostMapping("/finalize/{ticketId}")
//    public ResponseEntity<String> finalizeSale(@PathVariable Long ticketId, @RequestBody TicketPurchaseRequest request) {
//        try {
//            ticketPoolService.finalizeSale(ticketId, request.getCustomerId());
//            return ResponseEntity.ok("Ticket " + ticketId + " has been sold.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    // Get all tickets in the pool
//    @GetMapping("/all")
//    public ResponseEntity<List<Ticket>> getAllTickets() {
//        return ResponseEntity.ok(ticketPoolService.getAllTickets());
//    }

    // You can call this method to notify customers when new tickets are added


//    @PostMapping("/reserve")
//    public ResponseEntity<Ticket> reserveTicket(@RequestBody TicketReserveRequest request) {
//        Long customerId = request.getCustomerId();
//        try {
//            Ticket reservedTicket = ticketPoolService.reserveNextAvailableTicket(customerId);
//            return ResponseEntity.ok(reservedTicket);
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }
//
//    @PostMapping("/finalize")
//    public ResponseEntity<String> finalizeSale(@RequestBody TicketPurchaseRequest request) {
//        Long customerId = request.getCustomerId();
//        try {
//            ticketPoolService.finalizeSale(customerId);
//            return ResponseEntity.ok("Sale finalized successfully.");
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to finalize sale: " + e.getMessage());
//        }
//    }

    // Retrieve all available tickets in the pool
    @GetMapping("/available")
    public ResponseEntity<Map<String, Object>> getAvailableTickets() {
        try {
            Map<String, Object> response = ticketPoolService.getAvailableTickets();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Error retrieving tickets: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reserve")
    public synchronized ResponseEntity<Ticket> reserveTicket(@RequestHeader("x-customer-id") Long customerId) {
        System.out.println("Received customerId: " + customerId);
        try {
            Ticket reservedTicket = ticketPoolService.reserveNextAvailableTicket(customerId);
            return ResponseEntity.ok(reservedTicket);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/finalize")
    public ResponseEntity<ResponseDto> finalizeSale(@RequestHeader("x-customer-id") Long customerId) {
        try {
            ticketPoolService.finalizeSale(customerId);
            return ResponseEntity.ok(new ResponseDto("Sale finalized successfully."));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed to finalize sale: " + e.getMessage()));

        }
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> countAvailableTickets() {
        try {
            int availableTickets = ticketPoolService.countAvailableTickets();
            ticketPoolService.broadcastTicketCount(availableTickets);
            return ResponseEntity.ok(availableTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }
}



