package bl.controllers;

import bl.utils.Log;
import bl.utils.PassengerContext;
import dl.data.Passenger;
import dl.model.ModifyPassengers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by SHARK on 09.08.2017.
 */
public class PassengerModifyContr implements Initializable {
    @FXML
    private TextField flightNumberTField;

    @FXML
    private TextField firstNameTField;

    @FXML
    private TextField lastNameTField;

    @FXML
    private TextField nationalityTField;

    @FXML
    private TextField passportTField;

    @FXML
    private TextField birthdayTField;

    @FXML
    private ChoiceBox genderChBox;

    @FXML
    private ChoiceBox classChBox;

    private ObservableList<String> genderChoicesList =
            FXCollections.observableArrayList("male", "female");

    private ObservableList<String> classChoicesList =
            FXCollections.observableArrayList("economy", "business");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize gender choice box
        genderChBox.setItems(genderChoicesList);
        genderChBox.setValue("male");

        // initialize class choice box
        classChBox.setItems(classChoicesList);
        classChBox.setValue("business");

        if (PassengerContext.getInstance().getActionState().equals("add")) {
            clearFields();
        } else {
            predefineValues(PassengerContext.getInstance().getPsngr());
        }

        // validation
        birthdayTField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateBirthdayField();
        });
    }

    private void validateBirthdayField() {

        String curName = birthdayTField.getText();
        if (curName.matches("[1,2][0-9]{3}-[0,1][0-9]-[0,1,2,3][0-9]")) {
            birthdayTField.setStyle("-fx-text-fill: black");
        } else {
            birthdayTField.setStyle("-fx-text-fill: red");
        }


    }


    /* For cancel button */
    public void closeWin() {

        Log.getInstance().info("Closing passenger modify window");
        Stage stage = (Stage) firstNameTField.getScene().getWindow();
        stage.close();

    }

    /* Predefine fields with values from selected passenger item. Just for convenience.
    Raise NullPointer if item was not selected. */
    public void predefineValues(Passenger psngr) {

        if (psngr == null) {
            throw new NullPointerException();
        }
        flightNumberTField.setText(psngr.getFlightNumber());
        firstNameTField.setText(psngr.getFirstName());
        lastNameTField.setText(psngr.getLastName());
        nationalityTField.setText(psngr.getNationality());
        passportTField.setText(psngr.getPassport());
        // get old values for identity row in table
        PassengerContext.getInstance().setNonModifiedPassp(passportTField.getText());
        birthdayTField.setText(psngr.getBirthday());
        // get old values for identity row in table
        PassengerContext.getInstance().setNonModifiedBirth(birthdayTField.getText());
        genderChBox.setValue(psngr.getGender());
        classChBox.setValue(psngr.getClassOfFlight());
    }


    public Passenger getFieldsValues() {

        Passenger psngr = new Passenger(flightNumberTField.getText(),
                firstNameTField.getText(), lastNameTField.getText(),
                nationalityTField.getText(), passportTField.getText(),
                birthdayTField.getText(), (String) genderChBox.getValue(),
                (String) classChBox.getValue());

        return psngr;

    }

    public void clearFields() {

        flightNumberTField.clear();
        firstNameTField.clear();
        lastNameTField.clear();
        nationalityTField.clear();
        passportTField.clear();
        birthdayTField.clear();
        genderChBox.setValue("male");
        classChBox.setValue("business");
    }


    /* Saving modified data and close win */
    public void saveToPassengers() {

        new ModifyPassengers(getFieldsValues());
        closeWin();

    }
}
