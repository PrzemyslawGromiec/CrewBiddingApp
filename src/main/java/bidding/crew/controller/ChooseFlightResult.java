package bidding.crew.controller;

import bidding.crew.entity.Flight;

import java.util.Optional;

public class ChooseFlightResult {
        enum Status{
                FLIGHT_CHOSEN, PERIOD_SKIPPED, SHOW_ALL_DURATIONS
        }

        private final Status status;
        private final Optional<Flight> flight;

        public ChooseFlightResult(Status status, Optional<Flight> flight) {
                this.status = status;
                this.flight = flight;
        }

        public Status getStatus() {
                return status;
        }

        public Optional<Flight> getFlight() {
                return flight;
        }
}
