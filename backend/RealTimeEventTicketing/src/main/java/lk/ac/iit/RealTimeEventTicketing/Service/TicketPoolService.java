package lk.ac.iit.RealTimeEventTicketing.Service;

import jakarta.transaction.Transactional;
import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.ConfigLoader;
import lk.ac.iit.RealTimeEventTicketing.TicketPool;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
//@Service
//public class TicketPoolService {
//
//    private final TicketRepo ticketRepo;
//
//    private final List<Ticket> ticketPool = new ArrayList<>(); // List to simulate the pool (for example)
//    private static final int MAX_POOL_SIZE = 1000; // Max size 1000
//
//    private static final long RESERVATION_TIMEOUT_MS = 60 * 1000; // 1-minute reservation timeout
//    private static final int MAX_REQUESTS_PER_MINUTE = 3; // Max requests per minute for rate-limiting
//    private static final long RATE_LIMIT_WINDOW_MS = 60 * 1000; // Rate-limit window duration
//
//    private final Map<Long, ReentrantLock> reservationLocks = new HashMap<>();
//    private final Map<Long, Queue<Long>> customerRequestTimestamps = new HashMap<>();
//
//    public TicketPoolService(TicketRepo ticketRepo) {
//        this.ticketRepo = ticketRepo;
//    }
//
//    // Add tickets to the pool (Producer)
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
//        ticketPool.add(ticket);
//        reservationLocks.put(ticket.getTicketId(), new ReentrantLock());
//        System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");
//
//        // Notify any waiting consumer that there is a new ticket available
//        notifyAll();
//    }
//
//    // Get all tickets in the pool
//    public synchronized List<Ticket> getAllTickets() {
//        return new ArrayList<>(ticketPool);
//    }
//
//    // Reserve a ticket for a customer (Consumer-like operation)
//    public synchronized Ticket reserveTicket(Long ticketId, Long customerId) {
//        // Wait if the pool is empty
//        while (ticketPool.isEmpty()) {
//            try {
//                System.out.println("Ticket pool is empty, waiting for tickets...");
//                wait(); // Wait until there's a ticket in the pool
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new IllegalStateException("Failed to reserve ticket", e);
//            }
//        }
//
//        // Look for the ticket in the pool with status "Available"
//        Optional<Ticket> ticketOptional = ticketPool.stream()
//                .filter(ticket -> ticket.getTicketId().equals(ticketId) && ticket.getStatus().equals("Available"))
//                .findFirst();
//
//        if (ticketOptional.isEmpty()) {
//            throw new IllegalStateException("Ticket " + ticketId + " is not available.");
//        }
//
//        Ticket ticket = ticketOptional.get();
//        ReentrantLock lock = reservationLocks.get(ticketId);
//
//        // Lock the ticket to ensure it's not modified by another thread during reservation
//        lock.lock();
//        try {
//            // Ensure the ticket is not already reserved
//            if (!"Available".equals(ticket.getStatus())) {
//                throw new IllegalStateException("Ticket " + ticketId + " is already reserved or sold.");
//            }
//
//            // Mark the ticket as reserved for the customer
//            ticket.setStatus("Reserved");
//            ticket.setCustomerId(customerId);
//            System.out.println("Ticket " + ticketId + " reserved for customer " + customerId);
//
//            // Start a separate thread to release the reservation if not finalized within the timeout
//            new Thread(() -> releaseReservationIfNotPurchased(ticketId)).start();
//            return ticket;
//
//        } finally {
//            lock.unlock();
//        }
//    }

