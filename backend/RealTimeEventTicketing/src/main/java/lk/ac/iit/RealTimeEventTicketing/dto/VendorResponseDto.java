package lk.ac.iit.RealTimeEventTicketing.dto;

public class VendorResponseDto{
    private String message;
    private Integer vendorId;

    public VendorResponseDto(String message, Integer vendorId) {
        this.message = message;
        this.vendorId = vendorId;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }
}
