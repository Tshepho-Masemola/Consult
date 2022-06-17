package entitties;

public class Attendance {
    private int customerID;
    private String attendanceDate;
    private int numberOfAttendance;

    public Attendance() {
    }

    public Attendance(int customerID, String attendanceDate, int numberOfAttendance) {
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

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getNumberOfAttendance() {
        return numberOfAttendance;
    }

    public void setNumberOfAttendance(int numberOfAttendance) {
        this.numberOfAttendance = numberOfAttendance;
    }
}
