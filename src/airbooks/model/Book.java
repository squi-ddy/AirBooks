package airbooks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String subjectCode;
    private double deposit;
    private int rentalPeriod;

    private boolean isRented;
    private String studentID;
    private Date rentalDate;

    public Book(String ISBN, String title, String author, String subjectCode, double deposit, int rentalPeriod){
        if (!checkISBN(ISBN))
            throw new IllegalArgumentException("Unable to create Book. Invalid ISBN.");
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.subjectCode = subjectCode;
        this.deposit = deposit;
        this.rentalPeriod = rentalPeriod;
        this.isRented = false;
        this.studentID = "h2100000";
        this.rentalDate = null;
    }

    public Book(String ISBN, String title, String author, String subjectCode, double deposit, int rentalPeriod,
                String studentID, String rentalDate) throws ParseException {
        this(ISBN, title, author, subjectCode, deposit, rentalPeriod);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.isRented = true;
        if (!Student.checkStudentID(studentID))
            throw new IllegalArgumentException("Unable to create Book. Invalid Student ID.");
        this.studentID = studentID;
        this.rentalDate = format.parse(rentalDate);

    }

    public Book(Book b){
        this.title = b.title;
        this.author = b.author;
        this.ISBN = b.ISBN;
        this.subjectCode = b.subjectCode;
        this.deposit = b.deposit;
        this.rentalPeriod = b.rentalPeriod;
        this.isRented = b.isRented;
        this.studentID = b.studentID;
        this.rentalDate = b.rentalDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public double getDeposit() {
        return deposit;
    }

    public int getRentalPeriod() {
        return rentalPeriod;
    }

    public boolean getIsRented() {
        return isRented;
    }

    public void setIsRented(boolean isRented) {
        this.isRented = isRented;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        if (!Student.checkStudentID(studentID)) throw new IllegalArgumentException("Invalid Student ID.");
        this.studentID = studentID;
    }

    public String getRentalDate() {
        if (rentalDate == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(rentalDate);
        return dateString;
    }

    public void setRentalDate(Date rentalDate){
        this.rentalDate = rentalDate;
    }

    public static boolean checkISBN(String ISBN){
        if (ISBN.length() != 13) return false;
        for (int i = 0; i < 13; i++){
            if (!Character.isDigit(ISBN.charAt(i))) return false;
        }

        int checkDigit = Integer.parseInt(ISBN.substring(12));
        int sum = 0;
        for (int i = 0; i < 12; i++){
            int digit = Integer.parseInt(ISBN.substring(i,i+1));
            if(i%2 == 0)
                sum += digit;
            else
                sum += digit*3;
        }
        sum = 10 - sum % 10;
        if ((sum == 10 && checkDigit == 0) || sum == checkDigit)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String avail;
        if (isRented) {
            Calendar c = Calendar.getInstance();
            c.setTime(rentalDate);
            c.add(Calendar.DAY_OF_MONTH, rentalPeriod);
            avail = "Rented by " + studentID + ", due " + format.format(c.getTime());
        }
        else
            avail = "Available, " + rentalPeriod + " day rental = $" + String.format("%.2f", deposit);
        return "(" + subjectCode + ")" + " " + title + " by " + author + "; " + avail;
    }
}
