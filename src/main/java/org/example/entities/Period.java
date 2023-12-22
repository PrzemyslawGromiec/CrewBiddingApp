package org.example.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Period {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Period(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Period> splitPeriodAroundSelectedFlight(Flight flight) {
        Period first = new Period(startTime, flight.getReportTime());
        Period next = new Period(flight.getClearTime(), endTime);
        List<Period> createdPeriods = new ArrayList<>();
        createdPeriods.add(first);
        createdPeriods.add(next);
        for (Period createdPeriod : createdPeriods) {
            System.out.println("New period: " + createdPeriod);
        }
        return createdPeriods;
    }


    public Duration periodDuration() {
        return Duration.between(startTime, endTime);
    }


    @Override
    public String toString() {
        return "Period{" +
                "startTime = " + startTime +
                ", endTime = " + endTime +
                ", duration: " + periodDuration() +
                '}';
    }
}
