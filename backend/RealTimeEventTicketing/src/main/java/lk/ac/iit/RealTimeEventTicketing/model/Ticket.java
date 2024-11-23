package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long ticketId;
    private String eventName;

    public Ticket() {
    }

    public Ticket(String eventName) {
        this.eventName = eventName;
    }

    public Ticket(Long ticketId, String eventName) {
        this.ticketId = ticketId;
        this.eventName = eventName;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
