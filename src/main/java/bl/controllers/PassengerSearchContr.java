package bl.controllers;

import bl.utils.Log;
import bl.utils.PassengerContext;
import dl.data.Passenger;
import dl.model.DeletePassengers;
import dl.model.FetchPassengers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

/**
 * Created by SHARK on 09.08.2017.
 */
public class PassengerSearchContr implements Initializable {
    @FXML
    public Tooltip namePattern;

    @FXML
    public Tooltip passportPattern;

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField passportField;

    @FXML
    private TableView passengersTable;

    @FXML
    private ToggleGroup searchingKey;


    private FetchPassengers fp;
    // stack with changed passengers
    private Queue<Integer> changedPsngrsStack = new ArrayDeque<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fp = new FetchPassengers();
        fillPassengersTable();

        passengersTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                changedPsngrsStack.add(passengersTable.getSelectionModel().getSelectedIndex());
            }
        });

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateNameField();
        });

        passportField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassportField();
        });

    }


    /* Starting to fill the table when one appear */
    public void fillPassengersTable() {

        ObservableList<Passenger> flights = FXCollections.observableArrayList();

        for (Passenger passenger : fp.getAllPassengers()) {

            flights.add(passenger);
        }

        passengersTable.setItems(flights);
    }


    /* Running to refill table. Do it with sorting results */
    public void refillPassengerTable() {


        /* Getting a searchingKey */
        RadioButton selectedRadBtn = (RadioButton) searchingKey.getSelectedToggle();
        String filterKey = selectedRadBtn.getText().toString();
        String filterValue;
        if (filterKey.equals("name")) {
            filterValue = nameField.getText();
        } else if (filterKey.equals("city")) {
            filterValue = cityField.getText();

        } else {
            filterValue = passportField.getText();
        }
        ObservableList<Passenger> flights = FXCollections.observableArrayList();
        List<Passenger> filteredPassList = fp.getFilteredPassengers(filterKey, filterValue);
        for (Passenger passenger : filteredPassList) {

            flights.add(passenger);
        }
        passengersTable.setItems(flights);
    }


    /* Refill table and clear searching fields */
    public void refreshChanges(ActionEvent actionEvent) {

        fillPassengersTable();
        nameField.clear();
        cityField.clear();
        passportField.clear();
        Log.getInstance().info("refreshing of table data");

    }

    /* Invoking window for add new passengers */
    public void passengerAddWin() throws IOException {

        // set "add" mode
        PassengerContext.getInstance().setActionState("add");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/PassengerModify.fxml"));
        Stage psngrModifyWin = new Stage();
        psngrModifyWin.setScene(new Scene(root));
        psngrModifyWin.setTitle("Modify passenger data");
        psngrModifyWin.show();
    }

    /* Invoking window to edit passengers */
    public void passengerEditWin() throws IOException {

        // get selected item for editing in table and set "edit" mode
        Passenger selectedPsngr = (Passenger) passengersTable.getSelectionModel().getSelectedItem();
        if (selectedPsngr == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("select table item");
            alert.setHeaderText(null);
            alert.setContentText("Please choose item before editing ");
            alert.show();
        } else {
            PassengerContext.getInstance().setPsngr(selectedPsngr);
            PassengerContext.getInstance().setActionState("edit");

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PassengerModify.fxml"));
            Stage psngrModifyWin = new Stage();
            psngrModifyWin.setScene(new Scene(root));
            psngrModifyWin.setTitle("Modify passenger data");

            psngrModifyWin.show();
        }
    }

    /* For cancel button */
    public void closeWin() {

        Log.getInstance().info("Closing window with Passenger search");
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }


    /* Change text of name field if it not match to pattern */
    public void validateNameField() {

        String curName = nameField.getText();
        if (curName.matches("[A-Z][a-z]{1,20} [A-Z][a-z]{1,20}")) {
            nameField.setStyle("-fx-text-fill: black");
        } else {
            nameField.setStyle("-fx-text-fill: red");
        }

    }

    /* Change text of passport field if it not match to pattern */
    public void validatePassportField() {

        String curName = passportField.getText();
        if (curName.matches("[A-Z]{1,5}[0-9]{3,10}")) {
            passportField.setStyle("-fx-text-fill: black");
        } else {
            passportField.setStyle("-fx-text-fill: red");
        }

    }


    public void deletePassenger(ActionEvent actionEvent) {

        Passenger psngr = (Passenger) passengersTable.getSelectionModel().getSelectedItem();
        if (psngr != null) {
            new DeletePassengers(psngr);
        } else {
            Log.getInstance().info("Hey, choose someone!");
        }

    }
}
