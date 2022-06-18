package entitties;

import java.util.ArrayList;

public class Customer {
    private int customerID;
    private String name;
    private String surname;
    private String cellPhone;
    private int placeId;
    private ArrayList<Double> transaction;

    public Customer() {
    }

    public Customer(String name, String surname, String cellPhone) {
//        this.customerID = customerID;
        this.name = name;
        this.surname = surname;
        this.cellPhone = cellPhone;
        this.transaction = new ArrayList<>();
    }

    public void addTransaction(double amount){
        if (amount > 0){
            this.transaction.add(amount);
        }
        else {
            System.out.println(amount + " must greater than zero");
        }
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public ArrayList<Double> getTransaction() {
        return transaction;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setTransaction(ArrayList<Double> transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Surname: " + surname + ", Cell phone: " + cellPhone;
    }
}
