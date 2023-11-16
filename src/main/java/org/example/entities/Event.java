package org.example.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {
    private LocalDateTime start;
    private LocalDateTime end;
    private int priority;
    private String description;


    public Event(LocalDateTime start, LocalDateTime end, int priority, String description) {
        if(end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start date.");
        }
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
    }

    public boolean overlapsWith(Event other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Event{" +
                "start = " + start +
                ", end = " + end +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }
}
