package lk.ac.iit.RealTimeTicket.model;
import jakarta.persistence.*;
import lk.ac.iit.RealTimeTicket.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Vendor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;


    public Vendor(String name, String email, int ticketsPerRelease,
                  int releaseInterval) {
        super(name, email); // Calling the parent class constructor
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    public Vendor() {
        super();

    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);

}



