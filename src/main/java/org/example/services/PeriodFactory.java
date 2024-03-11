package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.Period;
import org.example.general.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeriodFactory {

    private Time time = Time.getTime();

    //todo: refaktoryzacja - metoda budujaca period
    public List<Period> createPeriodsBetweenRequests(List<EventRequest> requests) {
        List<Period> createdPeriods = new ArrayList<>();
        if (requests.isEmpty()) {
            LocalDateTime firstDayOfNextMonth = time.nextMonthTime().atTime(LocalTime.of(6,0,0));
            LocalDateTime lastDayOfTheMonth = time.nextMonthTime().atTime(LocalTime.MAX);
            Month nextMonth = firstDayOfNextMonth.getMonth();
            return Collections.singletonList(new Period(firstDayOfNextMonth.withDayOfMonth(1),
                    lastDayOfTheMonth.withDayOfMonth(nextMonth.maxLength())));
        }

        EventRequest firstRequest = requests.get(0);

        if (firstRequest.getStartTime().getDayOfMonth() != 1) {
            LocalDateTime periodStart = firstRequest.getStartTime().withDayOfMonth(1).toLocalDate().atTime(LocalTime.of(6,0,0));
            LocalDateTime periodEnd = firstRequest.getStartTime().toLocalDate().atTime(LocalTime.MAX).minusDays(1);
            createdPeriods.add(new Period(periodStart, periodEnd));
        }
        EventRequest previousEventRequest = firstRequest;

        for (int i = 1; i < requests.size(); i++) {
            EventRequest currentEventRequest = requests.get(i);
            LocalDateTime periodStart = previousEventRequest.getEndTime().toLocalDate().plusDays(1).atTime(LocalTime.of(6,0,0));
            LocalDateTime periodEnd = currentEventRequest.getStartTime().toLocalDate().atTime(LocalTime.MAX).minusDays(1);
            if (periodStart.isBefore(periodEnd)) {
                createdPeriods.add(new Period(periodStart, periodEnd));
            }
            previousEventRequest = currentEventRequest;
        }
        return createdPeriods;
    }
}

