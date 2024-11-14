package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Customer implements Runnable {
    @Id
    private long customerId;
    private int retrievalInterval;


    public Customer(@Value("${customer.customerId}") long customerId,
    @Value("${customer.retrievalInterval}") int retrievalInterval) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
    }

    public long getId() {
        return customerId;
    }

    public void setId(long customerId) {
        this.customerId = customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    @Override
    public void run() {
        logger.info("Running with ID: " + customerId + ", Retrieval Interval: " + retrievalInterval);
    }
}
