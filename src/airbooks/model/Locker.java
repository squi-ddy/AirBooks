package airbooks.model;

import java.util.ArrayList;
import java.util.Random;

public class Locker {
    // Attributes
    private int lockerNum;
    private String lockerPassword;
    private String studentID;
    private ArrayList<Book> booklist;

    // Constructors
    public Locker(int lockerNum) {
        if (lockerNum < 0) {
            throw new IllegalArgumentException("Cannot create Locker! Invalid locker number!");
        }
        this.lockerNum = lockerNum;
        this.lockerPassword = "NUSHabAdmin";
        this.studentID = "h2100000";
        this.booklist = new ArrayList<>();
    }

    // Accessors
    public int getLockerNum() {
        return lockerNum;
    }

    public String getStudentID() {
        return studentID;
    }

    public ArrayList<Book> getBookList() {
        ArrayList<Book> deepCopy = new ArrayList<>();
        for (Book b : booklist) {
            deepCopy.add(new Book(b));
        }
        return deepCopy;
    }

    // Methods
    public boolean isEmpty() {
        return booklist.isEmpty();
    }

    public String placeItem(String studentID, ArrayList<Book> booklist) {
        if (!Student.checkStudentID(studentID)) {
            throw new IllegalArgumentException("Invalid Student ID.");
        }
        this.studentID = studentID;
        String password = "";
        Random generator = new Random();
        for (int i = 0; i < 8; i++) {
            password += (char)(generator.nextInt(58) + 33);
        }
        this.lockerPassword = password;
        this.booklist = booklist;
        return password;
    }

    public String unlockLocker(String password) {
        if (isEmpty()) {
            return "The locker is empty.";
        }
        if (password.equals(lockerPassword)) {
            String returnString = "Thank you for using AirBooks! Please collect your items:\n";
            for (Book b : booklist) {
                returnString += b;
                returnString += '\n';
            }
            this.lockerPassword = "NUSHabAdmin";
            this.studentID = "h2100000";
            this.booklist = new ArrayList<>();
            return returnString;
        }
        return "Please ensure you keyed in the correct password.";
    }

    @Override
    public String toString(){
        if (isEmpty()) {
            return "Locker is empty";
        } else {
            return "Locker " + lockerNum + "; Password: " + lockerPassword + "\nPending collection by: " + studentID;
        }
    }
}
