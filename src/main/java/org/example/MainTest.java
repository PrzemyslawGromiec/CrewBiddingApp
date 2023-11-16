package org.example;

import org.example.entities.Event;
import org.example.entities.EventRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        Event event1 = new Event(LocalDateTime.of(2023,12,12,10,15), LocalDateTime.of(2023,12,13,10,0),3,"uni");
        Event event2 = new Event(LocalDateTime.of(2023,12,14,10,15), LocalDateTime.of(2023,12,15,10,0),2,"uni");

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        EventRequest request = new EventRequest(events);
        System.out.println(request);
    }
}
