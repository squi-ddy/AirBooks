package airbooks.model;

import java.util.Random;

public class SelfCollectStn {
    private String postalCode;
    private String areaDetails;
    private int numOfLockers;
    private Locker[] lockersArray;

    public SelfCollectStn(String postalCode, String areaDetails, int numOfLockers){
        if (numOfLockers <= 0) throw new IllegalArgumentException("Cannot create SelfCollectStn. Invalid number of lockers!");
        this.postalCode = postalCode;
        this.areaDetails = areaDetails;
        this.numOfLockers = numOfLockers;
        this.lockersArray = new Locker[numOfLockers];
        for (int i = 0; i < numOfLockers; i++){
            lockersArray[i] = new Locker(i);
        }
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAreaDetails() {
        return areaDetails;
    }

    public int getNumOfLockers() {
        return numOfLockers;
    }

    public Locker getLocker(int lockerNo){
        return lockersArray[lockerNo];
    }
    public int getEmptyLockerNum(){
        Random rand = new Random();
        int randNum = 999;
        while (true){
            randNum = rand.nextInt(numOfLockers);
            if (lockersArray[randNum].isEmpty()) break;
        }
        return randNum;
    }

    @Override
    public String toString(){
        return "Self-Collection point at " + areaDetails + " (" + postalCode + ")";
    }
}
