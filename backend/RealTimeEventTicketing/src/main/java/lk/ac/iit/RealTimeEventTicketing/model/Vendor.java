package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    private String vendorName;
    private int ticketReleaseRate;

    public Vendor() {
    }

    public Vendor(String vendorName, int ticketReleaseRate) {
        this.vendorName = vendorName;
        this.ticketReleaseRate = ticketReleaseRate;

    }
}

