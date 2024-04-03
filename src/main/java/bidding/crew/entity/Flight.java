package bidding.crew.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Flight {
    private String flightNumber;
    private String airportCode;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;

    public Flight(String flightNumber, String airportCode, LocalDateTime reportTime, LocalDateTime clearTime, AircraftType aircraftType) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
        this.reportTime = reportTime;
        this.clearTime = clearTime;
        this.aircraftType = aircraftType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }


    public LocalDateTime getClearTime() {
        return clearTime;
    }


    public AircraftType getAircraftType() {
        return aircraftType;
    }


    public Duration getFlightDuration() {
        return Duration.between(reportTime, clearTime);
    }

    public LocalDateTime getClearTimeWithBuffer() {
        return getClearTime().plus(calculateBuffer());
    }

    public boolean isValidFlightInPeriod(Flight flight) {
        return getClearTimeWithBuffer().isBefore(flight.getReportTime()) ||
                flight.getClearTimeWithBuffer().isBefore(getReportTime());
    }

    public Duration calculateBuffer() {
        if (getFlightDuration().compareTo(Duration.ofHours(14)) < 0) {
            return Duration.ofHours(12).plusMinutes(30);
        } else {
            return Duration.ofHours(48);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM h:mma", Locale.ENGLISH);
        String formattedReport = getReportTime().format(formatter);
        String formattedClear = getClearTime().format(formatter);
        return flightNumber +
                ", destination: " + airportCode + '\'' +
                ", report time: " + formattedReport +
                ", clear time: " + formattedClear +
                ", aircraft: " + aircraftType +
                ", duration: " + getFlightDuration();

    }
}
