package bl.controllers;

import bl.utils.FlightDetailedContext;
import bl.utils.Log;
import dl.data.FlightDetailed;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SHARK on 09.08.2017.
 */
public class FlightsDetailedContr implements Initializable {
    @FXML
    public Label flightNumber;
    @FXML
    public Label route;
    @FXML
    public Label businessPrice;
    @FXML
    public Label businessPassengers;
    @FXML
    public Label economyPrice;
    @FXML
    public Label economyPassengers;

    @FXML


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillLabels();

    }

    /*    Fill labels with data for more detailed information*/
    public void fillLabels() {

        FlightDetailed flight = FlightDetailedContext.getInstance().getFlight();

        flightNumber.setText(flight.getFlightNumber());
        route.setText(flight.getRoute());
        businessPassengers.setText(String.valueOf(flight.getBusinessPsngrCount()));
        economyPassengers.setText(String.valueOf(flight.getEconomyPsngrCount()));
        businessPrice.setText(String.valueOf(flight.getBusinessPrice()));
        economyPrice.setText(String.valueOf(flight.getEconomyPrice()));

    }


    public void closeWin() {

        Log.getInstance().info("Closing window with detailed flight info");
        Stage stage = (Stage) flightNumber.getScene().getWindow();
        stage.close();

    }

}
