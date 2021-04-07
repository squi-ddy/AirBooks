package airbooks.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SelfCollectStn {
    // Attributes
    private final String postalCode;
    private final String areaDetails;
    private final int numOfLockers;
    private final Locker[] lockersArray;

    // Constructors
    public SelfCollectStn(String postalCode, String areaDetails, int numOfLockers) {
        if (numOfLockers <= 0) {
            throw new IllegalArgumentException("Cannot create SelfCollectStn. Invalid number of lockers!");
        }
        this.postalCode = postalCode;
        this.areaDetails = areaDetails;
        this.numOfLockers = numOfLockers;
        this.lockersArray = new Locker[numOfLockers];
        Arrays.setAll(lockersArray, Locker::new);
    }

    // Accessors
    public int getNumOfLockers() {
        return numOfLockers;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAreaDetails() {return areaDetails;}

    // Methods
    public Locker getLocker(int lockerNo) {
        return lockersArray[lockerNo];
    }

    public int getEmptyLockerNum() {
        ArrayList<Locker> empty = new ArrayList<>();
        for (Locker l : lockersArray) {
            if (l.isEmpty()) {
                empty.add(l);
            }
        }
        Random generator = new Random();
        return empty.get(generator.nextInt(empty.size())).getLockerNum();
    }

    @Override
    public String toString(){
        return "Self-Collection point at " + areaDetails + " (" + postalCode + ")";
    }

}
