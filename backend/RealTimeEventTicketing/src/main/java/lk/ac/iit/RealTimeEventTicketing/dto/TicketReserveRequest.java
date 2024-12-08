package lk.ac.iit.RealTimeEventTicketing.dto;

public class TicketReserveRequest {
    private Long CustomerId;

    public TicketReserveRequest(Long customerId, int ticketsToReserve) {
        CustomerId = customerId;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }


    @Override
    public String toString() {
        return "TicketReserveRequest{" +
                "CustomerId=" + CustomerId +
                '}';
    }
}
