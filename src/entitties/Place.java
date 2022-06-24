package entitties;

import model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private int placeId;
    private String placeName;
    private List<Customer> customers;
    private final DataModel dataModel =  DataModel.getInstance();

    public Place() {
        dataModel.open();
        this.customers = dataModel.queryCustomer();

    }

    public Place(String placeName) {
        this.placeName = placeName;
        this.customers = dataModel.queryCustomer();
    }


    public String getPlaceName() {
        return placeName;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean newCustomer(String name, String surname, String cellPhone) {
        Customer existingCustomer = findCustomer(name);
        if (existingCustomer == null) {
            this.customers.add(new Customer(name, surname, cellPhone));
            return true;
        }
        return false;
    }

    public boolean addCustomerTransaction(String fullName, double amount) {
        Customer existingCustomer = findCustomer(fullName);
        if (existingCustomer != null) {
            existingCustomer.addTransaction(amount);
            return true;
        }
//        System.out.println("Customer does not exists");
        return false;
    }

    private Customer findCustomer(String fullName) {
        for (int i = 0; i < customers.size(); i++) {
            Customer existingCustomer = this.customers.get(i);
            if (existingCustomer.getName().equalsIgnoreCase(fullName)) {
                return existingCustomer;
            }
        }
        return null;
    }

    public void printCustomers(){
       if (this.customers != null) {
           System.out.println("Customers");
           for (var c : this.customers){
               System.out.println(c);
           }
       }
    }

    @Override
    public String toString() {
        return placeName;
    }
}
