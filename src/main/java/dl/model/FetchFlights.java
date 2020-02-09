package dl.model;

import bl.utils.Log;
import dl.data.Flight;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SHARK on 08.08.2017.
 */
public class FetchFlights {
    private CRUD crud;
    private List<Flight> arrivalFL;
    private List<Flight> depatureFL;


    public FetchFlights() {


        this.crud = new CRUD();

        try {
            crud.createConnection();
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }

    }

    /* Getting a list of arrivals for showing it into the arrival table*/
    public List<Flight> getArrivals() {

        arrivalFL = new ArrayList<Flight>();

        try {
            ResultSet rs = crud.doQuery("SELECT * FROM `flights` WHERE `typeOfFlight`='arrival' ORDER BY time");
            while (rs.next()) {

                String time = rs.getString("time");
                String status = generateStatus(time, "ARRIVAL");

                arrivalFL.add(new Flight(rs.getString("flightNumber"),
                        rs.getString("airportName"),
                        time,
                        rs.getString("terminal").charAt(0),
                        status));
            }

        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }

        return arrivalFL;
    }


    /* Getting a list of depatures for showing it into the depature table*/
    public List<Flight> getDepatures() {

        depatureFL = new ArrayList<Flight>();

        try {
            ResultSet rs = crud.doQuery("SELECT * FROM `flights` WHERE `typeOfFlight`='depature' ORDER BY time");
            while (rs.next()) {
                String time = rs.getString("time");
                String status = generateStatus(time, "DEPATURE");

                depatureFL.add(new Flight(rs.getString("flightNumber"),
                        rs.getString("airportName"),
                        time,
                        rs.getString("terminal").charAt(0),
                        status));
            }


        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }

        return depatureFL;
    }

    /* Return values of flights which sorted by flight abbreviation */
    public List<Flight> getFiltFlByAbbr(String charSequence, String typeOfFlight) {

        List<Flight> flightsList = null;
        List<Flight> filteredFlightList = new ArrayList<>();
        if (typeOfFlight.equals("arrival")) {
            flightsList = arrivalFL;
        } else if (typeOfFlight.equals("depature")) {
            flightsList = depatureFL;
        }
        for (Flight flight : flightsList) {
            if (flight.getFlightNumber().contains(charSequence)) {
                filteredFlightList.add(flight);
            }
        }
        return filteredFlightList;
    }

    /* Return values of flights which sorted by abbreviation*/
    public List<Flight> getFiltFlByAirport(String charSequence, String typeOfFlight) {

        List<Flight> flightsList = null;
        List<Flight> filteredFlightList = new ArrayList<>();
        if (typeOfFlight.equals("arrival")) {
            flightsList = arrivalFL;
        } else if (typeOfFlight.equals("depature")) {
            flightsList = depatureFL;
        }
        for (Flight flight : flightsList) {
            if (flight.getAirportName().contains(charSequence)) {
                filteredFlightList.add(flight);
            }
        }
        return filteredFlightList;
    }


    public String generateStatus(String time, String type) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime flightTime = LocalTime.from(LocalTime.parse(time, dtf));

        LocalTime now = LocalTime.now();
        long minutesDiff = ChronoUnit.MINUTES.between(now, flightTime);

        if (minutesDiff >= 20 && minutesDiff <= 120 && type.equals("DEPATURE")) {
            return "check-in";
        } else if (minutesDiff < 20 && minutesDiff >= 0 && type.equals("DEPATURE")) {
            return "boarding";
        } else if (minutesDiff < 0 && type.equals("DEPATURE")) {
            return "departed";
        } else if (minutesDiff < 180 && minutesDiff >= 0 && type.equals("ARRIVAL")) {
            Random rand = new Random();
            LocalTime sheduledTime = flightTime.plusMinutes(rand.nextInt(10));
            // getMinute can return minutes if single-digit format. Following upgrade it to
            // double-digit
            String formattedMin;
            if (sheduledTime.getMinute() < 10) {
                formattedMin = "0" + sheduledTime.getMinute();
            } else {
                formattedMin = String.valueOf(sheduledTime.getMinute());
            }
            return "sheduled " + String.valueOf(sheduledTime.getHour() + ":" + formattedMin);

        } else if (minutesDiff < 0 && type.equals("ARRIVAL")) {
            return "landed";
        } else {
            return "   -   ";
        }
    }

    @Override
    public void finalize() throws Throwable {

        super.finalize();
        crud.closeConnection();
    }

}
