package dl.model;

import bl.utils.Log;
import dl.data.Passenger;

import java.sql.SQLException;


/**
 * Created by SHARK on 08.08.2017.
 */
public class DeletePassengers implements PassengerAction {
    private CRUD crud;

    public DeletePassengers(Passenger psngr) {

        this.crud = new CRUD();
        try {
            crud.createConnection();
            Log.getInstance().info("Passenger: " + psngr.getFullName() + " was deleted");
            crud.execQuery(String.format("DELETE FROM passengers WHERE birthday='%s' && " +
                    "passport='%s'", psngr.getBirthday(), psngr.getPassport()));
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }


    }

    @Override
    public void finalize() throws Throwable {

        crud.closeConnection();
        super.finalize();

    }
}
