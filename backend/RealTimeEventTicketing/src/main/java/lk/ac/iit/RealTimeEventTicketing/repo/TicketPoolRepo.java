package lk.ac.iit.RealTimeEventTicketing.repo;

import lk.ac.iit.RealTimeEventTicketing.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepo extends JpaRepository<TicketPool, Long> {
}
