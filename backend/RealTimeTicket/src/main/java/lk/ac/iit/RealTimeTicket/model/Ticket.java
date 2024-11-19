package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long ticketId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Foreign key in the Ticket table
    private Customer customer;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false) // Foreign key in the Ticket table
    private Vendor vendor;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "status_ticket_id")
    private Ticket status;

    public void setStatus(Ticket status) {
        this.status = status;
    }

    public Ticket(Vendor vendor) {
        this.vendor = vendor;
    }

    public Ticket() {

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }


}
