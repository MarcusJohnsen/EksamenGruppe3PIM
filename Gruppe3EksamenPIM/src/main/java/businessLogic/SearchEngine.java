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

    private TreeSet<Product> productList;
    private TreeSet<Category> categoryList;
    private TreeSet<Distributor> distributorList;
    private TreeSet<Bundle> bundleList;
    private TreeSet<Attribute> attributeList;

    public SearchEngine() {
        this.productList = new TreeSet();
        this.categoryList = new TreeSet();
        this.distributorList = new TreeSet();
        this.bundleList = new TreeSet();
        this.attributeList = new TreeSet();
    }

    public void setupSearchEngine(TreeSet<Product> productList, TreeSet<Category> categoryList, TreeSet<Distributor> distributorList, TreeSet<Bundle> bundleList, TreeSet<Attribute> attributeList) {
        this.productList = productList;
        this.categoryList = categoryList;
        this.distributorList = distributorList;
        this.bundleList = bundleList;
        this.attributeList = attributeList;
    }

    public TreeSet<Product> simpleProductSearch(String searchString) {
        searchString = searchString.toLowerCase();
        boolean singleResultFromList = false;
        return productSearch(searchString, productList, singleResultFromList);
    }

    public TreeSet<Bundle> bundleSearch(String searchString, TreeSet<Bundle> bundleList, boolean singleResultFromList) {
        TreeSet<Bundle> result = new TreeSet();

        for (Bundle bundle : bundleList) {
            String productName = bundle.getBundleName().toLowerCase();
            String productID = Integer.toString(bundle.getBundleID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(bundle);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    public TreeSet<Category> categorySearch(String searchString, TreeSet<Category> categoryList, boolean singleResultFromList) {
        TreeSet<Category> result = new TreeSet();

        for (Category category : categoryList) {
            String productName = category.getName().toLowerCase();
            String productID = Integer.toString(category.getCategoryID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(category);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    public TreeSet<Distributor> distributorSearch(String searchString, TreeSet<Distributor> distributorList, boolean singleResultFromList) {
        TreeSet<Distributor> result = new TreeSet();

        for (Distributor distributor : distributorList) {
            String productName = distributor.getDistributorName().toLowerCase();
            String productID = Integer.toString(distributor.getDistributorID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(distributor);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    public TreeSet<Product> productSearch(String searchString, TreeSet<Product> productList, boolean singleResultFromList) {
        TreeSet<Product> result = new TreeSet();

        for (Product product : productList) {
            String productName = product.getName().toLowerCase();
            String productID = Integer.toString(product.getProductID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(product);
                if (singleResultFromList) {
                    break;
                }
            }
        }
        return result;
    }

    public TreeSet<Object> advancedSearch(String searchKey, String searchOnObject, HashMap<String, String> filterValues) {
        searchKey = searchKey.toLowerCase();
        TreeSet<Object> result = new TreeSet();

        switch (searchOnObject) {
            case "Product":
                result = advancedProductSearch(searchKey, filterValues);
                break;
            case "Category":
                result = advancedCategorySearch(searchKey, filterValues);
                break;
            case "Distributor":
                result = advancedDistributorSearch(searchKey, filterValues);
                break;
            case "Bundle":
                advancedBundleSearch(searchKey, filterValues);
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
            if (categoryFilter.length() > 0) {
                TreeSet<Category> productCategoryList = product.getProductCategories();
                if (!categorySearch(categoryFilter, productCategoryList, singleResultFromList).isEmpty()) {
                    result.remove(product);
                    break;
                }
            }

            if (distributorFilter.length() > 0) {
                TreeSet<Distributor> productDistributorList = product.getProductDistributors();
                if (!distributorSearch(searchKey, productDistributorList, singleResultFromList).isEmpty()) {
                    result.remove(product);
                    break;
                }
            }

            if (bundleFilter.length() > 0) {
                TreeSet<Bundle> productBundleList = product.getProductBundle();
                if (!bundleSearch(searchKey, productBundleList, singleResultFromList).isEmpty()) {
                    result.remove(product);
                    break;
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
            if (productFilter.length() > 0) {
                TreeSet<Product> categoryProductList = category.getCategoryProducts();
                if (!productSearch(productFilter, categoryProductList, singleResultFromList).isEmpty()) {
                    result.remove(category);
                    break;
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
            if (productFilter.length() > 0) {
                TreeSet<Product> distributorProductList = distributor.getDistributorProducts();
                if (!productSearch(productFilter, distributorProductList, singleResultFromList).isEmpty()) {
                    result.remove(distributor);
                    break;
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
            if (productFilter.length() > 0) {
                TreeSet<Product> bundleProductList = new TreeSet(bundle.getBundleProducts().keySet());
                if (!productSearch(productFilter, bundleProductList, singleResultFromList).isEmpty()) {
                    result.remove(bundle);
                    break;
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
        return filter;
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

    public void setAttributeList(TreeSet<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
