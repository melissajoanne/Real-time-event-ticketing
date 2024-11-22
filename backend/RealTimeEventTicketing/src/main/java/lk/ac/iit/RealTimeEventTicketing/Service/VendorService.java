package lk.ac.iit.RealTimeEventTicketing.Service;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    private final VendorRepo vendorRepo;

    @Autowired
    public VendorService(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    public Vendor addVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }

    public Vendor updateVendor(Vendor vendor) {
        return vendorRepo.save(vendor);
    }

    public Vendor findVendorById(Long VendorId) {
        return vendorRepo.findById(VendorId).orElse(null);
    }
    public List<Vendor> findAllVendors() {
        return vendorRepo.findAll();
    }

   public void deleteVendorById(Long VendorId) {
        vendorRepo.deleteById(VendorId);
   }



    }

