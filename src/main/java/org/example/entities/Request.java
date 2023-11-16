package org.example.entities;

import java.time.LocalDateTime;
import java.util.List;

abstract class Request {
    private int points;
    private int numberOfStars;

    abstract LocalDateTime getStartTime();

    abstract LocalDateTime getEndTime();

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    abstract RequestType getType();

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    @Override
    public String toString() {
        return "Request{"+
                "start time=" + getStartTime() +
                "end time=" + getEndTime() +
                "type=" + getType() +
                "points=" + points +
                ", numberOfStars=" + numberOfStars +
                '}';
    }
}


