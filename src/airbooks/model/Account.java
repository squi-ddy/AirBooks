package airbooks.model;

import java.util.Date;

public class Account {
    private final String loginID;
    private final String password;
    private final String studentID;
    private double wallet;

    public Account(String loginID, String password, String studentID, double wallet) {
        if (!Student.checkStudentID(studentID)) {
            throw new IllegalArgumentException("Unable to create Account. Invalid Student ID.");
        } else {
            this.loginID = loginID;
            this.password = password;
            this.studentID = studentID;
            this.wallet = wallet;
        }
    }

    public String getLoginID() {
        return loginID;
    }
    public String getPassword() {
        return password;
    }
    public String getStudentID() {
        return studentID;
    }
    public double getWallet() {
        return wallet;
    }

    public String addToWallet(double amt){
        if (wallet + amt > 25){
            return "Unable to add to " + studentID +" wallet as it will exceed the maximum of $25.";
        } else {
            wallet += amt;
            return "$" + String.format("%.2f", amt) + " was added to " + studentID + " wallet.";
        }
    }
    public String deductFromWallet(double amt){
        if (wallet - amt < 0) {
            return "Insufficient funds for deduction. Please top up the wallet!";
        }
        else{
            wallet -= amt;
            return "$" + String.format("%.2f",amt) + " was deducted from " + studentID +" wallet.";
        }
    }
    public String returnBook(Book bk) {
        if (!bk.getIsRented()) return "Cannot return a book which is not rented.";
        if (wallet + bk.getDeposit() > 25) return "Unable to return book. Wallet will exceed $25.";
        bk.setIsRented(false);
        bk.setStudentID("h2100000");
        bk.setRentalDate(null);
        return "Book \"" + bk.getTitle() + "\" successfully returned.";
    }

    public String rentBook(Book bk) {
        if (bk.getIsRented()) return "Cannot rent a book which is already rented.";
        if (wallet - bk.getDeposit() < 0) return "Unable to rent book. Insufficient funds in wallet.";
        bk.setIsRented(true);
        bk.setStudentID(this.getStudentID());
        bk.setRentalDate(new Date());
        return "Book \"" + bk.getTitle() + "\" successfully rented.";
    }

    @Override
    public String toString() {
        return this.studentID + " (" + this.loginID + ", " +  this.password + ") with wallet $" + String.format("%.2f", wallet);
    }
}
