package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Config;
import lk.ac.iit.RealTimeEventTicketing.Service.*;
import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketRepo;
import lk.ac.iit.RealTimeEventTicketing.repo.VendorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorControllers {
    private static final Logger logger = LoggerFactory.getLogger(VendorControllers.class);

    private final VendorService vendorService;

    private final TicketService ticketService;
    private final VendorRepo vendorRepo;

    private final TicketRepo ticketRepo;
    private final Config config;
    private Ticket ticket;

    @Autowired
    public VendorControllers(VendorService vendorService, TicketService ticketService, VendorRepo vendorRepo, TicketRepo ticketRepo, Config config) {
        this.vendorService = vendorService;

        this.ticketService = ticketService;
        this.vendorRepo = vendorRepo;

        this.ticketRepo = ticketRepo;
        this.config = config;
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
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(vendor);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable("vendorId") Long vendorId) {
        vendorService.deleteVendorById(vendorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}












