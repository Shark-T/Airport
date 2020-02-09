package dl.model;

import bl.utils.Log;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by SHARK on 08.08.2017.
 */
public class CRUD {
    private Connection con;
    private Properties prop;
    private String hostName;
    private String dbName;
    private String dbUser;
    private String dbPasswd;

    public CRUD() {

        getProperties();

    }

    public void createConnection() throws SQLException, ClassNotFoundException {

        con = DriverManager.getConnection(
                String.format("jdbc:mysql://%s/%s?useSSL=false", hostName, dbName), dbUser, dbPasswd);
        Log.getInstance().info("Database connection established");

    }

    /* Represents a mechanism for working with SELECT queries */
    public ResultSet doQuery(String query) throws SQLException {


        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);


        return rs;

    }


    //    represents a mechanism for working with INSERT qeuries
    public void execQuery(String query) throws SQLException {

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);

    }

    //    parsing property file for getting connection
    public void getProperties() {

        prop = new Properties();
        InputStream input = null;

        try {

            // load a properties file
            prop.load(getClass().getResourceAsStream("/database.properties"));
            // get the property value and print it out
            hostName = prop.getProperty("hostName");
            dbName = prop.getProperty("dbName");
            dbUser = prop.getProperty("dbUser");
            dbPasswd = prop.getProperty("dbPasswd");

        } catch (IOException ex) {

            Log.getInstance().error(ex.getMessage());

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.getInstance().error(e.getMessage());
                }
            }
        }
    }


    public void closeConnection() {

        try {
            con.close();
        } catch (SQLException e) {
            Log.getInstance().error(e.getMessage());
        }
    }
}
