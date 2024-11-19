package lk.ac.iit.RealTimeTicket.model;
import jakarta.persistence.*;
import lk.ac.iit.RealTimeTicket.services.TicketService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
@DiscriminatorValue("Vendor")
public class Vendor extends User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long vendorId;
    @Setter
    @Getter
    private int ticketsPerRelease;
    @Setter
    @Getter
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

    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);

}



