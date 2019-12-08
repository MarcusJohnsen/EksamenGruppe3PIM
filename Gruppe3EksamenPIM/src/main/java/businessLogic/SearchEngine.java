package businessLogic;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Andreas & Marcus
 */
public class SearchEngine {

    private static final String BUNDLE_FILTER_KEY = "bundleFilter";
    private static final String CATEGORY_FILTER_KEY = "categoryFilter";
    private static final String DISTRIBUTOR_FILTER_KEY = "distributorFilter";
    private static final String PRODUCT_FILTER_KEY = "productFilter";
    private static final String BUNDLE_TYPE = "Bundle";
    private static final String CATEGORY_TYPE = "Category";
    private static final String DISTRIBUTOR_TYPE = "Distributor";
    private static final String PRODUCT_TYPE = "Product";

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
        TreeSet<Object> result = new TreeSet();

        switch (searchOnObject) {
            case PRODUCT_TYPE:
                result = advancedProductSearch(searchKey, filterValues);
                break;
            case CATEGORY_TYPE:
                result = advancedCategorySearch(searchKey, filterValues);
                break;
            case DISTRIBUTOR_TYPE:
                result = advancedDistributorSearch(searchKey, filterValues);
                break;
            case BUNDLE_TYPE:
                result = advancedBundleSearch(searchKey, filterValues);
                break;
            default:
                throw new IllegalArgumentException("Cannot search on object type specificed: " + searchOnObject);
        }

        return result;
    }

    private TreeSet<Object> advancedProductSearch(String searchKey, HashMap<String, String> filterValues) {
        String categoryFilter = checkedFilter(filterValues, CATEGORY_FILTER_KEY);
        String distributorFilter = checkedFilter(filterValues, DISTRIBUTOR_FILTER_KEY);
        String bundleFilter = checkedFilter(filterValues, BUNDLE_FILTER_KEY);

        // Find all categories matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Product> fullSearchResult = new TreeSet(productSearch(searchKey, productList, singleResultFromList));
        TreeSet<Object> result = new TreeSet(fullSearchResult);

        // Searching with the filters to see if any products need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Product product : fullSearchResult) {
            boolean objectRemoved = false;
            if (categoryFilter.length() > 0 && !objectRemoved) {
                TreeSet<Category> productCategoryList = product.getProductCategories();
                if (categorySearch(categoryFilter, productCategoryList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(product);
                }
            }

            if (distributorFilter.length() > 0 && !objectRemoved) {
                TreeSet<Distributor> productDistributorList = product.getProductDistributors();
                if (distributorSearch(distributorFilter, productDistributorList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(product);
                }
            }

            if (bundleFilter.length() > 0 && !objectRemoved) {
                TreeSet<Bundle> productBundleList = product.getProductBundle();
                if (bundleSearch(bundleFilter, productBundleList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(product);
                }
            }
        }
        return result;
    }

    private TreeSet<Object> advancedCategorySearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, PRODUCT_FILTER_KEY);

        // Find all categories matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Category> fullSearchResult = new TreeSet(categorySearch(searchKey, categoryList, singleResultFromList));
        TreeSet<Object> result = new TreeSet(fullSearchResult);

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Category category : fullSearchResult) {
            boolean objectRemoved = false;
            if (productFilter.length() > 0 && !objectRemoved) {
                TreeSet<Product> categoryProductList = category.getCategoryProducts();
                if (productSearch(productFilter, categoryProductList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(category);
                }
            }
        }
        return result;
    }

    private TreeSet<Object> advancedDistributorSearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, PRODUCT_FILTER_KEY);

        // Find all distributors matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Distributor> fullSearchResult = new TreeSet(distributorSearch(searchKey, distributorList, singleResultFromList));
        TreeSet<Object> result = new TreeSet(fullSearchResult);

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Distributor distributor : fullSearchResult) {
            boolean objectRemoved = false;
            if (productFilter.length() > 0 && !objectRemoved) {
                TreeSet<Product> distributorProductList = distributor.getDistributorProducts();
                if (productSearch(productFilter, distributorProductList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(distributor);
                }
            }
        }
        return result;
    }

    private TreeSet<Object> advancedBundleSearch(String searchKey, HashMap<String, String> filterValues) {
        String productFilter = checkedFilter(filterValues, PRODUCT_FILTER_KEY);

        // Find all distributors matching searchKey
        boolean singleResultFromList = false;
        TreeSet<Bundle> fullSearchResult = new TreeSet(bundleSearch(searchKey, bundleList, singleResultFromList));
        TreeSet<Object> result = new TreeSet(fullSearchResult);

        // Searching with the filters to see if any categories need to be filtered from result. If any of the filters are empty, that filter can be ignored.
        singleResultFromList = true;
        for (Bundle bundle : fullSearchResult) {
            boolean objectRemoved = false;
            if (productFilter.length() > 0 && !objectRemoved) {
                TreeSet<Product> bundleProductList = new TreeSet(bundle.getBundleProducts().keySet());
                if (productSearch(productFilter, bundleProductList, singleResultFromList).isEmpty()) {
                    objectRemoved = result.remove(bundle);
                }
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
     * @param bundleFilter stored in Map under static variable BUNDLE_FILTER_KEY
     * @param categoryFilter stored in Map under static variable CATEGORY_FILTER_KEY
     * @param distributorFilter stored in Map under static variable DISTRIBUTOR_FILTER_KEY
     * @param productFilter stored in Map under static variable PRODUCT_FILTER_KEY
     * @return HashMap with String keys and values, usable for the searchEngine filters
     */
    public static HashMap<String, String> makeFilterMap(String bundleFilter, String categoryFilter, String distributorFilter, String productFilter){
        HashMap<String, String> filterMap = new HashMap();
        filterMap.put(BUNDLE_FILTER_KEY, bundleFilter);
        filterMap.put(CATEGORY_FILTER_KEY, categoryFilter);
        filterMap.put(DISTRIBUTOR_FILTER_KEY, distributorFilter);
        filterMap.put(PRODUCT_FILTER_KEY, productFilter);
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

    public static String getBUNDLE_FILTER_KEY() {
        return BUNDLE_FILTER_KEY;
    }

    public static String getCATEGORY_FILTER_KEY() {
        return CATEGORY_FILTER_KEY;
    }

    public static String getDISTRIBUTOR_FILTER_KEY() {
        return DISTRIBUTOR_FILTER_KEY;
    }

    public static String getPRODUCT_FILTER_KEY() {
        return PRODUCT_FILTER_KEY;
    }

    public static String getBUNDLE_TYPE() {
        return BUNDLE_TYPE;
    }

    public static String getCATEGORY_TYPE() {
        return CATEGORY_TYPE;
    }

    public static String getDISTRIBUTOR_TYPE() {
        return DISTRIBUTOR_TYPE;
    }

    public static String getPRODUCT_TYPE() {
        return PRODUCT_TYPE;
    }

}