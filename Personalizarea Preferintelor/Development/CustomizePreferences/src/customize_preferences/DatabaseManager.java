/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import static java.lang.Math.round;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Class that manages a connection to database contains methods for making
 * changes and retrieving data. <br>
 * Attributes : <br>
 * - instance : static attribute that holds a instance of DatabaseManager; <br>
 * - connection : attribute that holds a instance of an database connection
 * object; <br>
 * - statement : attribute that holds a instance of statement object; <br>
 * - resultSet : attribute that holds a instance of a ResultSet object; <br>
 *
 * @author Robert
 */
public class DatabaseManager {

    public static DatabaseManager instance = new DatabaseManager();
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    /**
     * Constructor method <br>
     * Instantiate a new DatabaseManager.
     */
    public DatabaseManager() {

    }
    /**
     * Makes the connection to the database and sets the statement attribute.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customize_preferences", "root", "root");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (InstantiationException ie) {
            System.out.println("Instantiation error: " + ie.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("connect - method : " + ex.getMessage());
        }

    }
    /**
     * Checks if the connection to the database is made. <br>
     * @return true if the connection to the database is made, false if not.
     */
    public boolean isConnected() {
        try {
            if (statement.getConnection() != null) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("isConnected - method : " + e.getMessage());
        }
        return false;
    }
    /**
     * Insert a new client record in clienti table. <br>
     * @param client the client object that will be inserted as a record
     */
    public void insertClient(Client client) {
        if (isConnected()) {
            // make a select on the clients table, to get the last row
            try {
                resultSet = statement.executeQuery("Select * from clienti");
                resultSet.moveToInsertRow();
                resultSet.updateString("criteriu_1", client.getCriteriu1() );
                resultSet.updateString("criteriu_2", client.getCriteriu2() );
                resultSet.updateString("criteriu_3", client.getCriteriu3() );
                resultSet.updateDouble("criteriu_4", round(client.getCriteriu4()));
                resultSet.updateDouble("criteriu_5", round(client.getCriteriu5()));
                resultSet.updateDouble("criteriu_6", round(client.getCriteriu6()));
                resultSet.updateDouble("criteriu_7", round(client.getCriteriu7()));
                resultSet.updateDouble("criteriu_8", round(client.getCriteriu8()));
                resultSet.insertRow();

            } catch (SQLException e) {
                System.out.println(" insertClient - method : " + e.getMessage());
            }
        } else {
            // show message for connection to db error
        }
    }
    /**
     * Updates a client record by setting its id_oferta_fk attribute. <br>
     * @param client the client that will be updated <br>
     * @param offer the offer instance from which the id will be taken
     */
    public void updateClient(Client client, Offer offer) {
        // in order to update client record, we need to select the offer instance from database and get the associated id
        if (isConnected()) {

            try {
                int offerId = getOfferId(offer);
                String updateString = "UPDATE clienti SET id_oferta_fk = " + offerId + " WHERE id_client = " + client.getIdClient();
                statement.executeUpdate(updateString);
            } catch (SQLException ex) {
                System.out.println("updateClient - method " + ex.getMessage());
            }
        }
    }
    /**
     * Inserts a offer record in oferte table. <br>
     * @param offer the offer object that will be inserted as a record
     */
    public void insertOffer(Offer offer) {
        if (isConnected()) {
            // make a select on the clients table, to get the last row
            try {
                resultSet = statement.executeQuery("Select * from oferte");
                resultSet.moveToInsertRow();
                resultSet.updateString( "criteriu_1", offer.getCriteriu1() );
                resultSet.updateString( "criteriu_2", offer.getCriteriu2() );
                resultSet.updateString( "criteriu_3", offer.getCriteriu3() );
                resultSet.updateDouble( "criteriu_4", offer.getCriteriu4() );
                resultSet.updateDouble( "criteriu_5", offer.getCriteriu5() );
                resultSet.updateDouble( "criteriu_6", offer.getCriteriu6() );
                resultSet.updateDouble( "criteriu_7", offer.getCriteriu7() );
                resultSet.updateDouble( "criteriu_8", offer.getCriteriu8() );
                resultSet.insertRow();

            } catch (SQLException e) {
                System.out.println("insertOffer - method " + e.getMessage());
            }
        } else {
            // show message for connection to db error
        }
    }   
    
