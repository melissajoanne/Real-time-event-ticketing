package lk.ac.iit.RealTimeEventTicketing.Controllers;

import lk.ac.iit.RealTimeEventTicketing.Service.CustomerService;
import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

//@PostMapping("/add")
//public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
//Customer newCustomer = customerService.addCustomer(customer);
//String responseMessage = "Customer created successfully. Your customer ID is: " + newCustomer.getCustomerId() + ". Please use this ID when you book tickets.";
//return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
//}
@PostMapping("/add")
public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.addCustomer(customer);

        // Create the response message using the customer ID
        String responseMessage = "Your customer ID is: " + newCustomer.getCustomerId() + ". Please use this ID when you book tickets.";

        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
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


//    @PostMapping("/signup")
//    public ResponseEntity<Customer> signup(@RequestBody Customer customer) {
//        try {
//            Customer createdCustomer = customerService.signup(customer.getCustomerEmail(),
//                    customer.getCustomerName(),
//                    customer.getCustomerPhone());
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Custom error message can be added
//        }
//    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String customerEmail) {
//        try {
//            Customer existingCustomer = customerService.login(customerEmail);
//
//            if (existingCustomer == null) {
//                // Customer does not exist, prompt for signup
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Customer not found. Please sign up first.");
//            }
//
//            // Customer found, login successful
//            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the error
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred during login.");
//        }
//    }


}






