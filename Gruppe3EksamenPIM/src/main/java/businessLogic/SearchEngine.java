package businessLogic;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Andreas & Marcus
 */
public class SearchEngine {

    private static final String FILTER_KEY_BUNDLE = "bundleFilter";
    private static final String FILTER_KEY_CATEGORY = "categoryFilter";
    private static final String FILTER_KEY_DISTRIBUTOR = "distributorFilter";
    private static final String FILTER_KEY_PRODUCT = "productFilter";
    private static final String TYPE_BUNDLE = "Bundle";
    private static final String TYPE_CATEGORY = "Category";
    private static final String TYPE_DISTRIBUTOR = "Distributor";
    private static final String TYPE_PRODUCT = "Product";

    private TreeSet<Product> productList;
    private TreeSet<Category> categoryList;
    private TreeSet<Distributor> distributorList;
    private TreeSet<Bundle> bundleList;

    public SearchEngine() {
        this.productList = new TreeSet();
        this.categoryList = new TreeSet();
        this.distributorList = new TreeSet();
        this.bundleList = new TreeSet();
    }

    public void setupSearchEngine(TreeSet<Product> productList, TreeSet<Category> categoryList, TreeSet<Distributor> distributorList, TreeSet<Bundle> bundleList) {
        this.productList = productList;
        this.categoryList = categoryList;
        this.distributorList = distributorList;
        this.bundleList = bundleList;
    }

