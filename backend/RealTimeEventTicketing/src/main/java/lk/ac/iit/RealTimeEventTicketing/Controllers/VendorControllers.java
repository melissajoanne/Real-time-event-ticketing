package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.CustomerService;
import lk.ac.iit.RealTimeEventTicketing.Service.VendorService;
import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorControllers {
    private final VendorService vendorService;

    @Autowired
    public VendorControllers(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.findAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

@GetMapping("/find/{vendorId}")
public ResponseEntity<Vendor> findVendorById(@PathVariable("vendorId") Long vendorId) {
Vendor vendor = vendorService.findVendorById(vendorId);
return new ResponseEntity<>(vendor, HttpStatus.OK);
}

    @PostMapping("/add")
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        Vendor newVendor = vendorService.addVendor(vendor);
        return new ResponseEntity<>(newVendor, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Vendor> updateCustomer(@RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(vendor);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable("vendorId") Long vendorId) {
        vendorService.deleteVendorById(vendorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




