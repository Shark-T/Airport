package bl.controllers;

import bl.utils.Log;
import dl.data.Flight;
import dl.model.AuthUser;
import dl.model.FetchFlights;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SHARK on 09.08.2017.
 */
public class MainWindowContr implements Initializable {
    public FetchFlights ff = new FetchFlights();

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab arrivalTab;

    @FXML
    private Tab depatureTab;

    @FXML
    private TableView arrivalTable;

    @FXML
    private TableView depatureTable;

    @FXML
    private MenuBar rootMenu;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwdInput;

    @FXML
    private VBox sidebar;

    @FXML
    private TextField searchByFlight;

    @FXML
    private TextField searchByAirport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillArrivalTable();
        fillDepatureTable();

        // define a behaviour for quick search TextFields
        searchByFlight.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            searchByAirport.clear();
            qSearchByFlight();
        });

        searchByAirport.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            searchByFlight.clear();
            qSearhByAiportName();
        });

        // assign a behaviour for arrival and depature tabs. Clearing field for no-frozing
        // showing results
        arrivalTab.selectedProperty().addListener((InvalidationListener) (ov) -> {
            searchByFlight.clear();
            fillArrivalTable();
        });
        depatureTab.selectedProperty().addListener((InvalidationListener) (ov) -> {
            searchByAirport.clear();
            fillDepatureTable();
        });

    }

    public void fillArrivalTable() {

        ObservableList<Flight> flights = FXCollections.observableArrayList();

        for (Flight flight : ff.getArrivals()) {
            flights.add(flight);
        }

        arrivalTable.setItems(flights);
    }


    public void fillDepatureTable() {

        ObservableList<Flight> flights = FXCollections.observableArrayList();

        for (Flight flight : ff.getDepatures()) {
            flights.add(flight);
        }

        depatureTable.setItems(flights);
    }


    public void quitRoot() {

        Log.getInstance().info("Leaving from company staff mode");
        loginInput.clear();
        passwdInput.clear();
        rootMenu.setVisible(false);
        sidebar.setVisible(true);

    }


    public void chownRoot() {

        // account passed
        if (ifRoot(loginInput.getText(), passwdInput.getText())) {

            // showing menu and hide sidebar
            rootMenu.setVisible(true);
            sidebar.setVisible(false);

            Log.getInstance().info("Logged like company staff");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hello!");
            alert.setHeaderText("Perfect");
            alert.setContentText("You have logged like company staff");
            alert.show();

            // account failed
        } else {
            Log.getInstance().info("Login attempt is failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Try again...");
            alert.setHeaderText("We are sorry...");
            alert.setContentText("but you are trying to type wrong login or password.");
            alert.show();
        }
    }


    public boolean ifRoot(String user, String passwd) {

        AuthUser authUser = new AuthUser();
        return (boolean) authUser.apply(user, passwd);
    }

    public void closeApp() {

        Log.getInstance().info("Closing Main window ");
        Stage stage = (Stage) arrivalTable.getScene().getWindow();
        stage.close();

    }


    public void sendForm(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {

            chownRoot();

        }
    }

    /* Searching and filter values by flight. Show any occurrences if happen */
    public void qSearchByFlight() {

        String activeTab = tabPane.getSelectionModel().getSelectedItem().getId();
        String fieldVal = searchByFlight.getText();

        ObservableList<Flight> flights = FXCollections.observableArrayList();

        for (Flight flight : ff.getFiltFlByAbbr(fieldVal, activeTab)) {

            flights.add(flight);
        }
        if (activeTab.equals("arrival")) {
            arrivalTable.setItems(flights);
        } else if (activeTab.equals("depature")) {
            depatureTable.setItems(flights);
        }

    }


    /* Searching and filter values by airportName. Show any occurrences if happen */
    public void qSearhByAiportName() {


        String activeTab = tabPane.getSelectionModel().getSelectedItem().getId();
        String fieldVal = searchByAirport.getText();

        ObservableList<Flight> flights = FXCollections.observableArrayList();

        for (Flight flight : ff.getFiltFlByAirport(fieldVal, activeTab)) {

            flights.add(flight);
        }
        if (activeTab.equals("arrival")) {
            arrivalTable.setItems(flights);
        } else if (activeTab.equals("depature")) {
            depatureTable.setItems(flights);
        }

    }


    public void passengerSearchWin() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/PassengerSearch.fxml"));
        Stage userSearchWin = new Stage();

        userSearchWin.setScene(new Scene(root));
        userSearchWin.setTitle("Passenger search");
        userSearchWin.show();

    }

    public void flightSearchWin() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FlightSearch.fxml"));
        Stage userSearchWin = new Stage();

        userSearchWin.setScene(new Scene(root));
        userSearchWin.setTitle("Advanced flight search");
        userSearchWin.show();

    }


    public void showAboutMe() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Me");
        alert.setHeaderText("");
        alert.setContentText("Author: Andrew Sotnikov\n\n" +
                "Email: andrew.sotnikov.eng@gmail.com\n\nMainAcademy, Java course final project\n\nKyiv 2017");
        alert.showAndWait();

    }

}
