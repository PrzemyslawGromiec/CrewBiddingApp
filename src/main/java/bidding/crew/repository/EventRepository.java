package bidding.crew.repository;

import bidding.crew.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    List<Event> getEvents();
    void addEvents(List<Event> events);
    Optional<Event> removeById(int id);
}
