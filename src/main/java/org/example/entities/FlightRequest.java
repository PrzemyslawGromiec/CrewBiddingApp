package org.example.entities;

import java.time.LocalDateTime;

public class FlightRequest extends Request{
    private Flight flight;

    public FlightRequest(Flight flight) {
        this.flight = flight;
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
    RequestType getType() {
        return RequestType.FLIGHT;
    }
}
