package bl.controllers;

import bl.utils.FlightDetailedContext;
import bl.utils.Log;
import dl.data.FlightDetailed;
import dl.model.FetchFlightsDetailed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by SHARK on 09.08.2017.
 */
public class FlightSearchContr implements Initializable {
    @FXML
    public TableView flightsTable;

    @FXML
    public TextField searchByFlight;

    @FXML
    public ContextMenu contextMenu;

    @FXML
    public TextField lowerLim;

    @FXML
    public TextField upperLim;

    private List<FlightDetailed> flightsDetailed = new ArrayList<FlightDetailed>();
    private FetchFlightsDetailed ffd = new FetchFlightsDetailed();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showAllFlights();

        // define a behaviour for quick FlightNumber quick search
        searchByFlight.addEventFilter(KeyEvent.KEY_RELEASED, e -> {

            qSearchByFlight();
        });

        // define a behaviour for limits of quick search
        lowerLim.addEventFilter(KeyEvent.KEY_RELEASED, e -> {

            qSearchByFlight();
            validateMinLimField();
        });

        upperLim.addEventFilter(KeyEvent.KEY_RELEASED, e -> {

            qSearchByFlight();
            validateMaxLimField();
        });


    }


    private void showAllFlights() {

        ObservableList<FlightDetailed> flights = FXCollections.observableArrayList();
        for (FlightDetailed flight : ffd.getFlightsDetailed()) {
            flights.add(flight);
        }
        flightsTable.setItems(flights);


    }


    private void qSearchByFlight() {

        ObservableList<FlightDetailed> flights = FXCollections.observableArrayList();
        String inputText = searchByFlight.getText();
        int lowerLim;
        int upperLim;
        if (this.lowerLim.getText().equals("")) {
            lowerLim = ffd.getMinPrice();
        } else {
            lowerLim = Integer.parseInt(this.lowerLim.getText());
        }

        if (this.upperLim.getText().equals("")) {
            upperLim = ffd.getMaxPrice();
        } else {
            upperLim = Integer.parseInt(this.upperLim.getText());
        }

        for (FlightDetailed flight : ffd.getFiltFlight(inputText, lowerLim, upperLim)) {
            flights.add(flight);
        }
        flightsTable.setItems(flights);

    }


    public void closeWin() {

        Log.getInstance().info("close Flight search window");
        Stage stage = (Stage) flightsTable.getScene().getWindow();
        stage.close();

    }

    /* Showing details about chosen flight. Passengers count, prices.
    if nothing have chosen show message box with error, otherwise show details. */
    public void showDetails() throws IOException {

        FlightDetailedContext fdCon = FlightDetailedContext.getInstance();
        FlightDetailed selectedFlight = (FlightDetailed) flightsTable.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("select table item");
            alert.setHeaderText(null);
            alert.setContentText("Please choose item before see details");
            alert.show();
        } else {
            fdCon.setFlight(selectedFlight);
            flightDetailedWin();
        }
    }


    public void flightDetailedWin() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FlightDetailed.fxml"));
        Stage userSearchWin = new Stage();

        userSearchWin.setScene(new Scene(root));
        userSearchWin.setTitle("Look at more details");
        userSearchWin.show();

    }


    /* Validate fields with min pric limit. Input should be Int type
    and be in range 10..999. If it is out of range then will be paint
    in red. */
    public void validateMinLimField() {

        String curName = lowerLim.getText();
        if (curName.matches("[1-9][0-9]{1,2}")) {
            lowerLim.setStyle("-fx-text-fill: black");
        } else {
            lowerLim.setStyle("-fx-text-fill: red");
        }
    }


    /* Working same like "validateMinLimField" */
    public void validateMaxLimField() {

        String curName = upperLim.getText();
        if (curName.matches("[1-9][0-9]{1,2}")) {
            upperLim.setStyle("-fx-text-fill: black");
        } else {
            upperLim.setStyle("-fx-text-fill: red");
        }
    }
}
