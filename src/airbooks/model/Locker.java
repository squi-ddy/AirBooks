package airbooks.model;

import java.util.ArrayList;
import java.util.Random;

public class Locker {
    private int lockerNum;
    private String lockerPassword;
    private String studentID;
    private ArrayList<Book> booklist;
    
    public Locker(int lockerNum){
        if (lockerNum < 0) throw new IllegalArgumentException("Cannot create Locker! Invalid locker number!");
        else this.lockerNum = lockerNum;
        this.lockerPassword = "NUSHabAdmin";
        this.studentID = "h2100000";
        this.booklist = new ArrayList<Book>();
    }

    public int getLockerNum(){return lockerNum;}
    public String getStudentID(){return studentID;}

    public ArrayList<Book> getBookList() {
        ArrayList<Book> temp = new ArrayList<Book>();
        for (Book b : booklist){
            temp.add(new Book(b));
        }
        return temp;
    }

    public boolean isEmpty(){
        if(booklist.isEmpty()) return true;
        else return false;
    }

    public String placeItem(String studentID, ArrayList<Book> booklist){
        lockerPassword = "";
        Random rand = new Random();
        for (int i = 0; i < 8; i++){
            lockerPassword += (char)(rand.nextInt(58)+33);
        }
        if (Student.checkStudentID(studentID))
            this.studentID = studentID;
        else
            throw new IllegalArgumentException("Invalid Student ID.");
        this.booklist = booklist;
        return lockerPassword;
    }

    public String unlockLocker(String password){
        if (this.isEmpty())
            return "The locker is empty.";
        if (password.equals(lockerPassword)) {
            this.lockerPassword = "NUSHabAdmin";
            this.studentID = "";
            ArrayList<Book> templist = this.getBookList();
            booklist.clear();
            String rtrn = "Thank you for using AirBooks! Please collect your items:\n";
            for (Book b : templist){
                rtrn += b+"\n";
            }
            return rtrn;
        }
        else
            return "Please ensure you keyed in the correct password.";
    }

    @Override
    public String toString(){
        if (this.isEmpty())
            return "Locker is empty";
        else {
            return "Locker " + lockerNum + "; Password: " + lockerPassword + "\nPending collection by: " + studentID;
        }
    }
}
