package lk.ac.iit.RealTimeEventTicketing.dto;

public class TicketReserveRequest {
    private Long CustomerId;
    private int ticketsToReserve;

    public TicketReserveRequest(Long customerId, int ticketsToReserve) {
        CustomerId = customerId;
        this.ticketsToReserve = ticketsToReserve;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    public int getTicketsToReserve() {
        return ticketsToReserve;
    }

    public void setTicketsToReserve(int ticketsToReserve) {
        this.ticketsToReserve = ticketsToReserve;
    }

    @Override
    public String toString() {
        return "TicketReserveRequest{" +
                "CustomerId=" + CustomerId +
                ", ticketsToReserve=" + ticketsToReserve +
                '}';
    }
}
