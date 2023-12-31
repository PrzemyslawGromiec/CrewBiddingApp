package org.example.entities;

import java.time.Duration;
import java.time.LocalDateTime;

public class FlightRequest extends Request{
    private Flight flight;
    private int numOfStars;

    public FlightRequest(Flight flight, int numOfStars) {
        this.flight = flight;
        this.numOfStars = numOfStars;
    }

    @Override
    LocalDateTime getStartTime() {
        return flight.getReportTime();
    }

    @Override
    LocalDateTime getEndTime() {
        return flight.getClearTime();
    }

    @Override
    int getPoints() {
        return 0;
    }

    @Override
    RequestType getType() {
        return RequestType.FLIGHT;
    }

    @Override
    int getNumberOfStars() {
        return numOfStars;
    }

    public Flight getFlight() {
        return flight;
    }


}
