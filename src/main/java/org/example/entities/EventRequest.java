package org.example.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventRequest extends Request {
    private List<Event> events;

    public EventRequest(List<Event> events) {
        this.events = new ArrayList<>(events);
        this.events.sort(Comparator.comparing(Event::getStart));
    }

    @Override
    public LocalDateTime getStartTime() {
        return events.get(0).getStart();
    }

    @Override
    public LocalDateTime getEndTime() {
        return events.get(events.size()-1).getEnd();
    }


    @Override
    public RequestType getType() {
        return RequestType.EVENT;
    }

    @Override
    public int getNumberOfStars() {
        return events.get(0).getPriority();
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {

        return  super.toString() +
                "events=" + events +
                '}';
    }
}
