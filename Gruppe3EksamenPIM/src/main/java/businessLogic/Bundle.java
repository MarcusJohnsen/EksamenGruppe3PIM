package businessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import persistence.Json.Exclude;

/**
 *
 * @author Andreas
 */
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

    public static Bundle findBundleOnID(int objectID) {
        for (Bundle bundle : bundleList) {
            if (bundle.objectID == objectID) {
                return bundle;
            }
        }
        return null;
    }

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
