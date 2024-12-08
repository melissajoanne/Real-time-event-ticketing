package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

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
}


