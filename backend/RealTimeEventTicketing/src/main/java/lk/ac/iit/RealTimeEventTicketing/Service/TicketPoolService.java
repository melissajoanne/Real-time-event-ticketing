package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.ConfigLoader;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.repo.CustomerRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//@Service
//public class TicketPoolService {
//    private Config config;
//    ConfigLoader configLoader;
//    private Set<Long> interestedCustomers = new HashSet<>();
//    @Autowired
//    private CustomerRepo customerRepo;
//
//    private static int MAX_POOL_SIZE;// Example maximum pool size
//    private static final long RESERVATION_TIMEOUT_MS = 60000; // 1 minute timeout for reservation
//
//    private final TicketRepo ticketRepo;
//    private final LinkedList<Ticket> ticketPool = new LinkedList<>(); // Change to LinkedList
//    private final ConcurrentHashMap<Long, ReentrantLock> reservationLocks = new ConcurrentHashMap<>();
//
//    @Autowired
//    public TicketPoolService(TicketRepo ticketRepo, ConfigLoader configLoader) {
//        this.ticketRepo = ticketRepo;
//        this.MAX_POOL_SIZE = configLoader.getAppConfig().getMaxTicketCapacity();
//    }
//
//    // Add tickets to the pool (Producer)
//    // Add a ticket to the pool temporarily (Producer method)
//    public synchronized void addTicketToPool(Ticket ticket) {
//        // Wait if the pool is full
//        while (ticketPool.size() >= MAX_POOL_SIZE) {
//            try {
//                System.out.println("Ticket pool is full, waiting to add more tickets...");
//                wait(); // Wait until there's space in the pool
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new IllegalStateException("Failed to add ticket to the pool", e);
//            }
//        }
//
//        boolean wasEmpty = ticketPool.isEmpty();
//        ticketPool.addLast(ticket); // Add to the end of the pool
//        reservationLocks.put(ticket.getTicketId(), new ReentrantLock());
//        System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");
//
//        // Notify all waiting customers that there is a new ticket available
//        if (wasEmpty) {
//            System.out.println("Notifying all waiting customers that a new ticket is available...");
//            notifyAll();
//        }
//    }
//
//
//    // Get all tickets in the pool
//    public Map<String, Object> getAvailableTickets() {
//        List<Ticket> availableTickets = ticketPool.stream()
//                .filter(ticket -> ticket.getStatus().equals("Available"))
//                .collect(Collectors.toList());
//
//        Map<String, Object> response = new HashMap<>();
//        if (availableTickets.isEmpty()) {
//            response.put("message", "No tickets are currently available.");
//            response.put("tickets", Collections.emptyList());
//        } else {
//            response.put("message", "Available tickets found.");
//            response.put("tickets", availableTickets);
//        }
//
//
//        return response;
//    }
//
//    public synchronized Ticket reserveNextAvailableTicket(Long customerId) {
//        // Validate if the customer exists in the system
//        if (!customerExists(customerId)) {
//            throw new IllegalStateException("Invalid customer ID: " + customerId);
//        }
//        // Wait if the pool is empty
//        while (ticketPool.isEmpty()) {
//            try {
//                System.out.println("Ticket pool is empty, waiting for tickets...");
//                // Return a message to the customer about the unavailability of tickets
//                throw new IllegalStateException("No tickets available at the moment. Please try again later.");
//            } catch (IllegalStateException e) {
//                // You can log the error or handle it differently, but it will let the customer know.
//                System.out.println(e.getMessage());
//                throw e; // Re-throw the exception so that the caller can handle it (e.g., show a message to the customer)
//            }
//        }
//
//        // Look for the first available ticket
//        Optional<Ticket> ticketOptional = ticketPool.stream()
//                .filter(ticket -> "Available".equals(ticket.getStatus()))
//                .findFirst();
//
//        if (ticketOptional.isEmpty()) {
//            // No tickets are available in the pool
//            throw new IllegalStateException("No available tickets at the moment.");
//        }
//
//        Ticket ticket = ticketOptional.get();
//        ReentrantLock lock = reservationLocks.get(ticket.getTicketId());
//
//        // Lock the ticket to ensure it's not modified by another thread during reservation
//        lock.lock();
//        try {
//            // Ensure the ticket is still available
//            if (!"Available".equals(ticket.getStatus())) {
//                throw new IllegalStateException("Ticket " + ticket.getTicketId() + " is no longer available.");
//            }
//
//            // Mark the ticket as reserved for the customer
//            ticket.setStatus("Reserved");
//            ticket.setCustomerId(customerId);
//            System.out.println("Ticket " + ticket.getTicketId() + " reserved for customer " + customerId);
//
//            // Start a separate thread to release the reservation if not finalized within 1 minute
//            new Thread(() -> {
//                try {
//                    // Wait for 1 minute before making the ticket available again
//                    TimeUnit.MINUTES.sleep(1);
//
//                    synchronized (ticket) {
//                        // Check if the ticket is still reserved
//                        if ("Reserved".equals(ticket.getStatus())) {
//                            ticket.setStatus("Available"); // Release the reservation
//                            ticket.setCustomerId(null); // Clear the customerId
//                            System.out.println("Ticket " + ticket.getTicketId() + " reservation expired.");
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }).start();
//
//            return ticket;
//
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    // Helper method to check if a customer exists in the database
//    private boolean customerExists(Long customerId) {
//        // Implement this method to query the customer database (e.g., using a repository)
//        return customerRepo.existsById(customerId);
//    }
//
//
//    public synchronized void finalizeSale(Long customerId) {
//        // Validate if the customer exists
//        if (!customerExists(customerId)) {
//            throw new IllegalStateException("Invalid customer ID: " + customerId);
//        }
//        // Find the reserved ticket for the customer
//        Optional<Ticket> ticketOptional = ticketPool.stream()
//                .filter(ticket -> ticket.getCustomerId().equals(customerId) && "Reserved".equals(ticket.getStatus()))
//                .findFirst();
//
//        if (ticketOptional.isEmpty()) {
//            throw new IllegalStateException("No reserved ticket found for customer " + customerId);
//        }
//
//        Ticket ticket = ticketOptional.get();
//
//        // Mark the ticket as sold
//        ticket.setStatus("Sold");
//
//        // Remove the ticket from the pool as it is sold
//        ticketPool.remove(ticket);
//        reservationLocks.remove(ticket.getTicketId());
//
//        // Update the ticket in the database
//        ticketRepo.save(ticket);
//
//        System.out.println("Ticket " + ticket.getTicketId() + " has been sold and removed from the pool.");
//
//        // Notify any waiting producers that a ticket has been sold and space is available in the pool
//        notifyAll();
//    }
//    public synchronized int countAvailableTickets() {
//        // Count tickets that are available
//        int availableTicketCount = 0;
//        for (Ticket ticket : ticketPool) {
//            if ("Available".equals(ticket.getStatus())) {
//                availableTicketCount++;
//            }
//        }
//        return availableTicketCount;
//    }
//
//}
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketPoolService {

    private final TicketRepo ticketRepo;
    private final int MAX_POOL_SIZE;
    private final ConcurrentLinkedQueue<Ticket> ticketPool = new ConcurrentLinkedQueue<>(); // Changed to ConcurrentLinkedQueue
    private final ConcurrentHashMap<Long, ReentrantLock> reservationLocks = new ConcurrentHashMap<>();
    private final CustomerRepo customerRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TicketPoolService(TicketRepo ticketRepo, ConfigLoader configLoader, CustomerRepo customerRepo, SimpMessagingTemplate messagingTemplate) {
        this.ticketRepo = ticketRepo;
        this.customerRepo = customerRepo;
        this.MAX_POOL_SIZE = configLoader.getAppConfig().getMaxTicketCapacity();
        this.messagingTemplate = messagingTemplate;
    }

    // Add tickets to the pool (Producer)
    public void addTicketToPool(Ticket ticket) {
        synchronized (this) {
            while (ticketPool.size() >= MAX_POOL_SIZE) {
                try {
                    System.out.println("Ticket pool is full, waiting to add more tickets...");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Failed to add ticket to the pool", e);
                }
            }
        }

        ticketPool.add(ticket);
        reservationLocks.put(ticket.getTicketId(), new ReentrantLock());
        System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");

        synchronized (this) {
            notifyAll();
        }
    }

    // Get all tickets in the pool
    public Map<String, Object> getAvailableTickets() {
        List<Ticket> availableTickets = ticketPool.stream()
                .filter(ticket -> ticket.getStatus().equals("Available"))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        if (availableTickets.isEmpty()) {
            response.put("message", "No tickets are currently available.");
            response.put("tickets", Collections.emptyList());
        } else {
            response.put("message", "Available tickets found.");
            response.put("tickets", availableTickets);
        }
        return response;
    }
    public void broadcastTicketCount(int availableTickets) {
        messagingTemplate.convertAndSend("/topic/ticket-count", availableTickets);
    }

    // Reserve the next available ticket
    public Ticket reserveNextAvailableTicket(Long customerId) {
        if (!customerExists(customerId)) {
            throw new IllegalStateException("Invalid customer ID: " + customerId);
        }

        Ticket ticket = ticketPool.stream()
                .filter(t -> "Available".equals(t.getStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No tickets available at the moment."));

        ReentrantLock lock = reservationLocks.get(ticket.getTicketId());
        lock.lock();
        try {
            if (!"Available".equals(ticket.getStatus())) {
                throw new IllegalStateException("Ticket " + ticket.getTicketId() + " is no longer available.");
            }

            ticket.setStatus("Reserved");
            ticket.setCustomerId(customerId);
            System.out.println("Ticket " + ticket.getTicketId() + " reserved for customer " + customerId);

            new Thread(() -> {
                try {
                    TimeUnit.MINUTES.sleep(1);
                    synchronized (ticket) {
                        if ("Reserved".equals(ticket.getStatus())) {
                            ticket.setStatus("Available");
                            ticket.setCustomerId(null);
                            System.out.println("Ticket " + ticket.getTicketId() + " reservation expired.");
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return ticket;
        } finally {
            lock.unlock();
        }
    }

    // Finalize ticket sale
    public void finalizeSale(Long customerId) {
        if (!customerExists(customerId)) {
            throw new IllegalStateException("Invalid customer ID: " + customerId);
        }

        Ticket ticket = ticketPool.stream()
                .filter(t -> customerId.equals(t.getCustomerId()) && "Reserved".equals(t.getStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No reserved ticket found for customer " + customerId));

        ticket.setStatus("Sold");
        ticketPool.remove(ticket);
        reservationLocks.remove(ticket.getTicketId());
        ticketRepo.save(ticket);

        System.out.println("Ticket " + ticket.getTicketId() + " has been sold and removed from the pool.");

        synchronized (this) {
            notifyAll();
        }
    }



    // Count available tickets
    public int countAvailableTickets() {
        return (int) ticketPool.stream()
                .filter(ticket -> "Available".equals(ticket.getStatus()))
                .count();
    }

    // Helper method to check if a customer exists
    private boolean customerExists(Long customerId) {
        return customerRepo.existsById(customerId);
    }
}




















