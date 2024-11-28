package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long vendorId;

    private String vendorName;
    private String email;
    private int ticketReleaseInterval;

    public int getTicketReleaseInterval() {
        return ticketReleaseInterval;
    }

    public void setTicketReleaseInterval(int ticketReleaseInterval) {
        this.ticketReleaseInterval = ticketReleaseInterval;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long id) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String name) {
        this.vendorName = vendorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
