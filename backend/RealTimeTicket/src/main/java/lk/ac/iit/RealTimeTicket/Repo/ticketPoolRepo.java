package lk.ac.iit.RealTimeTicket.Repo;

import lk.ac.iit.RealTimeTicket.model.Ticket;
import lk.ac.iit.RealTimeTicket.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ticketPoolRepo  extends JpaRepository<Ticket,Long> {
}
