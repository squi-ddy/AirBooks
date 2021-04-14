package airbooks.model;

import java.io.*;
import java.util.ArrayList;

public class Security {
    private ArrayList<Account> loginDB;
    private static String currStudentLogin;

    public Security(String filename){
        loginDB = new ArrayList<Account>();
        loadLoginDB(filename);
    }

    public Account getAccount(String studentID){
        if (!Student.checkStudentID(studentID))
            throw new IllegalArgumentException("Invalid Student ID!");

        for (Account a : loginDB){
            if(a.getStudentID().equals(studentID))
                return a;
        }
        return null;
    }

    public void loadLoginDB(String filename){
        BufferedReader br = null;
        try{
            String line;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                loginDB.add(new Account(data[0], data[1], data[2], Double.parseDouble(data[3])));
            }
            br.close();
        }catch (IOException e) { e.printStackTrace();
        }finally{ try { br.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    public static String getCurrStudentLogin(){
        return currStudentLogin;
    }

    public boolean login(String loginID, String password){
        for (Account a : loginDB){
            if (a.getLoginID().equals(loginID) && a.getPassword().equals(password)){
                currStudentLogin = a.getStudentID();
                return true;
            }
        }
        return false;
    }

    public void logout() {currStudentLogin = null;}

    public void writeAccount(String filename){
        int item = 1;
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream(filename));

            for (Account a: loginDB){
                output.println(a.getLoginID()+","
                        + a.getPassword() + ","
                        + a.getStudentID() + ","
                        + a.getWallet() + ",");
                item++;
            }
            output.close();
        } catch(Exception e){
            System.out.println("Error at record " + item);
        }
    }
}
