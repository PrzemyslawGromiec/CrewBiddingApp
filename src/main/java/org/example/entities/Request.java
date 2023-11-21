package org.example.entities;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Request {
    private int points;
    private int numberOfStars;

    abstract LocalDateTime getStartTime();

    abstract LocalDateTime getEndTime();

    abstract int getPoints();

    public void setPoints(int points) {
        this.points = points;
    }

    abstract RequestType getType();

    abstract int getNumberOfStars();

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    @Override
    public String toString() {
        return "Request{"+
                "start time=" + getStartTime() +
                "end time=" + getEndTime() +
                "type=" + getType() +
                "points=" + getPoints() +
                ", numberOfStars=" + getNumberOfStars() +
                '}';
    }
}


