package lk.ac.iit.RealTimeEventTicketing.Service;
import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VendorService {

    private final Config config;

    @Autowired
    private VendorRepo vendorRepo;
    public static final List<String> HARDCODED_EVENTS = Arrays.asList("Music Fest", "Tech Expo", "Sports Gala");

    public List<String> getAvailableEvents() {
        return HARDCODED_EVENTS;
    }


    private static final Logger logger=(Logger) LoggerFactory.getLogger(VendorService.class);

    public VendorService(Config config) {
        this.config = config;
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

