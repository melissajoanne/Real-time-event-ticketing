package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long customerId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    private int retrievalInterval;

    public Customer() {
        super();
    }

    public Customer(String name, String email,
                    int retrievalInterval) {
        super(name, email);
        this.retrievalInterval = retrievalInterval;

    }



    public long getCustomerId() {
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

}
