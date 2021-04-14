package airbooks.model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Database {
    private ArrayList<Student> studentDB;
    private ArrayList<Book> booksDB;
    private ArrayList<SelfCollectStn> selfcollectDB;
    private ArrayList<String> districtAreas;

    public Database(String studentFile, String booksFile, String selfCollectFile, String districtFile){
        studentDB = new ArrayList<Student>();
        loadStudentDB(studentFile);
        booksDB = new ArrayList<Book>();
        loadBookDB(booksFile);
        selfcollectDB = new ArrayList<SelfCollectStn>();
        loadSelfCollectDB(selfCollectFile);
        districtAreas = new ArrayList<String>();
        loadDistrictAreas(districtFile);
    }

    public void loadStudentDB(String filename){
        BufferedReader br = null;
        try{
            String line;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                studentDB.add(new Student(data[0], data[1], data[2]));
            }
            br.close();
        }catch (IOException e) { e.printStackTrace();
        }finally{ try { br.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    public void loadBookDB(String filename){
        BufferedReader br = null;
        try{
            String line;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    if (data.length == 6)
                        booksDB.add(new Book(data[0], data[1], data[2], data[3], Double.parseDouble(data[4]), Integer.parseInt(data[5])));
                    else
                        booksDB.add(new Book(data[0], data[1], data[2], data[3], Double.parseDouble(data[4]), Integer.parseInt(data[5]),
                                data[6], data[7]));
                } catch(ParseException e){e.printStackTrace();}
            }
            br.close();
        }catch (IOException e) { e.printStackTrace();
        }finally{ try { br.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    public void loadSelfCollectDB(String filename){
        BufferedReader br = null;
        try{
            String line;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                selfcollectDB.add(new SelfCollectStn(data[0], data[1], Integer.parseInt(data[2])));
            }
            br.close();
        }catch (IOException e) { e.printStackTrace();
        }finally{ try { br.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    public void loadDistrictAreas(String filename){
        BufferedReader br = null;
        try{
            String line;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                districtAreas.add(data[0]);
            }
            br.close();
        }catch (IOException e) { e.printStackTrace();
        }finally{ try { br.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public Student getStudent(String studentID){
        if (!Student.checkStudentID(studentID)) throw new IllegalArgumentException("Invalid Student ID!");
        for (Student s : studentDB) {
            if (s.getStudentID().equals(studentID))
                return s;
        }
        return null;
    }

    public Book getBook(String ISBN){
        if (!Book.checkISBN(ISBN)) throw new IllegalArgumentException("Invalid ISBN!");
        for (Book b : booksDB) {
            if (b.getISBN().equals(ISBN))
                return b;
        }
        return null;
    }

    public ArrayList<Book> getBooksDB() {
        return booksDB;
    }

    public ArrayList<String> getPossibleSubjCodes(){
        ArrayList<String> subjCodes = new ArrayList<>();
        for (Book b: booksDB){
            if (!subjCodes.contains(b.getSubjectCode()))
                subjCodes.add(b.getSubjectCode());
        }
        Collections.sort(subjCodes);
        return subjCodes;
    }
    public ArrayList<Book> getBooklistBySubjCode(String subjCode, ArrayList<Book> rentalCart){
        ArrayList<Book> temp = new ArrayList<Book>();
        boolean inRental;
        for (Book b : booksDB) {
            inRental = false;
            if (b.getSubjectCode().equals(subjCode) && !b.getIsRented()){
                for (Book ex : rentalCart){
                    if (ex.getISBN().equals(b.getISBN()))
                        inRental = true;
                }
                if (!inRental) temp.add(b);
            }
        }
        return temp;
    }

    public SelfCollectStn getSelfCollection(String postal){
        for (SelfCollectStn s: selfcollectDB){
            if (s.getPostalCode().equals(postal))
                return s;
        }
        return null;
    }

    public ArrayList<SelfCollectStn> getNearbySelfCollection(String postal){
        if (!postal.matches("\\d{6}")) return null;
        String area = postal.substring(0,2);
        ArrayList<String> nearbyDist;
        ArrayList<SelfCollectStn> stations = new ArrayList<SelfCollectStn>();
        for (String s : districtAreas){
            nearbyDist = new ArrayList<String>(Arrays.asList(s.split("-")));
            if (nearbyDist.contains(area)) {
                for (String nD : nearbyDist){
                    for (SelfCollectStn sc : selfcollectDB) {
                        if (sc.getPostalCode().substring(0, 2).equals(nD)) stations.add(sc);
                    }
                }
            }
        }
        return stations;
    }

    public void writeBook(String filename){
        int item = 1;
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream(filename));

            for (Book b: booksDB){
                if (!b.getIsRented()){
                    output.println(b.getISBN()+","
                            + b.getTitle() + ","
                            + b.getAuthor() + ","
                            + b.getSubjectCode() + ","
                            + b.getDeposit() + ","
                            + b.getRentalPeriod());
                }
                else{
                    output.println(b.getISBN() + ","
                            + b.getTitle() + ","
                            + b.getAuthor() + ","
                            + b.getSubjectCode() + ","
                            + b.getDeposit() + ","
                            + b.getRentalPeriod() + ","
                            + b.getStudentID() + ","
                            + b.getRentalDate());
                }
                item++;
            }
            output.close();
        } catch(Exception e){System.out.println("Error at record: " + item); }
    }
}
