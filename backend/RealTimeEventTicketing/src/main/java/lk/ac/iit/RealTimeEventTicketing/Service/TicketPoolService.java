package lk.ac.iit.RealTimeEventTicketing.Service;
import lk.ac.iit.RealTimeEventTicketing.model.TicketPool;
import lk.ac.iit.RealTimeEventTicketing.model.Vendor;
import lk.ac.iit.RealTimeEventTicketing.repo.TicketPoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPoolService {
    private final TicketPoolRepo ticketPoolRepo;

    @Autowired
    public TicketPoolService(TicketPoolRepo ticketPoolRepo) {
        this.ticketPoolRepo = ticketPoolRepo;
    }

    public TicketPool addTicketPool(TicketPool ticketPool) {
        return ticketPoolRepo.save(ticketPool);
    }

    public TicketPool updateTicketPool(TicketPool ticketPool) {
        return ticketPoolRepo.save(ticketPool);
    }

 public TicketPool getTicketPoolById(Long id) {
        return ticketPoolRepo.findById(id).orElse(null);
 }
    public List<TicketPool> findAllTicketPools() {
        return ticketPoolRepo.findAll();
    }

    public void deleteTicketPool(TicketPool ticketPool) {
        ticketPoolRepo.delete(ticketPool);
    }
}



