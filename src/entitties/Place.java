package entitties;

import java.util.ArrayList;

public class Place {
    private int placeId;
    private String placeName;
    private ArrayList<Customer> customers;

    public Place(int placeId, String placeName) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.customers = new ArrayList<>();
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public boolean newCustomer(String name, String surname, String cellPhone) {
        String names = name + " " + surname;
        Customer existingCustomer = findCustomer(names);
        if (existingCustomer != null) {
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
        return false;
    }

    private Customer findCustomer(String fullName) {
        for (int i = 0; i < customers.size(); i++) {
            Customer existingCustomer = this.customers.get(i);
            String name = existingCustomer.getSurname();
            String surname = existingCustomer.getSurname();
            String names = name + " " + surname;
            if (names.equalsIgnoreCase(fullName)) {
                return existingCustomer;
            }
        }
        return null;
    }
}
