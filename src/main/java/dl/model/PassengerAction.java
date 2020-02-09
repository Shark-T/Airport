package dl.model;

/**
 * Created by SHARK on 08.08.2017.
 */
public interface PassengerAction {
    CRUD crud = null;

    // close connection
    void finalize() throws Throwable;
}
