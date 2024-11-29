package lk.ac.iit.RealTimeEventTicketing.dto;

public class TicketPurchaseRequest {
    private Long CustomerId;

    public TicketPurchaseRequest(Long customerId) {
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
        return "TicketPurchaseRequest{" +
                "CustomerId=" + CustomerId +
                '}';
    }


}
