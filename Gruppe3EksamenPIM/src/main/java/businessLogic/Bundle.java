package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import persistence.Json.Exclude;

public class Bundle extends PIMObject {

    @Exclude
    private HashMap<Product, Integer> bundleProducts;

    private static TreeSet<Bundle> bundleList = new TreeSet();

    public Bundle(int bundleID, String bundleName, String bundleDescription, HashMap<Product, Integer> bundleProducts) {
        super(bundleID, bundleName, bundleDescription);
        if (bundleProducts != null) {
            this.bundleProducts = bundleProducts;
        } else {
            this.bundleProducts = new HashMap();
        }
        addBundleRelationToProducts();
    }

    /**
     * Establishes a double link between the bundle and each product relation.
     */
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

    /**
     * Get bundle with matching ID to the given search parameter.
     *
     * //TODO: move method to SearchEngine
     *
     * @param objectID ID of desired Bundle
     * @return Bundle with matching ID, or null
     */
    public static Bundle findBundleOnID(int objectID) {
        for (Bundle bundle : bundleList) {
            if (bundle.objectID == objectID) {
                return bundle;
            }
        }
        return null;
    }

    /**
     * Delete the bundle from the static bundlelist, after removing all relations to bundle-products.
     *
     * @param objectID ID for Bundle to be deleted
     * @return boolean true if the bundle was removed from the static bundlelist.
     */
    public static boolean deleteBundle(int objectID) {
        Bundle bundle = findBundleOnID(objectID);
        for (Product product : bundle.bundleProducts.keySet()) {
            product.removeBundleFromProduct(bundle);
        }
        return bundleList.remove(bundle);
    }

    public void editBundle(String bundleTitle, String bundleDescription, HashMap<Product, Integer> productListForBundle) {
        this.objectTitle = bundleTitle;
        this.objectDescription = bundleDescription;
        this.bundleProducts = productListForBundle;
    }

    /**
     * Check if user input is valid for businessLogic parameters for the bundle.
     *
     * @param bundleName user input for bundle title
     * @param bundleDescription user input for bundle description.
     * @param bundleID ID if the validation is for edit on exising bundle, or null if validation is for new bundle.
     * @return boolean true if user input is valid.
     * @throws IllegalArgumentException if user input is not valid, with message explaining what businesslogic was violated.
     */
    public static boolean validateBundleInput(String bundleName, String bundleDescription, Integer bundleID) throws IllegalArgumentException {
        if (bundleName.isEmpty()) {
            throw new IllegalArgumentException("please fill out bundle-name field");
        }
        if (bundleDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out bundle-description field");
        }

        for (Bundle bundlesInList : bundleList) {
            if (bundleName.equals(bundlesInList.objectTitle)) {
                if (bundleID != null) {
                    if (bundleID != bundlesInList.objectID) {
                        throw new IllegalArgumentException("name already in use");
                    }
                } else {
                    throw new IllegalArgumentException("name already in use");
                }
            }
        }
        return true;
    }

    /**
     * Get a sorted list of up to ten bundles, based and sorted on amount of bundle relations to products.
     *
     * @return sorted top ten bundles list.
     */
    public static List<Bundle> topTenBundles() {
        List<Bundle> bundleProductCounts = new ArrayList(bundleList);

        Collections.sort(bundleProductCounts, new Comparator<Bundle>() {
            @Override
            public int compare(final Bundle o1, final Bundle o2) {
                int o1Size = o1.getBundleProducts().size();
                int o2Size = o2.getBundleProducts().size();
                if (o1Size > o2Size) {
                    return -1;
                } else if (o1Size < o2Size) {
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

        List<Bundle> result = bundleProductCounts.subList(0, subListEnd);
        return result;
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

}
