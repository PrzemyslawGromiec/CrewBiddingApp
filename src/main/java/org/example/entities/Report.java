package org.example.entities;

import java.time.LocalDate;
import java.util.List;

public class Report {
    // data - typ - punkty

    private LocalDate date;
    private List<Request> requests;

    public Report(LocalDate date, List<Request> requests) {
        this.date = date;
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    @Override
    public String toString() {
        return "Report{" +
                "date=" + date +
                ", requests=" + requests +
                '}';
    }
}
