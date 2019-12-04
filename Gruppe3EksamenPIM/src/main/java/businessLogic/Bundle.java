package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 *
 * @author Andreas
 */
public class Bundle implements Comparable<Bundle> {

    private int bundleID;
    private String bundleName;
    private String bundleDescription;
    private HashMap<Product, Integer> bundleProducts;

    private static TreeSet<Bundle> bundleList = new TreeSet();

    public Bundle(int bundleID, String bundleName, String bundleDescription, HashMap<Product, Integer> bundleProducts) {
        this.bundleID = bundleID;
        this.bundleName = bundleName;
        this.bundleDescription = bundleDescription;
        if (bundleProducts != null) {
            this.bundleProducts = bundleProducts;
        } else {
            this.bundleProducts = new HashMap();
        }
    }

    public void addBundleRelationToProducts() {
        for (Product product : bundleProducts.keySet()) {
            product.addBundleToProduct(this);
        }
    }

    public void removeProductFromBundle(Product product) {
        this.bundleProducts.remove(product);
    }

    public static void setupBundleListFromDB(TreeSet<Bundle> BundleListFromDB) {
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
        Bundle bundle = findBundleOnID(bundleID);
        for (Product product : bundle.bundleProducts.keySet()) {
            product.removeBundleFromProduct(bundle);
        }
        return bundleList.remove(bundle);
    }

    public void editBundle(String bundleName, String bundleDescription, HashMap<Product, Integer> productListForBundle) {
        this.bundleName = bundleName;
        this.bundleDescription = bundleDescription;
        this.bundleProducts = productListForBundle;
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

    public static TreeSet<Bundle> getMatchingBundlesOnIDs(ArrayList<String> bundleChoices) {
        TreeSet<Bundle> result = new TreeSet();
        for (Bundle bundle : bundleList) {
            if (bundleChoices.contains(Integer.toString(bundle.getBundleID()))) {
                result.add(bundle);
            }
        }
        return result;
    }

    public static List<Pair<Bundle, Integer>> topTenBundles() {
        List<Pair<Bundle, Integer>> bundleProductCounts = new ArrayList();

        for (Bundle bundle : bundleList) {
            bundleProductCounts.add(new Pair(bundle, bundle.bundleProducts.size()));
        }

        Collections.sort(bundleProductCounts, new Comparator<Pair<Bundle, Integer>>() {
            @Override
            public int compare(final Pair<Bundle, Integer> o1, final Pair<Bundle, Integer> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else if (o1.getValue() < o2.getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int subListEnd = 10;
        if (bundleProductCounts.size() < 10) {
            subListEnd = bundleProductCounts.size();
        }

        List<Pair<Bundle, Integer>> result = bundleProductCounts.subList(0, subListEnd);
        return result;
    }

    public void addProductToBundle(Product product, int amount) {
        this.bundleProducts.put(product, amount);
    }

    public void setBundleProducts(HashMap<Product, Integer> bundleProducts) {
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

    public static TreeSet<Bundle> getBundleList() {
        return bundleList;
    }

    public HashMap<Product, Integer> getBundleProducts() {
        return bundleProducts;
    }

    public static int getTotalBundleCount() {
        return bundleList.size();
    }

    @Override
    public int compareTo(Bundle otherBundle) {

        int thisID = this.bundleID;
        int otherID = otherBundle.bundleID;
        return thisID - otherID;

    }
}
