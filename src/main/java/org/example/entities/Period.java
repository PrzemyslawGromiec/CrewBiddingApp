package org.example.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Period {

    //czy ta klasa nie powinna jakos wspolpracowac z klasa EventService

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Flight> flights = new ArrayList<>();

    public Period(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /*    public Duration calculateTotalPeriod() {
        if (startTimes.isEmpty()) {
            return Duration.ZERO;
        }

        LocalDateTime earliestStart = startTimes.get(0);
        LocalDateTime latestEnd = endTimes.get(0);

        for (int i = 1; i < startTimes.size(); i++) {
            if (startTimes.get(i).isBefore(earliestStart)) {
                earliestStart = startTimes.get(i);
            }
            if (endTimes.get(i).isAfter(latestEnd)) {
                latestEnd = endTimes.get(i);
            }
        }
        return Duration.between(earliestStart, latestEnd);
    }*/

/*    public Set<LocalDate> getCoveredDates() {
        if (startTimes.isEmpty()) {
            return new HashSet<>();
        }

        LocalDate earliestStart = startTimes.get(0).toLocalDate();
        LocalDate latestEnd = endTimes.get(0).toLocalDate();

        for (int i = 1; i < startTimes.size(); i++) {
            LocalDate startDate = startTimes.get(i).toLocalDate();
            LocalDate endDate = endTimes.get(i).toLocalDate();
            if (startDate.isBefore(earliestStart)) {
                earliestStart = startDate;
            }
            if (endDate.isAfter(latestEnd)) {
                latestEnd = endDate;
            }
        }

        Set<LocalDate> coveredDates = new HashSet<>();
        LocalDate currentDate = earliestStart;
        while (!currentDate.isAfter(latestEnd)) {
            coveredDates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return coveredDates;
    }*/

/*    public void displayCoveredDays() {
        Set<LocalDate> coveredDates = getCoveredDates();
        for (LocalDate coveredDate : coveredDates) {
            System.out.println(coveredDate);
        }
    }*/

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Period{" +
                "startTime = " + startTime +
                ", endTime = " + endTime +
                '}';
    }
}
