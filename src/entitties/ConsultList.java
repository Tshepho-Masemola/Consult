package entitties;

import model.DataModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConsultList {
    private String name;
    private final List<Lists> customerLists;
    private final List<Place> places;
    DataModel dataModel = new DataModel();

    public ConsultList() {
        dataModel.open();
        this.customerLists = dataModel.queryList();
        this.places = dataModel.queryPlace();

    }

    public ConsultList(String name) {
        this.name = name;
        this.customerLists = dataModel.queryList();
        this.places = dataModel.queryPlace();
    }

    public boolean addPlace(String placeName) {
        if (findPlace(placeName) == null) {
            this.places.add(new Place(placeName));
            return true;
        }

        return false;
    }


    public boolean addCustomer(String placeName, String name, String surname, String cellPhone) {
        Place place = findPlace(placeName);
        if (place != null) {
            return place.newCustomer(name, surname, cellPhone);
        }
        return false;
    }

    public boolean addCustomerTransaction(String placeName, String customerName, double amount) {
        Place place = findPlace(placeName);
        if (place != null) {
            place.addCustomerTransaction(customerName, amount);
            return true;
        }
        return false;
    }

    private Place findPlace(String placeName) {
        for (int i = 0; i < this.places.size(); i++) {
            Place checkedPlace = this.places.get(i);
            if (checkedPlace.getPlaceName().equalsIgnoreCase(placeName)) {
                return checkedPlace;
            }
        }
        return null;
    }

    public boolean addList(String listName) {
        Lists lists = findLists(listName);
        if (lists == null) {
            this.customerLists.add(new Lists(listName));
            return true;
        }
        return false;
    }

    private Lists findLists(String listName) {
        for (int i = 0; i < customerLists.size(); i++) {
            Lists checkedLists = this.customerLists.get(i);
            if (checkedLists.getListName().equalsIgnoreCase(listName)) {
                return checkedLists;
            }
        }
        return null;
    }

    public boolean addTransaction(int customerID, double amount){
        int customer = findCustomer(customerID);
        if (customerID > 0){
            java.sql.Date date = new  java.sql.Date(Calendar.getInstance().getTime().getTime());
            dataModel.insertTransaction(customerID,amount, date);
            return true;
        }
        return false;
    }

    public boolean addCustomerList(int listID, int custID) throws SQLException {
        int customerListId = findList(listID);
        int customerId = findCustomer(custID);
        if ((customerListId > 0) && (customerId > 0)) {
            int returned = dataModel.insertCustomerList(customerListId, customerId);
            System.out.println("Customer added successfully to list." + returned);
            return true;
        }
        return false;
    }

    private int findCustomer(int custID) {
        int index = custID - 1;
        for (Place place : places) {
            List<Customer> customers = place.getCustomers();
                if ((index > 0) && (index < customers.size())) {
                    return customers.get(index).getCustomerID();

            }
        }
        return -1;
    }

    private int findList(int listID) {
        int index = listID - 1;

        if ((index > 0) && (index < customerLists.size())) {
            return customerLists.get(index).getListId();
        }
        return -1;
    }

    public void printList() {
        for (Lists lists : customerLists) {
            System.out.println(lists.getListId() + ": " + lists.getListName());
        }
    }

    public void listPlaceCustomer() {
        for (Place place : places) {
            if (place != null) {
                System.out.println("Customers for: " + place.getPlaceName());
                List<Customer> customers = place.getCustomers();
                for (int i = 0; i < customers.size(); i++) {
                    Customer customer = customers.get(i);
                    if (place.getPlaceId() == customer.getPlaceId()) {
                        System.out.println("\t" + customers.get(i));
                    }
                }
            }

        }
    }

    public boolean listCustomers(String placeName, boolean showTransaction) {
        Place place = findPlace(placeName);
        if (place != null) {
            System.out.println("Customer details for " + place.getPlaceName());

            List<Customer> customers = place.getCustomers();
            for (int i = 0; i < customers.size(); i++) {
                Customer placeCustomer = customers.get(i);
                System.out.println("Customer name: " + placeCustomer.getName() + ", Customer Surname: " + placeCustomer.getSurname() +
                        ", cell phone: " + placeCustomer.getCellPhone() + " [" + (i + 1) + "]");

                if (showTransaction) {
                    System.out.println("\tTransactions");
                    ArrayList<Double> transactions = placeCustomer.getTransaction();
                    for (int j = 0; j < transactions.size(); j++) {
                        System.out.println("\t\t[" + (j + 1) + "] Amount: R" + transactions.get(j));
                    }
                }
            }
            return true;
        }
        System.out.println("No data");
        return false;
    }
}
