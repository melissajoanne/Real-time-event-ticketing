package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Customer;
import lk.ac.iit.RealTimeEventTicketing.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

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

    public Customer findCustomerById (Customer Long) {
        return customerRepo.findById(Long.getCustomerId()).orElse(null);
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public void deleteCustomer(Customer customer) {
        customerRepo.delete(customer);
    }
}
