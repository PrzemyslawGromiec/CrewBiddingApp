package org.example.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Period {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Flight> flights = new ArrayList<>();

    public Period(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "Period{" +
                "startTime = " + startTime +
                ", endTime = " + endTime +
                '}';
    }
}
