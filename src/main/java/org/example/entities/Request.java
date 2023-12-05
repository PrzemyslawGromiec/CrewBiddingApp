package org.example.entities;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Request {
    private int points;

    abstract LocalDateTime getStartTime();

    abstract LocalDateTime getEndTime();

    abstract int getPoints();

    public void setPoints(int points) {
        this.points = points;
    }

    abstract RequestType getType();

    abstract int getNumberOfStars();

    @Override
    public String toString() {
        return "Request{"+
                "start time = " + getStartTime() + " " +
                "end time = " + getEndTime() + " " +
                "type = " + getType() + " " +
                "points = " + getPoints() +
                ", numberOfStars = " + getNumberOfStars() +
                '}';
    }
}


