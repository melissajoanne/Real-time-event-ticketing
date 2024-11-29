package lk.ac.iit.RealTimeEventTicketing.Service;


import jakarta.transaction.Transactional;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolService {
    private final TicketRepo ticketRepo;

    private final List<Ticket> ticketPool = Collections.synchronizedList(new ArrayList<>());

    private static final long RESERVATION_TIMEOUT = 60 * 1000; // 1 minute in milliseconds
    private static final int MAX_POOL_SIZE = 1000; // Hardcoded pool size
    private final List<Ticket> pool = new ArrayList<>(MAX_POOL_SIZE);
    private final Map<Long, ReentrantLock> reservationLocks = new HashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 3; // Max requests per minute
    private static final long RATE_LIMIT_WINDOW_MS = 60 * 1000; // 1 minute window
    private final Map<Long, Queue<Long>> customerRequestTimestamps = new HashMap<>();


    public TicketPoolService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    // Add tickets to the pool
    public synchronized void addTicketToPool(Ticket ticket) {
        if (pool.size() < MAX_POOL_SIZE) {
            pool.add(ticket);
            reservationLocks.put(ticket.getTicketId(), new ReentrantLock());
            System.out.println("Added ticket " + ticket.getTicketId() + " to the pool.");
        } else {
            throw new IllegalStateException("Ticket pool is full");
        }
    }

//    // Reserve a ticket for a customer
//    public synchronized Ticket reserveTicket(Long ticketId) {
//        Optional<Ticket> ticketOptional = pool.stream()
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
//        // Try to acquire lock to prevent other customers from reserving the same ticket
//        boolean acquiredLock = lock.tryLock();
//        if (acquiredLock) {
//            try {
//                // Set a timeout for the reservation
//                Thread.sleep(RESERVATION_TIMEOUT);
//                return ticket;
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            } finally {
//                lock.unlock();
//            }
//        }
////         else {
////            throw new IllegalStateException("Ticket " + ticketId + " is already reserved by another customer.");
////        }
//        return null;

// Reserve a ticket for a customer
//
//public Ticket reserveTicket(Long ticketId) {
//    // Lock the ticket to prevent other threads from modifying it at the same time
//    ReentrantLock lock = reservationLocks.get(ticketId);
//
//    // Acquire the lock to ensure thread safety when modifying ticket status
//    lock.lock();
//    try {
//        // Look for the ticket in the pool with status "available"
//        Optional<Ticket> ticketOptional = pool.stream()
//                .filter(ticket -> ticket.getTicketId().equals(ticketId) && ticket.getStatus().equals("Available"))
//                .findFirst();
//
//        // If the ticket is not found or its status isn't "available", it's no longer available
//        if (ticketOptional.isEmpty()) {
//            throw new IllegalStateException("Ticket " + ticketId + " is not available.");
//        }
//
//        Ticket ticket = ticketOptional.get();
//
//        // Lock the ticket to safely update its status (synchronized access)
//        synchronized (ticket) {
//            // Mark the ticket as "reserved" temporarily (before finalizing purchase)
//            if ("Available".equals(ticket.getStatus())) {
//                ticket.setStatus("reserved");  // Mark as reserved
//            } else {
//                // If the ticket is not available, notify and return null
//                throw new IllegalStateException("Ticket " + ticketId + " is no longer available for reservation.");
//            }
//        }
//
//        // Simulate a reservation timeout (1 minute as specified)
//        long startTime = System.currentTimeMillis();
//        while (System.currentTimeMillis() - startTime < RESERVATION_TIMEOUT) {
//            // Polling for the purchase confirmation or timeout
//            if ("sold".equals(ticket.getStatus())) {
//                // If it's sold during the reservation, exit early
//                throw new IllegalStateException("Ticket " + ticketId + " has been sold.");
//            }
//        }
//
//        // After timeout, if still reserved and not sold, allow the reservation
//        if ("reserved".equals(ticket.getStatus())) {
//            return ticket;
//        }
//
//        return null;  // If ticket is sold during the reservation, return null
//
//    } finally {
//        lock.unlock();  // Always release the lock to avoid deadlocks
//    }
//}
// Reserve a ticket for a customer

    // Method to check if the customer exceeds their retrieval rate
    private boolean exceedsRateLimit(Long customerId) {
        Queue<Long> timestamps = customerRequestTimestamps.computeIfAbsent(customerId, k -> new LinkedList<>());

        long currentTime = System.currentTimeMillis();
        timestamps.offer(currentTime);

        // Remove timestamps that are older than the rate limit window
        while (!timestamps.isEmpty() && timestamps.peek() < currentTime - RATE_LIMIT_WINDOW_MS) {
            timestamps.poll();
        }

        // Check if the customer has exceeded the max allowed requests in the time window
        return timestamps.size() > MAX_REQUESTS_PER_MINUTE;
    }

    // Method to select a ticket to buy (Reservation phase)
    public Ticket selectTicketToBuy(Long ticketId, Long customerId) {
        // Check if the customer exceeds the retrieval rate
        if (exceedsRateLimit(customerId)) {
            throw new IllegalStateException("Customer " + customerId + " has exceeded the request rate limit.");
        }

        Optional<Ticket> ticketOptional = pool.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId) && ticket.getStatus().equals("Available"))
                .findFirst();

        if (ticketOptional.isEmpty()) {
            throw new IllegalStateException("Ticket " + ticketId + " is not available.");
        }

        Ticket ticket = ticketOptional.get();
        ReentrantLock lock = reservationLocks.computeIfAbsent(ticketId, id -> new ReentrantLock());

        // Try to acquire lock to prevent other customers from reserving the same ticket
        boolean acquiredLock = lock.tryLock();
        if (acquiredLock) {
            try {
                // Mark ticket as reserved temporarily for the customer
                if (!"Available".equals(ticket.getStatus())) {
                    throw new IllegalStateException("Ticket " + ticketId + " is already reserved or sold.");
                }
                ticket.setStatus("reserved");

                // Simulate reservation expiration after timeout (10 minutes)
                new Thread(() -> releaseReservationIfNotPurchased(ticketId)).start();

                return ticket;
            } catch (Exception e) {
                throw new RuntimeException("Error reserving ticket " + ticketId, e);
            } finally {
                lock.unlock(); // Always unlock the reservation lock after the operation
            }
        } else {
            throw new IllegalStateException("Ticket " + ticketId + " is already reserved by another customer.");
        }
    }

    // Release reservation if the ticket is not finalized within the timeout
    private void releaseReservationIfNotPurchased(Long ticketId) {
        try {
            TimeUnit.MILLISECONDS.sleep(RESERVATION_TIMEOUT);

            Optional<Ticket> ticketOptional = pool.stream()
                    .filter(ticket -> ticket.getTicketId().equals(ticketId) && "reserved".equals(ticket.getStatus()))
                    .findFirst();

            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                // Release the reservation and set it back to "Available"
                ticket.setStatus("Available");
                System.out.println("Ticket " + ticketId + " reservation expired and released back to available.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reservation release interrupted for ticket " + ticketId);
        }
    }

    @Transactional
    // Finalize the sale and mark the ticket as sold
    public synchronized void finalizeSale(Long ticketId, Long customerId) {
        // Check if the customer exceeds the retrieval rate before finalizing purchase
        if (exceedsRateLimit(customerId)) {
            throw new IllegalStateException("Customer " + customerId + " has exceeded the request rate limit.");
        }

        Optional<Ticket> ticketOptional = pool.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();

        if (ticketOptional.isEmpty()) {
            throw new IllegalStateException("Ticket " + ticketId + " not found in the pool.");
        }

        Ticket ticket = ticketOptional.get();

        // Ensure the ticket is reserved and not already sold
        if (!"reserved".equals(ticket.getStatus())) {
            throw new IllegalStateException("Ticket " + ticketId + " is not reserved or has already been sold.");
        }

        synchronized (ticket) {  // Ensure thread-safety when marking as sold
            if ("reserved".equals(ticket.getStatus())) {
                ticket.setStatus("sold");
                updateTicketStatus(ticketId, "sold");

                pool.remove(ticket);  // Remove from the pool as sold
                reservationLocks.remove(ticketId);  // Remove from lock map
                ticketRepo.save(ticket);  // Save the ticket in the database
                System.out.println("Ticket " + ticketId + " has been sold and removed from the pool.");
            } else {
                throw new IllegalStateException("Ticket " + ticketId + " is no longer reserved.");
            }
        }
    }

    // Method to update ticket status in the database (you'll need a service layer here)
    private void updateTicketStatus(Long ticketId, String status) {
        // Database update logic goes here
        // Assuming a TicketService that interacts with the database
        System.out.println("Updating ticket " + ticketId + " status to " + status);
    }

    // Get all available tickets in the pool
    public synchronized List<Ticket> getAllTickets() {
        return new ArrayList<>(pool);
    }

}









