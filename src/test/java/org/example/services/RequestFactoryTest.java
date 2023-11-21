package org.example.services;

import org.example.entities.Event;
import org.example.entities.EventRequest;
import org.example.entities.Request;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestFactoryTest {

    @Test
    void combineEventsTest() {
        RequestFactory requestFactory = new RequestFactory();
//        Event event1 = createEvent(3);
//        Event event2 = createNextEvent(event1, 2, 3);

        //TODO: metoda przetestuje scenariusz czy np 20 eventow dzien po dniu i je sklei do jednego

        Event event1 = new Event(LocalDateTime.of(2023, 12, 12, 10, 15),
                LocalDateTime.of(2023, 12, 13, 10, 0), 3, "uni");
        Event event2 = new Event(LocalDateTime.of(2023, 12, 14, 10, 15),
                LocalDateTime.of(2023, 12, 15, 10, 0), 2, "uni");
        Event event3 = new Event(LocalDateTime.of(2023, 12, 14, 10, 15),
                LocalDateTime.of(2023, 12, 17, 10, 0), 2, "uni");
        Event event4 = new Event(LocalDateTime.of(2023, 12, 20, 10, 15),
                LocalDateTime.of(2023, 12, 22, 10, 0), 2, "uni");



        List<Event> events = new ArrayList<>(List.of(event1,event2,event3,event4));

        List<Request> requests = requestFactory.createRequests(events);

        //wyciągnąć do osobnej metody
        boolean incorectlyCombined = true;
        for (Request request : requests) {
            EventRequest eventRequest = (EventRequest) request;
            if (eventRequest.getEvents().size() == 2) {
                if (eventRequest.getEvents().contains(event2) && eventRequest.getEvents().contains(event3)) {
                    if (!incorectlyCombined) {
                        fail("combined event should be created only once");
                    }
                    incorectlyCombined = false;
                }
            }
        }

        if (incorectlyCombined) {
            fail("incorrect combined events");
        }

        //1. zgodne priority
        //2. max 1 dzien odstepu pomiedzy requestami

        assertEquals(3,requests.size());

    }

}