package dl.model;

import bl.utils.Log;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by SHARK on 08.08.2017.
 */
public class AuthUser implements BiFunction{
    private CRUD crud = new CRUD();
    private boolean authComplete;


    @Override
    public Object apply(Object o, Object o2) {

        String login = (String) o;
        String passwd = (String) o2;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(passwd.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String passwdMD5 = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (passwdMD5.length() < 32) {
                passwdMD5 = "" + passwdMD5;
            }
            crud.createConnection();
            ResultSet rs = crud.doQuery(String.format("SELECT password FROM permittedUsers WHERE login='%s'", login));
            rs.next();
            if (rs.getString(1).equals(passwdMD5)) {
                authComplete = true;
            } else {
                authComplete = false;
            }

            crud.closeConnection();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.getInstance().error(e.getMessage());
        }

        return authComplete;
    }

    @Override
    public BiFunction andThen(Function after) {

        return null;
    }
}
