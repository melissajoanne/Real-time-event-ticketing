package lk.ac.iit.RealTimeEventTicketing.repo;

import lk.ac.iit.RealTimeEventTicketing.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepo extends JpaRepository<Event,Long> {
//   Optional<Event> findById(Long eventId);
}
