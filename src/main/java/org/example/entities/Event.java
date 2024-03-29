package org.example.entities;

import org.example.general.Time;

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
    private boolean reoccurring = false;


    public Event(int id, LocalDateTime start, LocalDateTime end, int priority, String description) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start date.");
        }
        this.id = id;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
    }

    public boolean overlapsWith(Event other) {
        return !getEnd().isBefore(other.getStart()) && !getStart().isAfter(other.getEnd());
    }

    public boolean isAtThatDayDifferentTime(Event other) {
        LocalDate thisDate = getStart().toLocalDate();
        LocalDate otherDate = other.getStart().toLocalDate();

        return thisDate.equals(otherDate) &&
                (getEnd().isBefore(other.getStart()) || getStart().isAfter(other.getEnd()));
    }

    public LocalDateTime getStart() {
        if (reoccurring) {
            start = withAppTime(start,0);
        }
        return start;
    }

    public LocalDateTime getEnd() {
        if (reoccurring) {
            if ((start.getMonth() != end.getMonth())) {
                end = withAppTime(end,1);
            } else {
                end = withAppTime(end,0);
            }
        }
        return end;
    }

    private LocalDateTime withAppTime(LocalDateTime time, int plusMonth) {
        return time.withMonth(Time.getTime().nextMonthTime().plusMonths(plusMonth).getMonthValue());
    }
    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public void setReoccurring(boolean reoccurring) {
        this.reoccurring = reoccurring;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id: " + id +
                " ,start = " + getStart() +
                ", end = " + getEnd() +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }
}
