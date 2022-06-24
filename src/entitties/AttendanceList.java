package entitties;

import model.DataModel;

import java.sql.Date;
import java.util.List;

public class AttendanceList {
    private List<Attendance> attendances ;
    private List<Place> places;
    private DataModel dataModel =  DataModel.getInstance();

    public AttendanceList() {
        dataModel.open();
        attendances = dataModel.queryAttendance();
        places = dataModel.queryPlace();
    }

    public boolean addAttendance(int custId, Date dateOfTransaction, int numberOfAttendance){
        int customerID = findCustomer(custId);
        System.out.println(customerID);
        if (customerID >= 0){
            for (int i=0;i<attendances.size();i++){
                Attendance attendance = attendances.get(i);
                if ((attendance.getCustomerID()==customerID) && (attendance.getAttendanceDate()==dateOfTransaction)){
                    attendance.addAdjustAttendance(numberOfAttendance);
                    return true;
                }else {
                    dataModel.insertAttendance(customerID,dateOfTransaction,numberOfAttendance);
                }
            }
        }
        System.out.println("Attendance not successful");
        return false;
    }
    private int findCustomer(int custID) {
        int index = custID - 1;
        for (Place place : places) {
            List<Customer> customers = place.getCustomers();
            if ((index >= 0) && (index < customers.size())) {
                System.out.println(customers.get(index));
                return customers.get(index).getCustomerID();
            }
        }
        return -1;
    }
}
