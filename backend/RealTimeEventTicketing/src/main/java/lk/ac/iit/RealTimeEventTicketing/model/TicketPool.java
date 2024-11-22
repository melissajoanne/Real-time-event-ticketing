package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;

@Entity
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long ticketId;
    private String ticketName;

    public TicketPool(Long ticketId, String ticketName) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
    }

    public TicketPool(String ticketName) {
        this.ticketName = ticketName;
    }
    public TicketPool() {}

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    @Override
    public String toString() {
        return "TicketPool{" +
                "ticketId=" + ticketId +
                ", ticketName='" + ticketName + '\'' +
                '}';
    }
}
