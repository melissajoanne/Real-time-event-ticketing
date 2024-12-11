package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long customerId;

    public Customer(String name, String email, long customerId) {
        super(name, email);
        this.customerId = customerId;
    }

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    public Customer() {

    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


}

