package entitties;

import java.util.ArrayList;

public class Customer {
//    private int customerID;
    private String name;
    private String surname;
    private String cellPhone;
    private ArrayList<Double> transaction;

    public Customer( String name, String surname, String cellPhone) {
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
}
