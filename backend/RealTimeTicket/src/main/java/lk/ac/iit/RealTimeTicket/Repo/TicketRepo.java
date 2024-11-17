package lk.ac.iit.RealTimeTicket.Repo;

import lk.ac.iit.RealTimeTicket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

}
