package dl.data;

/**
 * Created by SHARK on 08.08.2017.
 */
public class Passenger {
    private String flightNumber;
    private String firstName;
    private String lastName;
    private String nationality;
    private String passport;
    private String birthday;
    private String gender;
    private String classOfFlight;

    public Passenger(String flightNumber, String firstName, String lastName, String nationality, String passport, String birthday, String gender, String classOfFlight) {
        this.flightNumber = flightNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.passport = passport;
        this.birthday = birthday;
        this.gender = gender;
        this.classOfFlight = classOfFlight;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "flightNumber='" + flightNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", passport='" + passport + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", classOfFlight='" + classOfFlight + '\'' +
                '}';
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPassport() {
        return passport;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return (this.firstName + " " + this.lastName);
    }

    public String getClassOfFlight() {
        return classOfFlight;
    }

}
