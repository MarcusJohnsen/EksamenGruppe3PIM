/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class Bundle {
    private int bundleID;
    private String bundleName;
    private String bundleDescription;

    private static ArrayList<Bundle> bundleList = new ArrayList();
    ArrayList<Product> bundleProducts;
    
    public Bundle(int bundleID, String bundleName, String bundleDescription, ArrayList<Product> bundleProducts) {
        this.bundleID = bundleID;
        this.bundleName = bundleName;
        this.bundleDescription = bundleDescription;
        if (bundleProducts != null) {
            this.bundleProducts = bundleProducts;
        } else {
            this.bundleProducts = new ArrayList();
        }
    }
    
    public static void setupBundleListFromDB(ArrayList<Bundle> BundleListFromDB) {
        bundleList = BundleListFromDB;
    }
    
    public static void addToBundleList(Bundle newBundle) {
        bundleList.add(newBundle);
    }
    
    public static Bundle findBundleOnID(int bundleID) {
        for (Bundle bundle : bundleList) {
            if (bundle.bundleID == bundleID) {
                return bundle;
            }
        }
        return null;
    }
    
    public static boolean deleteBundle(int bundleID) {
        return bundleList.remove(findBundleOnID(bundleID));
    }

    public void editBundle(String bundleName, String bundleDescription) {
        this.bundleName = bundleName;
        this.bundleDescription = bundleDescription;
    }
    
    public static boolean validateBundleInput(String bundleName, String bundleDescription, Integer bundleID) throws IllegalArgumentException {
        if (bundleName.isEmpty()) {
            throw new IllegalArgumentException("please fill out bundle-name field");
        }
        if (bundleDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out bundle-description field");
        }

        for (Bundle bundlesInList : bundleList) {
            if (bundleName.equals(bundlesInList.bundleName)) {
                if (bundleID != null) {
                    if (bundleID != bundlesInList.getBundleID()) {
                        throw new IllegalArgumentException("name already in use");
                    }
                } else {
                    throw new IllegalArgumentException("name already in use");
                }
            }
        }
        return true;
    }
    
    public static ArrayList<Bundle> getMatchingBundlesOnIDs(ArrayList<String> bundleChoices) {
        ArrayList<Bundle> result = new ArrayList();
        for (Bundle bundle : bundleList) {
            if(bundleChoices.contains(Integer.toString(bundle.getBundleID()))){
                result.add(bundle);
            }
        }
        return result;
    }

    public void setBundleProducts(ArrayList<Product> bundleProducts) {
        this.bundleProducts = bundleProducts;
    }

    public int getBundleID() {
        return bundleID;
    }

    public String getBundleName() {
        return bundleName;
    }

    public String getBundleDescription() {
        return bundleDescription;
    }

    public static ArrayList<Bundle> getBundleList() {
        return bundleList;
    }

    public ArrayList<Product> getBundleProducts() {
        return bundleProducts;
    }
}
