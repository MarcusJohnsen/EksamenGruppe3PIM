package businessLogic;

import java.util.ArrayList;

/**
 * 
 * @author Marcus
 */
public class Distributor {

    private int distributorID;
    private String distributorName;
    private String distributorDescription;
    
    private static ArrayList<Distributor> distributorList = new ArrayList();

    public Distributor (int distributorID, String distributorName, String distributorDescription) {
        this.distributorID = distributorID;
        this.distributorName = distributorName;
        this.distributorDescription = distributorDescription;
    }
    
    public static void setupDistributorListFromDB(ArrayList<Distributor> distributorListFromDB) {
        distributorList = distributorListFromDB;
    }

    public static boolean validateDistributorInput(String distributorName, String distributorDescription) throws IllegalArgumentException {
        if (distributorName.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (distributorDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }
        return true;
    }

    static void addToDistributorList(Distributor distributor) {
        distributorList.add(distributor);
    }
    
    
    
    
    
    public int getDistributorID() {
        return distributorID;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getDistributorDescription() {
        return distributorDescription;
    }
}