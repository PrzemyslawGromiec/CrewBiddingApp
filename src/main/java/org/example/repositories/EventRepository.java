package org.example.repositories;

import org.example.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    List<Event> getEvents();
    void addEvents(List<Event> events);
    Optional<Event> removeById(int id);
}
