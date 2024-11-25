package lk.ac.iit.RealTimeEventTicketing.Service;

import lk.ac.iit.RealTimeEventTicketing.model.Event;
import lk.ac.iit.RealTimeEventTicketing.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class EventService {
    private final EventRepo eventRepo;

    @Autowired
    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public Event addEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event findEventById(Long eventId) {
        return eventRepo.findById(eventId).orElse(null);
    }

    public List<Event> findAllEvents() {
        return eventRepo.findAll();
    }

    public void deleteEvent(Long eventId) {
        eventRepo.deleteById(eventId);
    }
}







