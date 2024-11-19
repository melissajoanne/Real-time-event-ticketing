package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
@DiscriminatorValue("Customer")
public class Customer extends User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long customerId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @Setter
    @Getter
    private int retrievalInterval;

    public Customer() {
        super();
    }

    public Customer(String name, String email,
                    int retrievalInterval) {
        super(name, email);
        this.retrievalInterval = retrievalInterval;

    }


    public void setId(long customerId) {
        this.customerId = customerId;
    }

}
