package bidding.crew.service;

import bidding.crew.entity.EventRequest;
import bidding.crew.entity.Flight;
import bidding.crew.entity.FlightRequest;
import bidding.crew.general.Time;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestService {
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private EventService eventService;

    public RequestService(EventService eventService) {
        this.eventService = eventService;
    }

    public List<FlightRequest> getFlightRequests() {
        return flightFactory.getRequests();
    }
    public List<EventRequest> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }
    public void buildRequest(Flight chosenFlight, int priority) {
        flightFactory.buildRequest(chosenFlight, priority);
    }

    public List<Flight> filterBuffer(List<Flight> flightsForCurrentPeriod) {
        return flightsForCurrentPeriod.stream()
                .filter(this::isValidFlight)
                .sorted(Comparator.comparing(Flight::getReportTime))
                .collect(Collectors.toList());
    }

    private boolean isValidFlight(Flight flight) {
        return flightFactory.getRequests().stream()
                .allMatch(request -> request.getFlight().isValidFlightInPeriod(flight));
    }
}
