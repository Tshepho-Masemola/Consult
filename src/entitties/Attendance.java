package entitties;

import java.sql.Date;

public class Attendance {
    private int customerID;
    private Date attendanceDate;
    private int numberOfAttendance;

    public Attendance() {
    }

    public Attendance(int customerID, Date attendanceDate, int numberOfAttendance) {
        this.customerID = customerID;
        this.attendanceDate = attendanceDate;
        this.numberOfAttendance = numberOfAttendance;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getNumberOfAttendance() {
        return numberOfAttendance;
    }

    public void setNumberOfAttendance(int numberOfAttendance) {
        this.numberOfAttendance = numberOfAttendance;
    }

    public void addAdjustAttendance(int attendance){
        int newAttendanceNumber = this.numberOfAttendance + attendance;
        if (newAttendanceNumber >0){
            this.numberOfAttendance = newAttendanceNumber;
        }
    }
}
