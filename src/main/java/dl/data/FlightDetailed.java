package dl.data;

/**
 * Created by SHARK on 08.08.2017.
 */
public class FlightDetailed {
    private String flightNumber;
    private String airportName;
    private String time;
    private String typeOfFlight;
    private int economyPsngrCount;
    private int businessPsngrCount;
    private int economyPrice;
    private int businessPrice;


    public FlightDetailed(String flightNumber, String airportName, String time, String typeOfFlight,
                          int economyPsngrCount, int businessPsngrCount, int economyPrice, int businessPrice) {
        this.flightNumber = flightNumber;
        this.airportName = airportName;
        this.time = time;
        this.typeOfFlight = typeOfFlight;
        this.economyPsngrCount = economyPsngrCount;
        this.businessPsngrCount = businessPsngrCount;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getTime() {
        return time;
    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public int getEconomyPsngrCount() {
        return economyPsngrCount;
    }

    public void setEconomyPsngrCount(int economyPsngrCount) {
        this.economyPsngrCount = economyPsngrCount;
    }

    public int getBusinessPsngrCount() {
        return businessPsngrCount;
    }

    public void setBusinessPsngrCount(int businessPsngrCount) {
        this.businessPsngrCount = businessPsngrCount;
    }

    public int getEconomyPrice() {
        return economyPrice;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public String getRoute() {

        String route;
        if (typeOfFlight.equals("ARRIVAL")) {
            route = airportName + " - Sunrise";
        } else {
            route = "Sunrise - " + airportName;
        }
        return route;

    }

    public int getSumPsngrCount() {

        return (economyPsngrCount + businessPsngrCount);
    }

    @Override
    public String toString() {
        return "FlightDetailed { " +
                "flightNumber='" + flightNumber + '\'' +
                ", airportName='" + airportName + '\'' +
                ", time='" + time + '\'' +
                ", typeOfFlight='" + typeOfFlight + '\'' +
                ", economyPsngrCount=" + economyPsngrCount +
                ", businessPsngrCount=" + businessPsngrCount +
                ", economyPrice=" + economyPrice +
                ", businessPrice=" + businessPrice +
                '}';
    }
}
