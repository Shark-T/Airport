package dl.model;

import bl.utils.Log;
import dl.data.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHARK on 09.08.2017.
 */
public class FetchPassengers implements PassengerAction {
    private CRUD crud;
    private List<Passenger> passengerList;

    public FetchPassengers() {

        this.crud = new CRUD();

        try {
            crud.createConnection();
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }

        createDetailsTable();

    }


    public List<Passenger> getFilteredPassengers(String filterKey, String filterValue) {

        List<Passenger> filteredPassList = new ArrayList<Passenger>();
        for (Passenger passenger : passengerList) {

            if (filterKey.equals("name")) {
                if (filterValue.equals(passenger.getFullName())) {
                    filteredPassList.add(passenger);
                }
            } else if (filterKey.equals("city")) {
                filteredPassList = getFinalCity(filterValue);
                break;

            } else {
                if (filterValue.equals(passenger.getPassport())) {
                    filteredPassList.add(passenger);
                }
            }
        }

        return filteredPassList;

    }

    @Override
    public void finalize() throws Throwable {

        crud.closeConnection();
        super.finalize();
    }


    public List<Passenger> getFinalCity(String searchingCity) {

        List<Passenger> filteredPassList = new ArrayList<Passenger>();
        try {

            ResultSet rs = crud.doQuery("SELECT passengers.*, flightsTmp.city FROM passengers, flightsTmp " +
                    "WHERE passengers.flightNumber=flightsTmp.flightNumber;");
            while (rs.next()) {

                String existedCity = rs.getString("city");
                if (existedCity.equals(searchingCity)) {
                    filteredPassList.add(new Passenger(rs.getString("flightNumber"),
                            rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("nationality"), rs.getString("passport"),
                            rs.getString("birthday"), rs.getString("gender"),
                            rs.getString("classOfFlight")));
                }
            }
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }

        return filteredPassList;
    }

    public List<Passenger> getAllPassengers() {

        passengerList = new ArrayList<Passenger>();

        try {
            ResultSet rs = crud.doQuery("SELECT * FROM `passengers`");
            while (rs.next()) {

                passengerList.add(new Passenger(rs.getString("flightNumber"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("nationality"),
                        rs.getString("passport"),
                        rs.getString("birthday"),
                        rs.getString("gender"),
                        rs.getString("classOfFlight")));
            }
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }

        return passengerList;
    }

    public void createDetailsTable() {

        try {
            crud.execQuery("CREATE TEMPORARY TABLE flightsTmp (SELECT flights.*, airports.city " +
                    "FROM flights, airports WHERE flights.airportName=airports.airportName)");
        } catch (SQLException e) {
            Log.getInstance().error("Can't do executing temporary table cause MySQL. " +
                    "Maybe you are trying to execute this query on remote host, where is limitations");
        }

    }
}