    /**
     * Makes a query for returning the last record id inserted in oferte table
     * @return the id from database 
     */
    public int getLastInsertedOffer() {
        int id = 0;
        if (isConnected()) {
            // make a select on the clients table, to get the last row
            try {
                resultSet = statement.executeQuery("Select * from oferte");
                resultSet.last();
                id = resultSet.getInt("id_oferta");

            } catch (SQLException e) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            // show message for connection to db error

        }
        return id;
    }
    /**
     * Makes a select query on clienti table based on id_oferta. <br>
     * @param offerId integer value that represents the id_oferta; <br>
     * @return an Offer object based on the query select result; <br>
     */
    public Offer getOfferForId(int offerId) {
        Offer offer = new Offer();
        if (isConnected()) {
            try {
                resultSet = statement.executeQuery("SELECT * FROM oferte where id_oferta = " + offerId);
                if (resultSet.next()) {
                    offer.setIdOferta(resultSet.getInt("id_oferta"));
                    offer.setCriteriu1(resultSet.getString("criteriu_1"));
                    offer.setCriteriu2(resultSet.getString("criteriu_2"));
                    offer.setCriteriu3(resultSet.getString("criteriu_3"));
                    offer.setCriteriu4(resultSet.getInt("criteriu_4"));
                    offer.setCriteriu5(resultSet.getInt("criteriu_5"));
                    offer.setCriteriu6(resultSet.getInt("criteriu_6"));
                    offer.setCriteriu7(resultSet.getInt("criteriu_7"));
                    offer.setCriteriu8(resultSet.getInt("criteriu_8"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return offer;
        }
        return null;
    }
    /**
     * Makes a query select on clienti table based on an Offer object. <br>
     * @param offer an Offer object needed for query
     * @return the id_oferta that matches the values from Offer object 
     */
    public int getOfferId(Offer offer) {
        int offerId = 0;
        if (isConnected()) {
            try {
                String sql = getQuery(offer);
                System.out.println(sql);
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    offerId = resultSet.getInt("id_oferta");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }
        return offerId;
    }
    /**
     * Computes a complex query needed for getOfferId() method that will transform double values of type "5.0", "3.0" etc in integer values. <br>
     * @param offer an Offer object
     * @return a query select string
     */
    protected String getQuery(Offer offer) {
        String crit1 = offer.getCriteriu1();
        String crit2 = offer.getCriteriu2();
        String crit3 = offer.getCriteriu3();
        double crit4 = offer.getCriteriu4();
        double crit5 = offer.getCriteriu5();
        double crit6 = offer.getCriteriu6();
        double crit7 = offer.getCriteriu7();
        double crit8 = offer.getCriteriu8();
        String crit4String = "";
        String crit5String = "";
        String crit6String = "";
        String crit7String = "";
        String crit8String = "";
        if (String.valueOf(crit4).endsWith("0")) {
            crit4String = String.valueOf(crit4).split(Pattern.quote("."))[0];
        } else {
            crit4String = String.valueOf(crit4);
        }
        if (String.valueOf(crit5).endsWith("0")) {
            crit5String = String.valueOf(crit5).split(Pattern.quote("."))[0];
        } else {
            crit5String = String.valueOf(crit5);
        }
        if (String.valueOf(crit6).endsWith("0")) {
            crit6String = String.valueOf(crit6).split(Pattern.quote("."))[0];
        } else {
            crit6String = String.valueOf(crit6);
        }
        if (String.valueOf(crit7).endsWith("0")) {
            crit7String = String.valueOf(crit7).split(Pattern.quote("."))[0];
        } else {
            crit7String = String.valueOf(crit7);
        }
        if (String.valueOf(crit8).endsWith("0")) {
            crit8String = String.valueOf(crit8).split(Pattern.quote("."))[0];
        } else {
            crit8String = String.valueOf(crit8);
        }
        
        String query = "SELECT * FROM oferte where criteriu_1 = " + "'"+crit1+"'" + " AND "
                + "criteriu_2 = " + "'" + crit2 + "'" + " AND "
                + "criteriu_3 = " + "'" + crit3 + "'" + " AND "
                + "criteriu_4 LIKE " + crit4String + " AND "
                + "criteriu_5 LIKE " + crit5String + " AND "
                + "criteriu_6 LIKE " + crit6String + " AND "
                + "criteriu_7 LIKE " + crit7String + " AND "
                + "criteriu_8 LIKE " + crit8String;
        return query;
    }
    /**
     * Makes a query select on clienti table that will get all the clients. <br>
     * @return a list of Client objects;
     */
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("Select * from clienti");
            while (resultSet.next()) {
                Client c = new Client();
                c.setIdClient(resultSet.getInt("id_client"));
                c.setCriteriu1(resultSet.getString("criteriu_1"));
                c.setCriteriu2(resultSet.getString("criteriu_2"));
                c.setCriteriu3(resultSet.getString("criteriu_3"));
                c.setCriteriu4(resultSet.getInt("criteriu_4"));
                c.setCriteriu5(resultSet.getInt("criteriu_5"));
                c.setCriteriu6(resultSet.getInt("criteriu_6"));
                c.setCriteriu7(resultSet.getInt("criteriu_7"));
                c.setCriteriu8(resultSet.getInt("criteriu_8"));
                c.setIdOferta(resultSet.getInt("id_oferta_fk"));
                clients.add(c);
            }
        } catch (SQLException e) {
            System.out.println("getClients - method : " + e.getMessage());
        }
        return clients;
    }
    /**
     * Makes a query select on oferte table that will get all the offers. <br>
     * @return a list of Offer objects;
     */
    public List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("Select * from oferte");
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setIdOferta(resultSet.getInt("id_oferta"));
                offer.setCriteriu1(resultSet.getString("criteriu_1"));
                offer.setCriteriu2(resultSet.getString("criteriu_2"));
                offer.setCriteriu3(resultSet.getString("criteriu_3"));
                offer.setCriteriu4(resultSet.getDouble("criteriu_4"));
                offer.setCriteriu5(resultSet.getDouble("criteriu_5"));
                offer.setCriteriu6(resultSet.getDouble("criteriu_6"));
                offer.setCriteriu7(resultSet.getDouble("criteriu_7"));
                offer.setCriteriu8( resultSet.getDouble("criteriu_8") );
                offers.add(offer);
            }
        } catch (SQLException e) {
            System.out.println("getOffers - method : " + e.getMessage());
        }
        return offers;
    }
    /**
     * Makes a query select on clienti table that will get all the clients that have id_oferta_fk = offerId. <br>
     * @param offerId a integer value
     * @return a list of Client objects;
     */
    public List<Client> getClientsByOfferId(int offerId) {
        List<Client> clients = new ArrayList<Client>();
        try {
            resultSet = statement.executeQuery("Select * from clienti where id_oferta_fk = " + offerId);
            while (resultSet.next()) {
                Client client = new Client();
                client.setIdClient(resultSet.getInt("id_client"));
                client.setCriteriu1(resultSet.getString("criteriu_1"));
                client.setCriteriu2(resultSet.getString("criteriu_2"));
                client.setCriteriu3(resultSet.getString("criteriu_3"));
                client.setCriteriu4(resultSet.getDouble("criteriu_4"));
                client.setCriteriu5(resultSet.getDouble("criteriu_5"));
                client.setCriteriu6(resultSet.getDouble("criteriu_6"));
                client.setCriteriu7(resultSet.getDouble("criteriu_7"));
                client.setCriteriu8(resultSet.getDouble("criteriu_8"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println("getClientsByOfferId - method : " + e.getMessage());
        }

        return clients;
    }
}
