package org.example.repositories;

import org.example.entities.Event;

import java.util.List;

public interface EventRepository {
    List<Event> getEvents();
    void saveEvents(List<Event> events);
    int getNextId();

}
