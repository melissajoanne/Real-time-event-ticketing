package lk.ac.iit.RealTimeTicket.controllers;

import lk.ac.iit.RealTimeTicket.model.Vendor;
import lk.ac.iit.RealTimeTicket.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/")
    public String createVendor(@RequestBody Vendor vendor) throws InterruptedException {
        vendorService.releaseTickets(vendor.getVendorId());
        return "Ticket release started for multiple vendors!";
    }

}
