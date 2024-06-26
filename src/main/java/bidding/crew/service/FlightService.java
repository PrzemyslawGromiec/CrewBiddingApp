package bidding.crew.service;

import bidding.crew.entity.Flight;
import bidding.crew.entity.Period;
import bidding.crew.entity.Preference;
import bidding.crew.repository.generator.FlightGeneratorFacade;

import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private PreferencesService preferencesService;

    public FlightService(FlightGeneratorFacade flightGenerator, PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
        flights.addAll(flightGenerator.generateFlights());
    }

    public void applyAircraftTypePreference() {
        Preference preference = preferencesService.getModifiablePreferences();
        flights = flights.stream()
                .filter(flight -> preference.containsAircraftType(flight.getAircraftType()))
                .collect(Collectors.toList());
    }


    //todo used only for tests.
    public List<Flight> generateFlightsForPeriod(Period period) {
        return flights.stream()
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .sorted((flight1, flight2) -> flight2.getFlightDuration().compareTo(flight1.getFlightDuration()))
                .collect(Collectors.toList());
    }

    public List<Flight> getFlightsForPeriod(Period period, boolean allDurations) {
        Preference preference = preferencesService.getModifiablePreferences();
        return flights.stream()
                .filter(flight -> allDurations || preferredDuration(preference, flight))
                .filter(flight -> preference.containsAircraftType(flight.getAircraftType()))
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .collect(Collectors.toList());
    }

    private  boolean preferredDuration(Preference preference, Flight flight) {
        return flight.getFlightDuration().toHours() >= preference.getMinFlightHours()
                && flight.getFlightDuration().toHours() <= preference.getMaxFlightHours();
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }
}
