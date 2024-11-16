package lk.ac.iit.RealTimeTicket.Repo;

import lk.ac.iit.RealTimeTicket.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
}
