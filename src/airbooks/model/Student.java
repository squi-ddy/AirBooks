package airbooks.model;

public class Student {
    private String name;
    private String studentID;
    private String handphoneNo;

    public Student(){
        name = null;
        studentID = "h2100000";
        handphoneNo = "99999999";
    }

    public Student(String studentID, String name, String handphoneNo){
        if(!Student.checkStudentID(studentID))
            throw new IllegalArgumentException("Unable to create Student. Invalid Student ID.");

        else if(!Student.checkHandphoneNo(handphoneNo))
            throw new IllegalArgumentException("Unable to create Student. Invalid Handphone Number.");

        else {
            this.name = name;
            this.studentID = studentID;
            this.handphoneNo = handphoneNo;
        }
    }

    public static boolean checkStudentID(String studentID){
        int year = 2000 + Integer.parseInt(studentID.substring(1, 3));
        if (studentID.length() != 8) return false;
        for (int i = 1; i < 8; i++){
            if (!Character.isDigit(studentID.charAt(i))) return false;
        }
        return studentID.charAt(0) == 'h' && year <= 2021 && year >= 2016;
    }

    public static boolean checkHandphoneNo(String handphoneNo){
        if (handphoneNo.length() != 8) return false;
        for (int i = 0; i < 8; i++){
            if (!Character.isDigit(handphoneNo.charAt(i))) return false;
        }
        if (!(handphoneNo.charAt(0) == '8' || handphoneNo.charAt(0) == '9')) return false;

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        if(!Student.checkStudentID(studentID))
            throw new IllegalArgumentException("Invalid Student ID.");
        this.studentID = studentID;
    }

    public String getHandphoneNo() {
        return handphoneNo;
    }

    public void setHandphoneNo(String handphoneNo) {
        if(!Student.checkHandphoneNo(handphoneNo))
            throw new IllegalArgumentException("Invalid Handphone Number.");
        this.handphoneNo = handphoneNo;
    }

    @Override
    public String toString() {
        return studentID + "," + name + "," + handphoneNo;
    }
}