    private TreeSet<Bundle> bundleSearch(String searchString, TreeSet<Bundle> bundleList, boolean singleResultFromList) {
        TreeSet<Bundle> result = new TreeSet();

        for (Bundle bundle : bundleList) {
            String productName = bundle.objectTitle.toLowerCase();
            String productID = Integer.toString(bundle.objectID);
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(bundle);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    private TreeSet<Category> categorySearch(String searchString, TreeSet<Category> categoryList, boolean singleResultFromList) {
        TreeSet<Category> result = new TreeSet();

        for (Category category : categoryList) {
            String productName = category.objectTitle.toLowerCase();
            String productID = Integer.toString(category.objectID);
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(category);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    private TreeSet<Distributor> distributorSearch(String searchString, TreeSet<Distributor> distributorList, boolean singleResultFromList) {
        TreeSet<Distributor> result = new TreeSet();

        for (Distributor distributor : distributorList) {
            String productName = distributor.objectTitle.toLowerCase();
            String productID = Integer.toString(distributor.objectID);
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(distributor);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    private TreeSet<Product> productSearch(String searchString, TreeSet<Product> productList, boolean singleResultFromList) {
        TreeSet<Product> result = new TreeSet();

        for (Product product : productList) {
            String productName = product.objectTitle.toLowerCase();
            String productID = Integer.toString(product.objectID);
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(product);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    public TreeSet<Product> simpleProductSearch(String searchString) {
        searchString = searchString.toLowerCase();
        boolean singleResultFromList = false;
        return productSearch(searchString, productList, singleResultFromList);
    }

    public TreeSet<Object> advancedSearch(String searchKey, String searchOnObject, HashMap<String, String> filterValues) {
        searchKey = searchKey.toLowerCase();
        TreeSet<Object> result;

        switch (searchOnObject) {
            case TYPE_PRODUCT:
                result = advancedProductSearch(searchKey, filterValues);
                break;
            case TYPE_CATEGORY:
                result = advancedCategorySearch(searchKey, filterValues);
                break;
            case TYPE_DISTRIBUTOR:
                result = advancedDistributorSearch(searchKey, filterValues);
                break;
            case TYPE_BUNDLE:
                result = advancedBundleSearch(searchKey, filterValues);
                break;
            default:
                throw new IllegalArgumentException("Cannot search on object type specificed: " + searchOnObject);
        }

        return result;
    }

    private TreeSet<Object> advancedProductSearch(String searchKey, HashMap<String, String> filterValues) {
        String categoryFilter = checkedFilter(filterValues, FILTER_KEY_CATEGORY);
        String distributorFilter = checkedFilter(filterValues, FILTER_KEY_DISTRIBUTOR);
        String bundleFilter = checkedFilter(filterValues, FILTER_KEY_BUNDLE);

        // Find all categories matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Product> fullSearchResult = new TreeSet(productSearch(searchKey, productList, singleResultFromList));
        TreeSet<Object> result = new TreeSet();

        // Searching with the filters to see if any products need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Product product : fullSearchResult) {
            boolean objectAllowed = true;
            if (categoryFilter.length() > 0 && objectAllowed) {
                TreeSet<Category> productCategoryList = product.getProductCategories();
                if (categorySearch(categoryFilter, productCategoryList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }

            if (distributorFilter.length() > 0 && objectAllowed) {
                TreeSet<Distributor> productDistributorList = product.getProductDistributors();
                if (distributorSearch(distributorFilter, productDistributorList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }

            if (bundleFilter.length() > 0 && objectAllowed) {
                TreeSet<Bundle> productBundleList = product.getProductBundle();
                if (bundleSearch(bundleFilter, productBundleList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }
            if (objectAllowed) {
                result.add(product);
            }
        }
        return result;
    }

    private TreeSet<Object> advancedCategorySearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, FILTER_KEY_PRODUCT);

        // Find all categories matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Category> fullSearchResult = new TreeSet(categorySearch(searchKey, categoryList, singleResultFromList));
        TreeSet<Object> result = new TreeSet();

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Category category : fullSearchResult) {
            boolean objectAllowed = true;
            if (productFilter.length() > 0 && objectAllowed) {
                TreeSet<Product> categoryProductList = category.getCategoryProducts();
                if (productSearch(productFilter, categoryProductList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }
            if (objectAllowed) {
                result.add(category);
            }
        }
        return result;
    }

    private TreeSet<Object> advancedDistributorSearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, FILTER_KEY_PRODUCT);

        // Find all distributors matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Distributor> fullSearchResult = new TreeSet(distributorSearch(searchKey, distributorList, singleResultFromList));
        TreeSet<Object> result = new TreeSet();

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Distributor distributor : fullSearchResult) {
            boolean objectAllowed = true;
            if (productFilter.length() > 0 && objectAllowed) {
                TreeSet<Product> distributorProductList = distributor.getDistributorProducts();
                if (productSearch(productFilter, distributorProductList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }
            if (objectAllowed) {
                result.add(distributor);
            }
        }
        return result;
    }

    private TreeSet<Object> advancedBundleSearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, FILTER_KEY_PRODUCT);

        // Find all distributors matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Bundle> fullSearchResult = new TreeSet(bundleSearch(searchKey, bundleList, singleResultFromList));
        TreeSet<Object> result = new TreeSet();

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Bundle bundle : fullSearchResult) {
            boolean objectAllowed = true;
            if (productFilter.length() > 0 && objectAllowed) {
                TreeSet<Product> bundleProductList = new TreeSet(bundle.getBundleProducts().keySet());
                if (productSearch(productFilter, bundleProductList, singleResultFromList).isEmpty()) {
                    objectAllowed = false;
                }
            }
            if (objectAllowed) {
                result.add(bundle);
            }
        }
        return result;
    }

    private String checkedFilter(HashMap<String, String> filterValues, String filterKey) {
        String filter = filterValues.get(filterKey);
        if (filter == null) {
            filter = "";
        } else if (filter.replaceAll(" ", "").length() == 0) {
            filter = "";
        }
        return filter.toLowerCase();
    }

    /**
     * Makes a map with filter values for 4 strings in order: <br>
     * bundle, category, distributor & product.
     *
     * @param bundleFilter stored in Map under static variable FILTER_KEY_BUNDLE
     * @param categoryFilter stored in Map under static variable FILTER_KEY_CATEGORY
     * @param distributorFilter stored in Map under static variable FILTER_KEY_DISTRIBUTOR
     * @param productFilter stored in Map under static variable FILTER_KEY_PRODUCT
     * @return HashMap with String keys and values, usable for the searchEngine filters
     */
    public static HashMap<String, String> makeFilterMap(String bundleFilter, String categoryFilter, String distributorFilter, String productFilter) {
        HashMap<String, String> filterMap = new HashMap();
        filterMap.put(FILTER_KEY_BUNDLE, bundleFilter);
        filterMap.put(FILTER_KEY_CATEGORY, categoryFilter);
        filterMap.put(FILTER_KEY_DISTRIBUTOR, distributorFilter);
        filterMap.put(FILTER_KEY_PRODUCT, productFilter);
        return filterMap;
    }

    public void setProductList(TreeSet<Product> productList) {
        this.productList = productList;
    }

    public void setCategoryList(TreeSet<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setDistributorList(TreeSet<Distributor> distributorList) {
        this.distributorList = distributorList;
    }

    public void setBundleList(TreeSet<Bundle> bundleList) {
        this.bundleList = bundleList;
    }

    public static String getFILTER_KEY_BUNDLE() {
        return FILTER_KEY_BUNDLE;
    }

    public static String getFILTER_KEY_CATEGORY() {
        return FILTER_KEY_CATEGORY;
    }

    public static String getFILTER_KEY_DISTRIBUTOR() {
        return FILTER_KEY_DISTRIBUTOR;
    }

    public static String getFILTER_KEY_PRODUCT() {
        return FILTER_KEY_PRODUCT;
    }

    public static String getTYPE_BUNDLE() {
        return TYPE_BUNDLE;
    }

    public static String getTYPE_CATEGORY() {
        return TYPE_CATEGORY;
    }

    public static String getTYPE_DISTRIBUTOR() {
        return TYPE_DISTRIBUTOR;
    }

    public static String getTYPE_PRODUCT() {
        return TYPE_PRODUCT;
    }

}
