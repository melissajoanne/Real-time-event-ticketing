package lk.ac.iit.RealTimeEventTicketing.model;

import jakarta.persistence.*;
import lk.ac.iit.RealTimeEventTicketing.Service.TicketPoolService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long VendorId;
    private String VendorName;

    public Vendor() {
    }

    public Long getVendorId() {
        return VendorId;
    }

    public void setVendorId(Long vendorId) {
        VendorId = vendorId;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }


}
