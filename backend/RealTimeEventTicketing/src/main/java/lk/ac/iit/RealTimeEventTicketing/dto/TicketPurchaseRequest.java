package lk.ac.iit.RealTimeEventTicketing.dto;

public class TicketPurchaseRequest {
    private Long CustomerId;

    public TicketPurchaseRequest(Long customerId) {
        CustomerId = customerId;
    }
    private boolean finalizePurchase;

    public boolean isFinalizePurchase() {
        return finalizePurchase;
    }

    public void setFinalizePurchase(boolean finalizePurchase) {
        this.finalizePurchase = finalizePurchase;
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
