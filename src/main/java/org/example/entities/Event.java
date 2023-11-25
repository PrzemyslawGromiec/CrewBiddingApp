package org.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime start;
    private LocalDateTime end;
    private int priority;
    private String description;
    private int id;


    public Event(int id, LocalDateTime start, LocalDateTime end, int priority, String description) {
        if(end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start date.");
        }
        this.id = id;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
    }

    public boolean overlapsWith(Event other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    public boolean isAtThatDayDifferentTime(Event other) {
        LocalDate thisDate = this.start.toLocalDate();
        LocalDate otherDate = other.start.toLocalDate();

        return thisDate.equals(otherDate) &&
                (this.end.isBefore(other.start) || this.start.isAfter(other.end));
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

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id: " + id +
                " ,start = " + start +
                ", end = " + end +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }
}
