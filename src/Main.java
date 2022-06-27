import entitties.AttendanceList;
import entitties.ConsultList;
import entitties.Place;
import model.DataModel;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConsultList consult = new ConsultList();
    private static final DataModel dataModel =  DataModel.getInstance();
    private static final AttendanceList attendanceList = new AttendanceList();

    public static void main(String[] args) throws SQLException {


        if (!dataModel.open()) {
            System.out.println("Can't open datasource");
        }
        menu();
        boolean quit = false;

        while (!quit) {
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("Closing app...................");
                    quit = true;
                    break;
                case 1:
                    addCustomer();
                    break;
                case 2:
                    Place place = new Place();
                    place.printCustomersToFile();
                    break;
                case 3:
                    consult.printList();
                    break;
                case 4:
                    consult.updatePlaceName();
//                    consult.listPlaceCustomer();
                    break;
                case 5:
                    System.out.println("Enter place name");
                    String name = scanner.nextLine();
                    consult.listCustomers(name,false);
//                    addTransaction();
                    break;
                case 6:
                    menu();
                    break;
            }
        }


//        List<Place> customers = dataModel.queryPlace();
//        for (var c : customers) {
//            System.out.println(c);
//        }


        dataModel.close();

    }

    private static void menu() {
        System.out.println("Available action\n\tpress:");
        System.out.println("\t\t0 - to quit\n" +
                "\t\t1 - to add customer\n" +
                "\t\t2 - to view customers\n" +
                "\t\t3 - to view all places\n" +
                "\t\t4 - to view place per customers\n" +
                "\t\t5 - to add transaction\n" +
                "\t\t6 - print available actions");
    }

    private static void addCustomer() {
        System.out.print("Enter place name: ");
        String placeName = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter cell phone: ");
        String cellPhone = scanner.nextLine();

        if (dataModel.insertCustomer(placeName,name,surname,cellPhone)) {
            System.out.println("Customer " + name + " " + surname + " from " + placeName + " created");
        }else {
            System.out.println("Cannot add customer");
        }

    }
}