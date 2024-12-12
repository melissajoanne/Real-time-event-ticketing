package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.ConfigLoader;
import lk.ac.iit.RealTimeEventTicketing.dto.TicketReleaseRequest;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


@Service
@EnableAsync
public class TicketService {

    private final TicketRepo ticketRepo;

    private final VendorRepo vendorRepo;

    private final Config config;
    private final TicketPoolService ticketPoolService;
    private final ConfigLoader configLoader;


    private final ExecutorService executorService = Executors.newFixedThreadPool(10);  // Limit of 10 vendors

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);


    @Autowired
    public TicketService(TicketRepo ticketRepo, VendorRepo vendorRepo, Config config, TicketPoolService ticketPoolService, ConfigLoader configLoader) {
        this.ticketRepo = ticketRepo;
        this.vendorRepo = vendorRepo;
        this.config = config;
        this.ticketPoolService = ticketPoolService;
        this.configLoader = configLoader;
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

    @Async("taskExecutor")
    public CompletableFuture<Void> releaseTickets(Long vendorId, TicketReleaseRequest releaseRequest) {
        if (!ticketPoolService.isRunning()) {
            throw new IllegalStateException("Ticket handling operations are currently stopped.");
        }

        return CompletableFuture.runAsync(() -> {
            try {
                for (int i = 0; i < releaseRequest.getTicketsPerRelease(); i++) {
                    // Create a new ticket
                    Ticket ticket = new Ticket();
                    ticket.setVendorId(vendorId);
                    ticket.setStatus("Available");
                    ticket.setType(releaseRequest.getTicketType());
                    ticket.setPrice(releaseRequest.getTicketPrice());

                    addTicket(ticket); // Save the ticket to the database
                    ticketPoolService.addTicketToPool(ticket); // Add the ticket to the pool

                    logger.info("Thread: {}, Vendor {} released ticket {}. Type: {}, Price: {}", Thread.currentThread().getName(), vendorId, ticket.getTicketId(), ticket.getType(), ticket.getPrice());

                    // Sleep for the configured interval
                    Thread.sleep(configLoader.getAppConfig().getTicketReleaseRate() * 1000L);
                }
            } catch (Exception e) {
                logger.error("Error while releasing tickets for vendor {}: {}", vendorId, e.getMessage(), e);
                throw new RuntimeException("Error while releasing tickets: " + e.getMessage(), e);
            }
        }, executorService);
    }

}

















