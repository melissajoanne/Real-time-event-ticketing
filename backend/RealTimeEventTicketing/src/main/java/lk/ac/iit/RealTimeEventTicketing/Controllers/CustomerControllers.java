package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.CustomerService;
import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerControllers {
    private final CustomerService customerService;

    @Autowired
    public CustomerControllers(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
public ResponseEntity<List<Customer>> getAllCustomers() {
List<Customer> customers = customerService.findAllCustomers();
return new ResponseEntity<>(customers, HttpStatus.OK);
}

@GetMapping("/find/{customerId}")
public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
    Customer customer= customerService.findCustomerById(customerId);
    return new ResponseEntity<>(customer, HttpStatus.OK);

}

@PostMapping("/add")
public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
Customer newCustomer = customerService.addCustomer(customer);
return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
}

@PutMapping("/update")
public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
Customer updatedCustomer = customerService.updateCustomer(customer);
return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
}

@DeleteMapping("/delete/{customerId}")
public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
    customerService.deleteCustomer(customerId);
    return new ResponseEntity<>(HttpStatus.OK);
}
    }




