package lk.ac.iit.RealTimeTicket.model;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Entity
public class Vendor implements Runnable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private long vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;


    public Vendor(@Value("${vendor.ticketsPerRelease}") int ticketsPerRelease,
                  @Value("${vendor.releaseInterval}") int releaseInterval) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    public Vendor() {

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

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Vendor.class);

    @Override
    public void run() {
        logger.info("Running with vendor ID: " + vendorId + ", Release Interval: " + releaseInterval + ", Tickets per Release: " + ticketsPerRelease);


        logger.info("Finished with vendor ID: " + vendorId + ", Release Interval: " + releaseInterval + ", Tickets per Release: " + ticketsPerRelease);


    }

    }


