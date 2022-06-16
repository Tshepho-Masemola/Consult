package model;

import entitties.Customer;
import entitties.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
    public static final String dbURL = "jdbc:sqlserver://TSHEPHOM7470\\MSSQL2019;encrypt=true;trustServerCertificate=true;databaseName=ProjectX";
    public static final String user = "sa";
    public static final String pass = "1mq5p@55w0rd";

    public static final String TABLE_CUSTOMER = "Customer";
    public static final String COLUMN_CUSTOMER_ID = "customerID";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_CUSTOMER_SURNAME = "customerSurname";
    public static final String COLUMN_CUSTOMER_CELLPHONE = "customerCellPhone";
    public static final String COLUMN_CUSTOMER_PLACE_ID = "placeID";

    public static final String TABLE_PLACE = "Place";
    public static final String COLUMN_PLACE_ID = "placeID";
    public static final String COLUMN_PLACE_NAME = "placeName";

    public static final String INSERT_INTO_CUSTOMER = "INSERT INTO " + TABLE_CUSTOMER + "(" +
            COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_SURNAME + ", "
            + COLUMN_CUSTOMER_CELLPHONE + ", " + COLUMN_CUSTOMER_PLACE_ID + ") VALUES (?,?,?,?)";

    public static final String INSERT_INTO_PLACE = "INSERT INTO " + TABLE_PLACE +
            "(" + COLUMN_PLACE_NAME + ") VALUES(?)";

    public static final String QUERY_PLACE = "SELECT " + COLUMN_PLACE_ID + " FROM " + TABLE_PLACE +
            " WHERE " + COLUMN_PLACE_NAME + " = ?";

    public static final String QUERY_CUSTOMER = "SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMER +
            " WHERE " + COLUMN_CUSTOMER_NAME + " = ?";

    public static final String QUERY_CUSTOMER_BY_PLACE = "SELECT " + TABLE_PLACE + "." + COLUMN_PLACE_NAME +
            ", " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + ", " + TABLE_CUSTOMER + "." +
            COLUMN_CUSTOMER_SURNAME + ", " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_CELLPHONE + " FROM "
            + TABLE_CUSTOMER + " JOIN " + TABLE_PLACE + " ON " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_PLACE_ID
            + " = " + TABLE_PLACE + "." + COLUMN_PLACE_ID;
    private Connection conn;

    private PreparedStatement insertIntoCustomer;
    private PreparedStatement insertIntoPlace;
    private PreparedStatement insertIntoTransaction;

    private PreparedStatement queryPlace;
    private PreparedStatement queryCustomer;
    private PreparedStatement queryCustomerByPlace;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            insertIntoPlace = conn.prepareStatement(INSERT_INTO_PLACE, Statement.RETURN_GENERATED_KEYS);
            insertIntoCustomer = conn.prepareStatement(INSERT_INTO_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
            queryPlace = conn.prepareStatement(QUERY_PLACE);
            queryCustomer = conn.prepareStatement(QUERY_CUSTOMER);
            queryCustomerByPlace = conn.prepareStatement(QUERY_CUSTOMER_BY_PLACE);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (insertIntoPlace != null) {
                insertIntoPlace.close();
            }
            if (insertIntoCustomer != null) {
                insertIntoCustomer.close();
            }
            if (insertIntoTransaction != null) {
                insertIntoTransaction.close();
            }
            if (queryPlace != null) {
                queryPlace.close();
            }
            if (queryCustomer != null) {
                queryCustomer.close();
            }
            if (queryCustomerByPlace != null) {
                queryCustomerByPlace.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }

    }

    public List<Customer> queryCustomer() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CUSTOMER)) {

            List<Customer> customers = new ArrayList<>();
            List<Place> places = new ArrayList<>();

            while (results.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(results.getInt(COLUMN_CUSTOMER_ID));
                customer.setName(results.getString(COLUMN_CUSTOMER_NAME));
                customer.setSurname(results.getString(COLUMN_CUSTOMER_SURNAME));
                customer.setCellPhone(results.getString(COLUMN_CUSTOMER_CELLPHONE));
                customer.setPlaceId(results.getInt(COLUMN_CUSTOMER_PLACE_ID));
                customers.add(customer);
            }

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_PLACE);
            while (resultSet.next()) {
                Place place = new Place();
                place.setPlaceId(resultSet.getInt(COLUMN_PLACE_ID));
                place.setPlaceName(resultSet.getString(COLUMN_PLACE_NAME));
                places.add(place);
            }

            return customers;


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
}
