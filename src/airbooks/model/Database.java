package airbooks.model;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Database {
    // Attributes
    private ArrayList<Student> studentDB;
    private ArrayList<Book> booksDB;
    private ArrayList<SelfCollectStn> selfcollectDB;
    private ArrayList<String> districtAreas;

    // Constructors
    public Database(String studentFile, String booksFile, String selfCollectFile, String districtFile) {
        loadStudentDB(studentFile);
        loadBookDB(booksFile);
        loadSelfCollectDB(selfCollectFile);
        loadDistrictAreas(districtFile);
    }

    // Methods
    public void loadStudentDB(String filename) {
        studentDB = new ArrayList<>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = csv.readLine()) != null) {
                String[] attributes = line.split(",");
                studentDB.add(new Student(attributes[0], attributes[1], attributes[2]));
            }
            csv.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadBookDB(String filename) {
        booksDB = new ArrayList<>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = csv.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length == 6) {
                    booksDB.add(new Book(attributes[0], attributes[1], attributes[2], attributes[3],
                            Double.parseDouble(attributes[4]), Integer.parseInt(attributes[5])));
                } else {
                    booksDB.add(new Book(attributes[0], attributes[1], attributes[2], attributes[3],
                            Double.parseDouble(attributes[4]), Integer.parseInt(attributes[5]),
                            attributes[6], attributes[7]));
                }
            }
            csv.close();
        } catch (IOException | NumberFormatException | ParseException e) {
            return;
        }
    }

    public void loadSelfCollectDB(String filename) {
        selfcollectDB = new ArrayList<>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = csv.readLine()) != null) {
                String[] attributes = line.split(",");
                selfcollectDB.add(new SelfCollectStn(attributes[0], attributes[1], Integer.parseInt(attributes[2])));
            }
            csv.close();
        } catch (IOException | NumberFormatException e) {
            return;
        }
    }

    public void loadDistrictAreas(String filename) {
        districtAreas = new ArrayList<>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = csv.readLine()) != null) {
                String[] attributes = line.split(",");
                districtAreas.add(attributes[0]);
            }
            csv.close();
            Collections.sort(districtAreas);
        } catch (IOException | NumberFormatException e) {
            return;
        }
    }

    public Student getStudent(String studentID) {
        if (!Student.checkStudentID(studentID)) {
            throw new IllegalArgumentException("Invalid Student ID!");
        }
        for (Student s : studentDB) {
            if (s.getStudentID().equals(studentID)) {
                return s;
            }
        }
        return null;
    }

    public Book getBook(String ISBN) {
        if (!Book.checkISBN(ISBN)) {
            throw new IllegalArgumentException("Invalid ISBN!");
        }
        for (Book b : booksDB) {
            if (b.getISBN().equals(ISBN)) {
                return b;
            }
        }
        return null;
    }

    public ArrayList<String> getPossibleSubjCodes() {
        ArrayList<String> subjects = new ArrayList<>();
        for (Book b : booksDB) {
            if (!subjects.contains(b.getSubjectCode())) {
                subjects.add(b.getSubjectCode());
            }
        }
        Collections.sort(subjects);
        return subjects;
    }

    public ArrayList<Book> getBooklistBySubjCode(String subjCode, ArrayList<Book> rentalCart) {
        // Logically, books that have the same ISBN should be the same book
        // Thus the definition for same books will be if the ISBN is equal
        ArrayList<String> rentalISBN = new ArrayList<>();
        for (Book b : rentalCart) {
            rentalISBN.add(b.getISBN());
        }
        ArrayList<Book> booklist = new ArrayList<>();
        for (Book b : booksDB) {
            if (b.getSubjectCode().equals(subjCode) && !b.getIsRented() && !rentalISBN.contains(b.getISBN())) {
                booklist.add(b);
            }
        }
        return booklist;
    }

    public SelfCollectStn getSelfCollection(String postal) {
        for (SelfCollectStn scs : selfcollectDB) {
            if (scs.getPostalCode().equals(postal)) {
                return scs;
            }
        }
        return null;
    }

    public ArrayList<SelfCollectStn> getNearbySelfCollection(String postal) {
        if (postal.length() != 6) {
            return null;
        }
        try {
            Integer.parseInt(postal);
        } catch (NumberFormatException e) {
            return null;
        }
        String districtArea = postal.substring(0, 2);
        String[] validDistrictAreas = {};
        for (String district : districtAreas) {
            if (district.contains(districtArea)) {
                validDistrictAreas = district.split("-");
                break;
            }
        }
        ArrayList<SelfCollectStn> results = new ArrayList<>();
        for (SelfCollectStn scs : selfcollectDB) {
            for (String validDistrictArea : validDistrictAreas) {
                if (scs.getPostalCode().substring(0, 2).equals(validDistrictArea)) {
                    results.add(scs);
                    break;
                }
            }
        }
        return results;
    }

    public void writeBook(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            for (Book b : booksDB) {
                if (b.getIsRented()) {
                    out.println(b.getISBN() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getSubjectCode() +
                            "," + b.getDeposit() + "," + b.getRentalPeriod() + "," + b.getStudentID() + "," +
                            b.getRentalDate());
                } else {
                    out.println(b.getISBN() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getSubjectCode() +
                            "," + b.getDeposit() + "," + b.getRentalPeriod());
                }
            }
            out.close();
        } catch (IOException e) {
            return;
        }
    }

    public ArrayList<Book> getBooksDB() {
        return booksDB;
    }
}
