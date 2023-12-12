package org.example.entities;

import java.time.Duration;
import java.time.LocalDateTime;


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

    public void shortenPeriod(Flight flight) {
        Duration firstPeriod = Duration.between(this.startTime,flight.getReportTime());
        Duration secondPeriod = Duration.between(this.endTime, flight.getClearTime());
        if (firstPeriod.compareTo(secondPeriod) < 0) {
            endTime = flight.getReportTime();
        } else {
            startTime = flight.getClearTime();
        }
    }

    public Duration periodDuration() {
        return Duration.between(startTime,endTime);
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
