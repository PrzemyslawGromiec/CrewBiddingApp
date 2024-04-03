package bidding.crew.entity;

import java.util.ArrayList;
import java.util.List;

public class Preference {
    private int minFlightHours = 0;
    private int maxFlightHours = Integer.MAX_VALUE;
    private List<AircraftType> types = new ArrayList<>(List.of(AircraftType.A319,AircraftType.A320,AircraftType.A321,
            AircraftType.B777,AircraftType.B787));


    public int getMinFlightHours() {
        return minFlightHours;
    }

    public int getMaxFlightHours() {
        return maxFlightHours;
    }

    public List<AircraftType> getTypes() {
        return types;
    }

    public void setMinFlightHours(int minFlightHours) {
        this.minFlightHours = minFlightHours;
    }

    public void setMaxFlightHours(int maxFlightHours) {
        this.maxFlightHours = maxFlightHours;
    }

    public void addType (AircraftType type) {
        types.add(type);
    }

    public boolean containsAircraftType(AircraftType type){
        return types.contains(type);
    }
}
