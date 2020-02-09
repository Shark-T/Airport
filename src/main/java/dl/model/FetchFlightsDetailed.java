package dl.model;

import bl.utils.Log;
import dl.data.FlightDetailed;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHARK on 09.08.2017.
 */
public class FetchFlightsDetailed implements PassengerAction {
    private CRUD crud;
    private List<FlightDetailed> flightsDetailed = new ArrayList<FlightDetailed>();

    public FetchFlightsDetailed() {


        this.crud = new CRUD();

        try {
            crud.createConnection();
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }

    }


    /* Getting a list of all flights with details (route, class, cost).
    Every flight devided by classOfFlight. */
    public List<FlightDetailed> getRawFlights() {


        List<FlightDetailed> tmpFlights = new ArrayList<FlightDetailed>();

        try {
            ResultSet rs = crud.doQuery("SELECT flights.flightNumber, flights.airportName, " +
                    "flights.time, flights.typeOfFlight, prices.cost, prices.classOfFlight " +
                    "FROM flights, prices WHERE flights.flightNumber=prices.flightNumber " +
                    "ORDER BY flights.flightNumber, prices.classOfFlight;");
            while (rs.next()) {
                String flightNumber = rs.getString("flightNumber");
                String airportName = rs.getString("airportName");
                String time = rs.getString("time");
                String typeOfFlight = rs.getString("typeOfFlight");
                int businessPrice;
                int economyPrice;
                if (rs.getString("classOfFlight").equals("business")) {
                    businessPrice = rs.getInt("cost");
                    economyPrice = 0;
                } else {
                    businessPrice = 0;
                    economyPrice = rs.getInt("cost");
                    ;
                }

                tmpFlights.add(new FlightDetailed(flightNumber, airportName, time, typeOfFlight,
                        0, 0, economyPrice, businessPrice));
            }

        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }

        return tmpFlights;
    }


    public void fillPrice() {

        List<FlightDetailed> tmpFlights = getRawFlights();

        for (int i = 0; i < tmpFlights.size(); i = i + 2) {

            FlightDetailed fdBus = tmpFlights.get(i);
            FlightDetailed fdEco = tmpFlights.get(i + 1);
            flightsDetailed.add(new FlightDetailed(fdBus.getFlightNumber(), fdBus.getAirportName(), fdBus.getTime(),
                    fdBus.getTypeOfFlight(), 0, 0, fdEco.getEconomyPrice(),
                    fdBus.getBusinessPrice()));
        }
    }


    public void fillPassCount() {

        for (FlightDetailed flightDetailed : flightsDetailed) {

            try {

                System.out.printf("");
                ResultSet rs = crud.doQuery(String.format("SELECT COUNT(*) FROM passengers WHERE flightNumber='%s' " +
                        "&& classOfFlight='business';", flightDetailed.getFlightNumber()));
                rs.next();
                int businessPrice = rs.getInt(1);

                rs = crud.doQuery(String.format("SELECT COUNT(*) FROM passengers WHERE flightNumber='%s' " +
                        "&& classOfFlight='economy';", flightDetailed.getFlightNumber()));
                rs.next();
                int economyPrice = rs.getInt(1);
                flightDetailed.setBusinessPsngrCount(businessPrice);
                flightDetailed.setEconomyPsngrCount(economyPrice);

            } catch (SQLException e) {
                Log.getInstance().error(e.getMessage());
            }
        }
    }


    public List<FlightDetailed> getFlightsDetailed() {

        fillPrice();
        fillPassCount();

        return flightsDetailed;

    }


    /* Return values of flights which sorted by flight */
    public List<FlightDetailed> getFiltFlight(String charSequence, int lowerLim, int upperLim) {

        List<FlightDetailed> filteredFlightList = new ArrayList<>();
        for (FlightDetailed flight : flightsDetailed) {
            if (flight.getFlightNumber().contains(charSequence)
                    && flight.getEconomyPrice() >= lowerLim
                    && flight.getBusinessPrice() <= upperLim) {
                filteredFlightList.add(flight);
            }
        }
        return filteredFlightList;
    }

    public int getMaxPrice() {

        try {
            ResultSet rs = crud.doQuery("SELECT MAX(cost) FROM prices;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());

        }
        return Integer.parseInt(null);

    }

    public int getMinPrice() {

        try {
            ResultSet rs = crud.doQuery("SELECT MIN(cost) FROM prices;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());

        }
        return Integer.parseInt(null);

    }


    @Override
    public void finalize() throws Throwable {

        super.finalize();
        crud.closeConnection();
    }

}
