package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.ConfigLoader;
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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    private ConfigLoader configLoader;
    private Config appConfig;

    private Ticket ticket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors


    @Autowired
    public TicketController(TicketService ticketService, TicketPoolService ticketPoolService, TicketRepo ticketRepo, VendorRepo vendorRepo) {
        this.ticketService = ticketService;
        this.ticketPoolService = ticketPoolService;
        this.ticketRepo = ticketRepo;
        this.vendorRepo = vendorRepo;
    }

    @PostMapping("/vendor/release")
    public ResponseEntity<MessageResponse> addTicket(
            @RequestHeader("x-vendor-id") Long vendorId,
            @RequestBody TicketReleaseRequest releaseRequest) {
        try {
            if (releaseRequest.getTicketsPerRelease() <= 0) {
                return new ResponseEntity<>(new MessageResponse("error", "Invalid number of tickets per release."), HttpStatus.BAD_REQUEST);
            }

            Optional<Vendor> vendor = vendorRepo.findById(vendorId);
            if (!vendor.isPresent()) {
                // Return structured error response
                return new ResponseEntity<>(new MessageResponse("error", "Vendor with ID " + vendorId + " not found."), HttpStatus.NOT_FOUND);
            }

            // Start the ticket release process asynchronously
            executorService.submit(() -> ticketService.releaseTickets(vendorId, releaseRequest));

            return new ResponseEntity<>(new MessageResponse("success", "Ticket release process started."), HttpStatus.OK);
        } catch (Exception e) {
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
    public synchronized ResponseEntity<Object> reserveTicket(@RequestHeader("x-customer-id") Long customerId) {
        System.out.println("Received customerId: " + customerId);
        try {
            Ticket reservedTicket = ticketPoolService.reserveNextAvailableTicket(customerId);
            return ResponseEntity.ok(reservedTicket);
        } catch (IllegalStateException e) {
            // Return a meaningful error message when no tickets are available
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("No tickets available in the pool."));
        }
    }

    // error response
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
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
            //ticketPoolService.broadcastTicketCount(availableTickets);
            return ResponseEntity.ok(availableTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @GetMapping("/maxTicketCapacity")
    public ResponseEntity<Integer> getMaxTicketCapacity() {
        try {
            int maxTicketCapacity = ticketPoolService.MAX_POOL_SIZE;
            return ResponseEntity.ok(maxTicketCapacity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> findAllTickets() {
        return new ResponseEntity<>(ticketService.findAllTickets(), HttpStatus.OK);
    }


}



