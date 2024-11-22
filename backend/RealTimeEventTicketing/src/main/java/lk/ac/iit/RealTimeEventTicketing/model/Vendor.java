package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long vendorId;
    @Column(updatable = true)
    private String vendorName;

    public Vendor(Long vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

    public Vendor(String vendorName) {
        this.vendorName = vendorName;
    }

    public Vendor(){}

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}
