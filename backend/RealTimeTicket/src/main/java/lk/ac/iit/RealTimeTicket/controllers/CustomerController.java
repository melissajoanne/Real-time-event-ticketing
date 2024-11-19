package lk.ac.iit.RealTimeTicket.controllers;

import lk.ac.iit.RealTimeTicket.model.Customer;
import lk.ac.iit.RealTimeTicket.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/tickets")
    public String acquireTickets(@RequestBody Customer customer) throws InterruptedException {
        customerService.acquireTickets(String.valueOf(customer));
        return "Tickets retrieved by multiple customers!";

    }
}
/*public ResponseEntity<String> acquireTickets(@PathVariable String customerName) {
    try {
        customerService.acquireTickets(customerName);
        return ResponseEntity.ok("Customer " + customerName + " is acquiring tickets asynchronously.");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}*/
