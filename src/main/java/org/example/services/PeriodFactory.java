package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.Period;
import org.example.entities.Request;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PeriodFactory {
    //metoda przyjmuje liste requestow
    //na podstawie requestow tworzy okresy dookola tych reuestow
    //bierze pierwszy request, sprawdza date startu
    //jesli zaczyna sie pierwszego, to szuka konca, od konca plus 1 do poczatku kolejnego requestu robi period
    //data konca periodu to data startu requestu -1
    //tworzy Periody
    //dodaje do listy Periodow i zwraca periods

    public List<Period> createPeriodsBetweenRequests(List<Request> requests) {
        List<Period> createdPeriods = new ArrayList<>();
        EventRequest previousEventRequest = null;

        if (!requests.isEmpty() && requests.get(0) instanceof EventRequest firstRequest) {
            if (firstRequest.getStartTime().getDayOfMonth() != 1) {
                LocalDateTime periodStart = firstRequest.getStartTime().withDayOfMonth(1).toLocalDate().atStartOfDay();
                LocalDateTime periodEnd = firstRequest.getStartTime().toLocalDate().atTime(LocalTime.MAX).minusDays(1);
                createdPeriods.add(new Period(periodStart, periodEnd));
            }
            previousEventRequest = firstRequest;
        }

        for (int i = 1; i < requests.size(); i++) {
            if (requests.get(i) instanceof EventRequest currentEventRequest) {
                LocalDateTime periodStart = previousEventRequest.getEndTime().toLocalDate().atStartOfDay().plusDays(1);
                LocalDateTime periodEnd = currentEventRequest.getStartTime().toLocalDate().atTime(LocalTime.MAX).minusMinutes(1);
                if (periodStart.isBefore(periodEnd)) {
                    createdPeriods.add(new Period(periodStart, periodEnd));
                }
                previousEventRequest = currentEventRequest;
            }
        }
        for (Period createdPeriod : createdPeriods) {
            System.out.println(createdPeriod);
        }

        return createdPeriods;
        }
    }

