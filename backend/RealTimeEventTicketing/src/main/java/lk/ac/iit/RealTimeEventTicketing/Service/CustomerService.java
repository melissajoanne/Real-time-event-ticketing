package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepo.findById(customerId).orElse(null);

    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

  public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
  }
    // Signup: create a new customer
//    public Customer signup(String customerEmail, String customerName, String customerPhone) {
//        // Check if the email already exists
//        Optional<Customer> existingCustomer = customerRepo.findByCustomerEmail(customerEmail);
//        if (existingCustomer.isPresent()) {
//            throw new IllegalStateException("Email is already in use.");
//        }
//
//        // Create new customer and save it
//        Customer newCustomer = new Customer();
//        newCustomer.setCustomerEmail(customerEmail);
//        newCustomer.setCustomerName(customerName);
//        newCustomer.setCustomerPhone(customerPhone);
//
//        return customerRepo.save(newCustomer);
//    }
//
//    // Login: find customer by email
//    public Customer login(String CustomerEmail) {
//        return customerRepo.findByCustomerEmail(CustomerEmail)
//                .orElseThrow(() -> new IllegalStateException("Email not found. Please sign up first."));
//    }
}



