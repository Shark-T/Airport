package dl.model;

import bl.utils.Log;
import bl.utils.PassengerContext;
import dl.data.Passenger;

import java.sql.SQLException;

/**
 * Created by SHARK on 09.08.2017.
 */
public class ModifyPassengers implements PassengerAction {
    private CRUD crud;
    private Passenger psngr;


    public ModifyPassengers(Passenger psngr) {

        this.crud = new CRUD();
        this.psngr = psngr;

        try {
            crud.createConnection();
            crud.execQuery(generateQuery());
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }

    }


    /* Generate a query depending on which button event from {add/edit} */
    public String generateQuery() {

        String query;
        if (PassengerContext.getInstance().getActionState().equals("add")) {

            /* Query to adding in passengers table */
            Log.getInstance().info("adding to passengers");
            query = String.format("INSERT INTO passengers(flightNumber, firstName, lastName, nationality, " +
                            "passport, birthday, gender, classOfFlight) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s');",
                    psngr.getFlightNumber(), psngr.getFirstName(), psngr.getLastName(), psngr.getNationality(),
                    psngr.getPassport(), psngr.getBirthday(), psngr.getGender(), psngr.getClassOfFlight());

            return query;
            // query to editing in passengers table
        } else {

            Log.getInstance().info("editing passenger");
            query = String.format("UPDATE passengers SET flightNumber='%s'," +
                            "firstName='%s', lastName='%s', nationality='%s', " +
                            "passport='%s', birthday='%s', gender='%s', classOfFlight='%s' " +
                            "WHERE birthday='%s' && passport='%s'",
                    psngr.getFlightNumber(), psngr.getFirstName(),
                    psngr.getLastName(), psngr.getNationality(), psngr.getPassport(),
                    psngr.getBirthday(), psngr.getGender(), psngr.getClassOfFlight(),
                    PassengerContext.getInstance().getNonModifiedBirth(),
                    PassengerContext.getInstance().getNonModifiedPassp());

            return query;
        }

    }

    @Override
    public void finalize() throws Throwable {

        crud.closeConnection();
        super.finalize();
    }


}
