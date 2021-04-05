package airbooks.model;

import java.io.*;
import java.util.ArrayList;

public class Security {
    // Attributes
    private ArrayList<Account> loginDB;
    private static String currStudentLogin;

    // Constructors
    public Security(String filename) {
        loadLoginDB(filename);
    }

    // Accessors
    public static String getCurrStudentLogin() {
        return currStudentLogin;
    }

    // Methods
    public Account getAccount(String studentID) {
        if (!Student.checkStudentID(studentID)) {
            throw new IllegalArgumentException("Invalid Student ID!");
        }
        for (Account a : loginDB) {
            if (a.getStudentID().equals(studentID)) {
                return a;
            }
        }
        return null;
    }

    public void loadLoginDB(String filename) {
        loginDB = new ArrayList<>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = csv.readLine()) != null) {
                String[] attributes = line.split(",");
                loginDB.add(new Account(attributes[0], attributes[1], attributes[2], Double.parseDouble(attributes[3])));
            }
            csv.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String loginID, String password) {
        for (Account a : loginDB) {
            if (a.getLoginID().equals(loginID) && a.getPassword().equals(password)) {
                currStudentLogin = a.getStudentID();
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currStudentLogin = null;
    }

    public void writeAccount(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            for (Account a : loginDB) {
                out.println(a.getLoginID() + "," + a.getPassword() + "," + a.getStudentID() + "," + a.getWallet());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
