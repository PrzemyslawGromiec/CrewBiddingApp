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
            return Collections.singletonList(nextMonthPeriod());
        }

        EventRequest firstRequest = requests.get(0);

        if (firstRequest.getStartTime().getDayOfMonth() != 1) {
            //zaczynamy od pierwszego dnia miesiaca do jeden dzien przed requestem
            createdPeriods.add(nextMonthPeriod(firstRequest.getStartTime()));
        }
        EventRequest previousEventRequest = firstRequest;

        for (int i = 1; i < requests.size(); i++) {
            EventRequest currentEventRequest = requests.get(i);
            //period zaczyna sie dzien po poprzednim i dzien przed aktualnym periodem
            Period period = nextMonthPeriod(previousEventRequest.getEndTime(),currentEventRequest.getStartTime());
            if (period.getStartTime().isBefore(period.getEndTime())) {
                createdPeriods.add(period);
            }
            previousEventRequest = currentEventRequest;
        }
        return createdPeriods;
    }

    private Period nextMonthPeriod() {
        return nextMonthPeriod(time.nextMonthTime().plusMonths(1).withDayOfMonth(1));
    }
    private Period nextMonthPeriod(LocalDateTime endBeforeThis) {
        return nextMonthPeriod(time.nextMonthTime(),endBeforeThis);
    }

    private Period nextMonthPeriod(LocalDateTime startAfterThis, LocalDateTime endBeforeThis) {
        return new Period(startAfterThis.toLocalDate().plusDays(1).atTime(LocalTime.of(6,0,0)),
                endBeforeThis.toLocalDate().atTime(LocalTime.MAX).minusDays(1));
    }


}

