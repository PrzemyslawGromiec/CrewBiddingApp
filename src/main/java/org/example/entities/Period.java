package org.example.entities;

import org.example.services.EventService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Period {

    //czy ta klasa nie powinna jakos wspolpracowac z klasa EventService

    private List<LocalDateTime> startTimes;
    private List<LocalDateTime> endTimes;

    public Period() {
        this.startTimes = new ArrayList<>();
        this.endTimes = new ArrayList<>();
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

    public List<LocalDateTime> getStartTimes() {
        return startTimes;
    }

    public List<LocalDateTime> getEndTimes() {
        return endTimes;
    }
}
