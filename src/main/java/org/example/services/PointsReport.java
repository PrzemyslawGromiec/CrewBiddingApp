package org.example.services;

public class PointsReport {
    private String requestType;
    private int points;

    public PointsReport(String requestType, int points) {
        this.requestType = requestType;
        this.points = points;
    }

    public String getRequestType() {
        return requestType;
    }

    public int getPoints() {
        return points;
    }
}