//    // Release reservation if not purchased within timeout
//    private void releaseReservationIfNotPurchased(Long ticketId) {
//        try {
//            // Wait for 1 minute to see if the purchase is finalized
//            TimeUnit.MILLISECONDS.sleep(RESERVATION_TIMEOUT_MS); // 1 minute timeout
//
//            synchronized (this) {
//                Optional<Ticket> ticketOptional = ticketPool.stream()
//                        .filter(ticket -> ticket.getTicketId().equals(ticketId) && "Reserved".equals(ticket.getStatus()))
//                        .findFirst();
//
//                if (ticketOptional.isPresent()) {
//                    Ticket ticket = ticketOptional.get();
//                    // If the ticket is still reserved, release it and set it back to available
//                    ticket.setStatus("Available");
//                    ticket.setCustomerId(null);  // Clear the customerId
//                    System.out.println("Ticket " + ticketId + " reservation expired and is now available.");
//
//                    // Notify any waiting producer (if the pool is not full) that a ticket has become available
//                    notifyAll();
//                }
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            System.err.println("Reservation release interrupted for ticket " + ticketId);
//        }
//    }
//
//    // Finalize sale for a ticket
//    public synchronized void finalizeSale(Long ticketId, Long customerId) {
//        Optional<Ticket> ticketOptional = ticketPool.stream()
//                .filter(ticket -> ticket.getTicketId().equals(ticketId))
//                .findFirst();
//
//        if (ticketOptional.isEmpty()) {
//            throw new IllegalStateException("Ticket " + ticketId + " not found in the pool.");
//        }
//
//        Ticket ticket = ticketOptional.get();
//
//        // Ensure the ticket is reserved and not already sold
//        if (!"Reserved".equals(ticket.getStatus())) {
//            throw new IllegalStateException("Ticket " + ticketId + " is not reserved.");
//        }
//
//        // Ensure that the ticket is reserved by the correct customer
//        if (!ticket.getCustomerId().equals(customerId)) {
//            throw new IllegalStateException("Ticket " + ticketId + " was reserved by another customer.");
//        }
//
//        synchronized (ticket) {
//            // Mark the ticket as sold
//            ticket.setStatus("Sold");
//
//            // Remove the ticket from the pool as it is sold
//            ticketPool.remove(ticket);
//            reservationLocks.remove(ticketId);
//
//            // Update the ticket in the database
//            ticketRepo.save(ticket);
//
//            System.out.println("Ticket " + ticketId + " has been sold and removed from the pool.");
//        }
//
//        // Notify any waiting producer that a ticket has been sold and space is available in the pool
//        notifyAll();
//    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolService {
    private Config config;
    ConfigLoader configLoader;

    private static int MAX_POOL_SIZE;// Example maximum pool size
    private static final long RESERVATION_TIMEOUT_MS = 60000; // 1 minute timeout for reservation

    private final TicketRepo ticketRepo;
    private final LinkedList<Ticket> ticketPool = new LinkedList<>(); // Change to LinkedList
    private final ConcurrentHashMap<Long, ReentrantLock> reservationLocks = new ConcurrentHashMap<>();

    @Autowired
    public TicketPoolService(TicketRepo ticketRepo, ConfigLoader configLoader) {
        this.ticketRepo = ticketRepo;
        this.MAX_POOL_SIZE = configLoader.getAppConfig().getMaxTicketCapacity();
    }

    // Add tickets to the pool (Producer)
    public synchronized void addTicketToPool(Ticket ticket) {
        // Wait if the pool is full
        while (ticketPool.size() >= MAX_POOL_SIZE) {
            try {
                System.out.println("Ticket pool is full, waiting to add more tickets...");
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Failed to add ticket to the pool", e);
            }
        }

        ticketPool.addLast(ticket); // Use addLast for LinkedList
        reservationLocks.put(ticket.getTicketId(), new ReentrantLock());
        System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");

        // Notify any waiting consumer that there is a new ticket available
        notifyAll();
    }

    // Get all tickets in the pool
    public synchronized List<Ticket> getAllTickets() {
        return new LinkedList<>(ticketPool); // Create a new LinkedList from the pool
    }

    // Reserve a ticket for a customer (Consumer-like operation)
    public synchronized Ticket reserveTicket(Long ticketId, Long customerId) {
        // Wait if the pool is empty
        while (ticketPool.isEmpty()) {
            try {
                System.out.println("Ticket pool is empty, waiting for tickets...");
                wait(); // Wait until there's a ticket in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Failed to reserve ticket", e);
            }
        }

        // Look for the ticket in the pool with status "Available"
        Optional<Ticket> ticketOptional = ticketPool.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId) && ticket.getStatus().equals("Available"))
                .findFirst();

        if (ticketOptional.isEmpty()) {
            throw new IllegalStateException("Ticket " + ticketId + " is not available.");
        }

        Ticket ticket = ticketOptional.get();
        ReentrantLock lock = reservationLocks.get(ticketId);

        // Lock the ticket to ensure it's not modified by another thread during reservation
        lock.lock();
        try {
            // Ensure the ticket is not already reserved
            if (!"Available".equals(ticket.getStatus())) {
                throw new IllegalStateException("Ticket " + ticketId + " is already reserved or sold.");
            }

            // Mark the ticket as reserved for the customer
            ticket.setStatus("Reserved");
            ticket.setCustomerId(customerId);
            System.out.println("Ticket " + ticketId + " reserved for customer " + customerId);

            // Start a separate thread to release the reservation if not finalized within the timeout
            new Thread(() -> releaseReservationIfNotPurchased(ticketId)).start();
            return ticket;

        } finally {
            lock.unlock();
        }
    }

    // Release reservation if not purchased within timeout
    private void releaseReservationIfNotPurchased(Long ticketId) {
        try {
            // Wait for 1 minute to see if the purchase is finalized
            TimeUnit.MILLISECONDS.sleep(RESERVATION_TIMEOUT_MS); // 1 minute timeout

            synchronized (this) {
                Optional<Ticket> ticketOptional = ticketPool.stream()
                        .filter(ticket -> ticket.getTicketId().equals(ticketId) && "Reserved".equals(ticket.getStatus()))
                        .findFirst();

                if (ticketOptional.isPresent()) {
                    Ticket ticket = ticketOptional.get();
                    // If the ticket is still reserved, release it and set it back to available
                    ticket.setStatus("Available");
                    ticket.setCustomerId(null);  // Clear the customerId
                    System.out.println("Ticket " + ticketId + " reservation expired and is now available.");

                    // Notify any waiting producer (if the pool is not full) that a ticket has become available
                    notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reservation release interrupted for ticket " + ticketId);
        }
    }

    // Finalize sale for a ticket
    public synchronized void finalizeSale(Long ticketId, Long customerId) {
        Optional<Ticket> ticketOptional = ticketPool.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();

        if (ticketOptional.isEmpty()) {
            throw new IllegalStateException("Ticket " + ticketId + " not found in the pool.");
        }

        Ticket ticket = ticketOptional.get();

        // Ensure the ticket is reserved and not already sold
        if (!"Reserved".equals(ticket.getStatus())) {
            throw new IllegalStateException("Ticket " + ticketId + " is not reserved.");
        }

        // Ensure that the ticket is reserved by the correct customer
        if (!ticket.getCustomerId().equals(customerId)) {
            throw new IllegalStateException("Ticket " + ticketId + " was reserved by another customer.");
        }

        synchronized (ticket) {
            // Mark the ticket as sold
            ticket.setStatus("Sold");

            // Remove the ticket from the pool as it is sold
            ticketPool.remove(ticket); // `remove` works efficiently with LinkedList
            reservationLocks.remove(ticketId);

            // Update the ticket in the database
            ticketRepo.save(ticket);

            System.out.println("Ticket " + ticketId + " has been sold and removed from the pool.");
        }

        // Notify any waiting producer that a ticket has been sold and space is available in the pool
        notifyAll();
    }
}














