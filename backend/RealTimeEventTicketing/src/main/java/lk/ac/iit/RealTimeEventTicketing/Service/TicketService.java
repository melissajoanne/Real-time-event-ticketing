package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.TicketStatus;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketReleaseRequest;
import lk.ac.iit.RealTimeEventTicketing.model.Event;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.EventRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
@EnableAsync
public class TicketService {

    private final TicketRepo ticketRepo;

    private final VendorRepo vendorRepo;

    private final EventRepo eventRepo;
    private final Config config;

    private EventService eventService;

    private Vendor vendor;
    private final AtomicInteger vendorCountForEvent = new AtomicInteger(0);

    private final ReentrantLock lock = new ReentrantLock();

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors

    private final ConcurrentHashMap<Long, ReentrantLock> eventLocks = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);


    @Autowired
    public TicketService(TicketRepo ticketRepo, VendorRepo vendorRepo, EventRepo eventRepo, Config config) {
        this.ticketRepo = ticketRepo;
        this.vendorRepo = vendorRepo;
        this.eventRepo = eventRepo;
        this.config = config;
    }


    public String addTicket(Ticket ticket) {
        return ticketRepo.save(ticket).toString();
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

    // Get the current vendor count for the event
    public int getCurrentVendorCountForEvent(Long eventId) {
        // In a real system, this should be stored in a database or a distributed cache like Redis
        return vendorCountForEvent.get();
    }

    // Increment vendor count for a specific event
    public void incrementVendorCountForEvent(Long eventId) {
        vendorCountForEvent.incrementAndGet();
    }

    // Decrement vendor count after ticket release
    public void decrementVendorCountForEvent(Long eventId) {
        vendorCountForEvent.decrementAndGet();
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> releaseTickets(Long vendorId, Long eventId, TicketReleaseRequest releaseRequest) {
        return CompletableFuture.runAsync(() -> {
            // Lock to ensure only one vendor releases tickets for an event at a time
            ReentrantLock eventLock = eventLocks.computeIfAbsent(eventId, k -> new ReentrantLock());

            long lastReleaseTime = System.currentTimeMillis(); // Track the last release time for the vendor

            try {
                incrementVendorCountForEvent(eventId); // Increase the counter for vendors releasing tickets

                for (int i = 0; i < releaseRequest.getTicketsPerRelease(); i++) {
                    // Ensure that the ticket release happens at the correct interval
                    synchronized (this) {
                        eventLock.lock(); // Lock event to ensure one vendor works at a time
                        try {
                            // Create a ticket
                            Ticket ticket = new Ticket();
                            ticket.setVendorId(vendorId);
                            ticket.setEventId(eventId);
                            ticket.setStatus("available");
                            ticket.setType(releaseRequest.getTicketType());
                            ticket.setPrice(releaseRequest.getTicketPrice());
                            addTicket(ticket); // Save the ticket to the database

                            // Log the ticket release
                            logger.info("Thread: {}, Vendor {} released ticket {} for event {}. Type: {}, Price: {}",
                                    Thread.currentThread().getName(), vendorId, ticket.getTicketId(), eventId, ticket.getType(), ticket.getPrice());

                            // Update last release time after each ticket is added
                            lastReleaseTime = System.currentTimeMillis();
                        } finally {
                            eventLock.unlock(); // Unlock the event after releasing the ticket
                        }

                        // Ensure the vendor waits the proper interval between ticket releases
                        long currentTime = System.currentTimeMillis();
                        long delayInMillis = releaseRequest.getTicketReleaseInterval() * 1000L; // Convert to milliseconds
                        long timeElapsed = currentTime - lastReleaseTime;

                        // If not enough time has passed, sleep for the remaining time
                        if (timeElapsed < delayInMillis) {
                            long sleepTime = delayInMillis - timeElapsed;
                            try {
                                Thread.sleep(sleepTime); // Wait for the remaining time
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                logger.error("Ticket release interrupted for vendor {} and event {}", vendorId, eventId, e);
                                throw new RuntimeException("Ticket release was interrupted.", e);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error while releasing tickets for vendor {} and event {}: {}", vendorId, eventId, e.getMessage(), e);
                throw new RuntimeException("Error while releasing tickets: " + e.getMessage(), e);
            } finally {
                decrementVendorCountForEvent(eventId); // Decrease vendor count when done
            }
        }, executorService);
    }



}












