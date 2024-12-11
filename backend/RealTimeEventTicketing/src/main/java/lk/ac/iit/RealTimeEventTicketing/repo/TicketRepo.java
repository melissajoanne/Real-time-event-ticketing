package lk.ac.iit.RealTimeEventTicketing.repo;

import lk.ac.iit.RealTimeEventTicketing.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

}
