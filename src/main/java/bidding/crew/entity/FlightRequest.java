package bidding.crew.entity;

import java.time.LocalDateTime;

public class FlightRequest extends Request {
    private Flight flight;
    private int numOfStars;

    public FlightRequest(Flight flight, int numOfStars) {
        this.flight = flight;
        this.numOfStars = numOfStars;
    }

    @Override
    public LocalDateTime getStartTime() {
        return flight.getReportTime();
    }

    @Override
    LocalDateTime getEndTime() {
        return flight.getClearTime();
    }

    @Override
    public int getNumberOfStars() {
        return numOfStars;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "flight -> " + flight +
                super.toString();
    }
}
