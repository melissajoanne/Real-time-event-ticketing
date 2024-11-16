package lk.ac.iit.RealTimeTicket.services;

import lk.ac.iit.RealTimeTicket.model.Customer;
import lk.ac.iit.RealTimeTicket.model.Ticket;
import lk.ac.iit.RealTimeTicket.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * Asynchronously acquires tickets for a customer until no tickets are available.
     *
     * @param customerName the name of the customer
     */
    @Async
    public void acquireTickets(String customerName) {
        logger.info("Customer {} started acquiring tickets.", customerName);

        while (ticketService.getAvailableTickets() > 0) {
            try {
                Ticket acquiredTicket = ticketService.acquireTicket();
                logger.info("Customer {} acquired ticket ID: {}", customerName, acquiredTicket.getTicketId());

                // Simulate processing time
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Thread interrupted while acquiring tickets for customer: {}", customerName, e);
                return;
            } catch (Exception e) {
                logger.error("An error occurred while acquiring tickets for customer: {}", customerName, e);
                return;
            }
        }

        logger.info("Customer {} has finished acquiring tickets. No tickets available.", customerName);
    }
}
