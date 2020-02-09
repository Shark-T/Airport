package bl.utils;

import dl.data.FlightDetailed;

/**
 * Created by SHARK on 09.08.2017.
 */
public class FlightDetailedContext {
    private final static FlightDetailedContext instance = new FlightDetailedContext();

    public static FlightDetailedContext getInstance() {

        return instance;
    }

    private FlightDetailed flight;

    public FlightDetailed getFlight() {

        return flight;
    }

    public void setFlight(FlightDetailed flight) {

        this.flight = flight;
    }
}
