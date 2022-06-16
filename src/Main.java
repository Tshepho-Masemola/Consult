import entitties.Customer;
import model.DataModel;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataModel dataModel = new DataModel();

        if (!dataModel.open()){
            System.out.println("Can't open datasource");
        }

        List<Customer> customers = dataModel.queryCustomer();

        for (var c : customers){
            System.out.println(c.getCustomerID() + " " + c.getName() + " " + c.getSurname());
        }

        dataModel.close();
//        ConsultList consult = new ConsultList("Test");
//
//        consult.addPlace("Phokoane");
//        consult.addCustomer("Phokoane", "Linah", "Magolego",  "0832479619");
//        consult.addCustomer("Phokoane", "Steave", "Mmamonyane",  "0727689036");
//        consult.addCustomer("Phokoane", "Moses", "Mmakola",  "0766706390");
//        consult.addCustomer("Phokoane", "Selinah", "Makunyane",  "0768226490");
//        consult.addCustomer("Phokoane", "Linah", "Magolego",  "0832479619");
//
//        consult.addPlace("Lefufountein");
//        consult.addCustomer("Lefufountein", "Sinah", "Mmamogobo",  "0734320566");
//        consult.addPlace("Jane Furse");
//        consult.addCustomer("Jane Furse", "Jonny", "Maake", "0829390595");
//        consult.addPlace("Riverside");
//        consult.addCustomer("Riverside", "Mmakadikwe", "Mahubene",  "0822156433");
//
//        consult.addCustomerTransaction("Phokoane","Linah",1500);
//        consult.addCustomerTransaction("Phokoane","Linah",1200);
//
//        consult.listCustomers("Phokoane",true);


    }
}