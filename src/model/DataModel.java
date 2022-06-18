package model;

import entitties.Customer;
import entitties.Lists;
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

    public static final String TABLE_lIST = "List";
    public static final String COLUMN_lIST_ID = "listID";
    public static final String COLUMN_LIST_NAME = "listName";

    public static final String TABLE_TRANSACTION = "Transactions";
    public static final String COLUMN_TRANSACTION_CUSTOMER_ID = "customerID";
    public static final String COLUMN_TRANSACTION_AMOUNT = "amount";
    public static final String COLUMN_TRANSACTION_DATE = "transactionDate";

    public static final String TABLE_CUSTOMER_LIST = "CustomerList";
    public static final String COLUMN_CUSTOMER_lIST_ID = "ID";
    public static final String COLUMN_CUSTOMER_LIST_LIST_ID = "listID";
    public static final String COLUMN_CUSTOMER_lIST_CUSTOMER_ID = "customerID";

    public static final String INSERT_INTO_CUSTOMER = "INSERT INTO " + TABLE_CUSTOMER + "(" +
            COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_SURNAME + ", "
            + COLUMN_CUSTOMER_CELLPHONE + ", " + COLUMN_CUSTOMER_PLACE_ID + ") VALUES (?,?,?,?)";

    public static final String INSERT_INTO_PLACE = "INSERT INTO " + TABLE_PLACE +
            "(" + COLUMN_PLACE_NAME + ") VALUES(?)";

    public static final String INSERT_INTO_LIST = "INSERT INTO " + TABLE_lIST + "(" +
            COLUMN_LIST_NAME + ") VALUES (?)";

    public static final String INSERT_INTO_CUSTOMER_LIST = "INSERT INTO " + TABLE_CUSTOMER_LIST + "(" +
            COLUMN_CUSTOMER_LIST_LIST_ID + ", " + COLUMN_CUSTOMER_lIST_CUSTOMER_ID + ") VALUES (?,?)";

    public static final String INSERT_INTO_TRANSACTION = "INSERT INTO " + TABLE_TRANSACTION + "(" +
            COLUMN_TRANSACTION_CUSTOMER_ID + ", " + COLUMN_TRANSACTION_AMOUNT +  ","
            + COLUMN_TRANSACTION_DATE + ") VALUES (?,?,?)";
    public static final String QUERY_PLACE = "SELECT " + COLUMN_PLACE_ID + " FROM " + TABLE_PLACE +
            " WHERE " + COLUMN_PLACE_NAME + " = ?";

    public static final String QUERY_CUSTOMER = "SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMER +
            " WHERE " + COLUMN_CUSTOMER_NAME + " = ?";

    public static final String QUERY_LIST = "SELECT " + COLUMN_lIST_ID + " FROM " + TABLE_lIST +
            " WHERE " + COLUMN_LIST_NAME + " = ?";

    public static final String QUERY_CUSTOMER_LIST = "SELECT " + COLUMN_CUSTOMER_lIST_ID +
            " FROM " + TABLE_CUSTOMER_LIST + " WHERE " + COLUMN_CUSTOMER_LIST_LIST_ID + " = ? AND " +
            COLUMN_CUSTOMER_lIST_CUSTOMER_ID + " = ?";
    public static final String QUERY_CUSTOMER_BY_PLACE = "SELECT " + TABLE_PLACE + "." + COLUMN_PLACE_NAME +
            ", " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + ", " + TABLE_CUSTOMER + "." +
            COLUMN_CUSTOMER_SURNAME + ", " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_CELLPHONE + " FROM "
            + TABLE_CUSTOMER + " JOIN " + TABLE_PLACE + " ON " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_PLACE_ID
            + " = " + TABLE_PLACE + "." + COLUMN_PLACE_ID;
    private Connection conn;

    private PreparedStatement insertIntoCustomer;
    private PreparedStatement insertIntoPlace;
    private PreparedStatement insertIntoTransaction;
    private PreparedStatement insertIntoCustomerList;
    private PreparedStatement insertIntoList;

    private PreparedStatement queryPlace;
    private PreparedStatement queryCustomer;

    private PreparedStatement queryList;
    private PreparedStatement queryCustomerByPlace;
    private PreparedStatement queryCustomerList;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(dbURL, user, pass);
            insertIntoPlace = conn.prepareStatement(INSERT_INTO_PLACE, Statement.RETURN_GENERATED_KEYS);
            insertIntoCustomer = conn.prepareStatement(INSERT_INTO_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
            insertIntoTransaction = conn.prepareStatement(INSERT_INTO_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
            insertIntoCustomerList = conn.prepareStatement(INSERT_INTO_CUSTOMER_LIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoList = conn.prepareStatement(INSERT_INTO_LIST);
            queryPlace = conn.prepareStatement(QUERY_PLACE);
            queryCustomer = conn.prepareStatement(QUERY_CUSTOMER);
            queryCustomerByPlace = conn.prepareStatement(QUERY_CUSTOMER_BY_PLACE);
            queryList = conn.prepareStatement(QUERY_LIST);
            queryCustomerList = conn.prepareStatement(QUERY_CUSTOMER_LIST);
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
            if (insertIntoList != null) {
                insertIntoList.close();
            }
            if (insertIntoCustomerList != null) {
                insertIntoCustomerList.close();
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
            if (queryList != null) {
                queryList.close();
            }
            if (queryCustomerList != null) {
                queryCustomerList.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public boolean insertCustomer(String placeName, String name, String surname, String cellphone) {
        try {
            conn.setAutoCommit(false);

            int placeId = insertPlace(placeName);

            insertIntoCustomer.setString(1, name);
            insertIntoCustomer.setString(2, surname);
            insertIntoCustomer.setString(3, cellphone);
            insertIntoCustomer.setInt(4, placeId);

            int affectedRows = insertIntoCustomer.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                throw new SQLException("Customer insert failed");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Insert customer exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
        return false;
    }

    public int insertPlace(String name) throws SQLException {
        queryPlace.setString(1, name);
        ResultSet results = queryPlace.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            insertIntoPlace.setString(1, name);
            int affectedRows = insertIntoPlace.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert place!");
            }
            ResultSet generatedKey = insertIntoPlace.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new SQLException("Couldn't get ID for place!");
            }
        }
    }

    public int insertList(String name) throws SQLException {
        queryList.setString(1, name);
        ResultSet results = queryList.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            insertIntoList.setString(1, name);
            int affectedRows = insertIntoList.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert list");
            }
            ResultSet generatedKey = insertIntoList.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new SQLException("Couldn't get ID for List!");
            }
        }
    }

    public int insertCustomerList(int customerListId, int customerID) throws SQLException {

        try {
            queryCustomerList.setInt(1, customerListId);
            queryCustomerList.setInt(2, customerID);
            ResultSet resultSet = queryCustomerList.executeQuery();
            if (resultSet.next()) {
                System.out.println("Customer already added!");
                return resultSet.getInt(1);
            } else {
                insertIntoCustomerList.setInt(1, customerListId);
                insertIntoCustomerList.setInt(2, customerID);
                int affectedRows = insertIntoCustomerList.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Couldn't insert customer into list");
                }

                ResultSet generatedKey = insertIntoCustomerList.getGeneratedKeys();
                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                } else {
                    throw new SQLException("Couldn't get ID for customer list");
                }
            }
        } catch (SQLException e) {
            System.out.println("Insert customer List exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("Things are really bad! " + e2.getMessage());
            }
        }
        return -1;
    }

    public boolean insertTransaction(int customerId, double amount, Date date){
        try {
            insertIntoTransaction.setInt(1,customerId);
            insertIntoTransaction.setDouble(2,amount);
            insertIntoTransaction.setDate(3,date);
            int affectedRows = insertIntoTransaction.executeUpdate();

            if (affectedRows == 1) {
                return true;
            }else {
                throw new SQLException("Couldn't insert into transaction");
            }

        }catch (SQLException e){
            System.out.println("Insert transaction failed: " + e.getMessage());
        }
        return false;
    }

    public List<Lists> queryList() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_lIST)) {
            List<Lists> customerList = new ArrayList<>();

            while (results.next()) {
                Lists lists = new Lists();
                lists.setListId(results.getInt(COLUMN_lIST_ID));
                lists.setListName(results.getString(COLUMN_LIST_NAME));
                customerList.add(lists);
            }
            return customerList;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Place> queryPlace() {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_PLACE)) {
            List<Place> places = new ArrayList<>();

            while (resultSet.next()) {
                Place place = new Place();
                place.setPlaceId(resultSet.getInt(COLUMN_PLACE_ID));
                place.setPlaceName(resultSet.getString(COLUMN_PLACE_NAME));
                places.add(place);
            }
            return places;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> queryCustomer() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CUSTOMER)) {

            List<Customer> customers = new ArrayList<>();

            while (results.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(results.getInt(COLUMN_CUSTOMER_ID));
                customer.setName(results.getString(COLUMN_CUSTOMER_NAME));
                customer.setSurname(results.getString(COLUMN_CUSTOMER_SURNAME));
                customer.setCellPhone(results.getString(COLUMN_CUSTOMER_CELLPHONE));
                customer.setPlaceId(results.getInt(COLUMN_CUSTOMER_PLACE_ID));
                customers.add(customer);
            }

            return customers;


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
}
