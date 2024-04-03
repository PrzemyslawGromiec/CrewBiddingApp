package bidding.crew.service;

import bidding.crew.entity.Request;
import bidding.crew.entity.Event;
import bidding.crew.entity.EventRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventRequestFactoryTest {
    private static int nextId = 0;

    @Test
    void combineEventsTest() {
        EventRequestFactory eventRequestFactory = new EventRequestFactory();

        Event event1 = new Event(1,LocalDateTime.of(2023, 12, 12, 10, 15),
                LocalDateTime.of(2023, 12, 13, 10, 0), 3, "uni");

        Event event2 = new Event(2,LocalDateTime.of(2023, 12, 14, 10, 15),
                LocalDateTime.of(2023, 12, 15, 10, 0), 2, "uni");

        Event event3 = new Event(3,LocalDateTime.of(2023, 12, 14, 10, 15),
                LocalDateTime.of(2023, 12, 17, 10, 0), 2, "uni");

        Event event4 = new Event(4,LocalDateTime.of(2023, 12, 20, 10, 15),
                LocalDateTime.of(2023, 12, 22, 10, 0), 2, "uni");

        Event event5 = createEvent(29,9,0, 2);
        Event event6 = createEvent(30,9,0,2);
        Event event7 = createEvent(27,14,0,2);


        List<Event> events = new ArrayList<>(List.of(event1,event2,event3,event4,event5,event6,event7));

        for (Event event : events) {
            System.out.println(event);
        }

        List<EventRequest> requests = eventRequestFactory.createRequests(events);
        for (Request request : requests) {
            System.out.println(request);
        }

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

        assertEquals(5,requests.size());

    }


    public Event createEvent(int day, int startHour, int startMinute, int priority) {
        int year = LocalDate.now().getYear();
        int month = 12;

        LocalDateTime startDateTime = LocalDateTime.of(year, month, day, startHour, startMinute);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        int id = getNextId();
        String defaultDescription = "test event";
        return new Event(getNextId(),startDateTime,endDateTime,priority,defaultDescription);
    }

    private int getNextId() {
        return ++nextId;
    }

}