package org.example.services;

import org.example.entities.Event;
import org.example.entities.EventRequest;
import org.example.entities.Period;
import org.example.entities.Request;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeriodFactoryTest {
    private static int id = 0;

    @Test
    public void test() {
        PeriodFactory periodFactory = new PeriodFactory();
        List<Request> testRequests = new ArrayList<>();
        testRequests.add(createRequest(1,4));
        testRequests.add(createRequest(8,12));
        testRequests.add(createRequest(15,31));

        List<Period> periods = periodFactory.createPeriodsBetweenRequests(testRequests);

        assertEquals(2, periods.size());
        Period firstPeriod = periods.get(0);
        assertEquals(5,firstPeriod.getStartTime().getDayOfMonth());
        assertEquals(7,firstPeriod.getEndTime().getDayOfMonth());
        Period secondPeriod = periods.get(1);
        assertEquals(13,secondPeriod.getStartTime().getDayOfMonth());
        assertEquals(14,secondPeriod.getEndTime().getDayOfMonth());

    }



    private Request createRequest(int startDay, int endDay) {
        Event event1 = new Event(generateId(), LocalDateTime.of(2023,12,startDay, 0,0),
                LocalDateTime.of(2023,12,endDay,0,0),3,"uni");
        EventRequest request = new EventRequest(List.of(event1));

        return request;
    }

    private int generateId() {
        int startingId = 0;
        startingId = id;
        startingId++;
        return startingId;
    }

}