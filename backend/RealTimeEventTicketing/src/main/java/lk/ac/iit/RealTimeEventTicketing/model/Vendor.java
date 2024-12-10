package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public  class Vendor extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long vendorId;
    private String CompanyName;
    public Vendor() {

    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Vendor(String name, String email, Long vendorId) {
        super(name, email);
        this.vendorId = vendorId;
    }

    public Vendor(Long vendorId) {
        this.vendorId = vendorId;
    }
}