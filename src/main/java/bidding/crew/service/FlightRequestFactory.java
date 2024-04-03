package bidding.crew.service;

import bidding.crew.entity.Flight;
import bidding.crew.entity.FlightRequest;

import java.util.ArrayList;
import java.util.List;

public class FlightRequestFactory {
    private List<FlightRequest> requests = new ArrayList<>();
    public void buildRequest(Flight chosenFlight, int priority) {
        FlightRequest flightRequest = new FlightRequest(chosenFlight,priority);
        requests.add(flightRequest);
    }

    public List<FlightRequest> getRequests() {
        return requests;
    }
}
