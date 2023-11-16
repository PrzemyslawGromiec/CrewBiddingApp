package org.example.entities;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class EventRequest extends Request {
    private List<Event> events;

    public EventRequest(List<Event> events) {
        this.events = events;
        events.sort(Comparator.comparing(Event::getStart));
        //TODO:przetestwoac czy dobrze sortuje
    }

    @Override
    LocalDateTime getStartTime() {
        return events.get(0).getStart();
    }

    @Override
    LocalDateTime getEndTime() {
        return events.get(events.size()-1).getEnd();
    }

    @Override
    RequestType getType() {
        return RequestType.EVENT;
    }
}
