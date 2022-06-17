package entitties;

import model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ConsultList {
    private String name;
    private ArrayList<Lists> customerLists;
    private List<Place> places;
    DataModel dataModel = new DataModel();

    public ConsultList(String name) {
        this.name = name;
        this.customerLists = new ArrayList<>();
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

    public boolean addList(String listName){
        Lists lists = findLists(listName);
        if (lists == null){
            this.customerLists.add(new Lists(listName));
            return true;
        }
        return false;
    }
    private Lists findLists(String listName){
        for (int i=0;i< customerLists.size();i++){
            Lists checkedLists = this.customerLists.get(i);
            if (checkedLists.getListName().equalsIgnoreCase(listName)){
                return checkedLists;
            }
        }
        return null;
    }
    public boolean listCustomers(String placeName, boolean showTransaction) {
        Place place = findPlace(placeName);
        if (place != null) {
            System.out.println("Customer details for " + place.getPlaceName());

            ArrayList<Customer> customers = place.getCustomers();
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
