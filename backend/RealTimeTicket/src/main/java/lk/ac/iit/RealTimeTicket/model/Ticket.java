package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long ticketId;

    public Ticket(Vendor vendor) {
        this.vendor = vendor;
    }

    public Ticket() {

    }

    public Customer getCustomer() {
        return customer;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Foreign key in the Ticket table
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false) // Foreign key in the Ticket table
    private Vendor vendor;
}
