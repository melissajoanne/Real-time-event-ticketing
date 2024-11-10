package lk.ac.iit.RealTimeTicket.controllers;

import lk.ac.iit.RealTimeTicket.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketReleaseController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/release")
    public String releaseTickets() {
        vendorService.releaseTickets("Vendor1");
        vendorService.releaseTickets("Vendor2");
        vendorService.releaseTickets("Vendor3");

        return "Ticket release started for multiple vendors!";
    }
}
