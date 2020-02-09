package bl.utils;

import dl.data.Passenger;

/**
 * Created by SHARK on 09.08.2017.
 */
public class PassengerContext {
    private final static PassengerContext instance = new PassengerContext();

    public static PassengerContext getInstance() {

        return instance;
    }

    private Passenger psngr;
    private String actionState;

    private String nonModifiedBirth;
    private String nonModifiedPassp;


    public Passenger getPsngr() {

        return psngr;
    }

    public void setPsngr(Passenger passenger) {

        psngr = passenger;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }


    public String getNonModifiedBirth() {
        return nonModifiedBirth;
    }

    public void setNonModifiedBirth(String nonModifiedBirth) {
        this.nonModifiedBirth = nonModifiedBirth;
    }

    public String getNonModifiedPassp() {
        return nonModifiedPassp;
    }

    public void setNonModifiedPassp(String nonModifiedPassp) {
        this.nonModifiedPassp = nonModifiedPassp;
    }
}
