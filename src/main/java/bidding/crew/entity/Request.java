package bidding.crew.entity;

import java.time.LocalDateTime;

public abstract class Request {
    private int points;

    public abstract LocalDateTime getStartTime();

    abstract LocalDateTime getEndTime();

    public int getPoints(){
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public abstract int getNumberOfStars();

    @Override
    public String toString() {
        return " ,request points: " + getPoints();
    }
}


