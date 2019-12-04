package businessLogic;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Andreas & Marcus
 */
public class SearchEngine {

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

    public TreeSet<Product> simpleSearch(String searchString) {
        TreeSet<Product> result = new TreeSet();
        searchString = searchString.toLowerCase();
        for (Product product : productList) {
            String productName = product.getName().toLowerCase();
            String productID = Integer.toString(product.getProductID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(product);
            }
        }
        return result;
    }

    public TreeSet<Object> advancedSearch(String searchKey, String searchOnObject, HashMap<String, String> filterValues) {
        searchKey = searchKey.toLowerCase();
        searchOnObject = searchOnObject.toLowerCase();

        TreeSet<Object> result = new TreeSet();
        switch (searchOnObject) {
            case "Product":
                break;
            case "Category":
                break;
            case "Distributor":
                break;
            case "Bundle":
                break;
            default:
                break;
        }
        return result;
    }

    private TreeSet<Object> advancedProductSearch(String searchKey, HashMap<String, String> filterValues) {
        searchKey = searchKey.toLowerCase();
        String categoryFilter = filterValues.get("categoryFilter");
        String distributorFilter = filterValues.get("distributorFiter");
        String bundleFilter = filterValues.get("bundleFilter");

        TreeSet<Object> result = new TreeSet(simpleSearch(searchKey));
        
        if(categoryFilter != null){
            for (Object object : result) {
                Product product = (Product) object;
                boolean matchCategoryFilter = false;
                for (Category category : product.getProductCategories()) {
                    String categoryName = category.getName().toLowerCase();
                    String categoryID = Integer.toString(category.getCategoryID());
                    if(categoryName.contains(searchKey) || categoryID.contains(searchKey)){
                        matchCategoryFilter = true;
                        break;
                    }
                }
                if(!matchCategoryFilter){
                    result.remove(object);
                }
            }
        }
        
        if(distributorFilter != null){
            for (Object object : result) {
                Product product = (Product) object;
                boolean matchDistributorFilter = false;
                for (Distributor distributor : product.getProductDistributors()) {
                    String distributorName = distributor.getDistributorName().toLowerCase();
                    String distributorID = Integer.toString(distributor.getDistributorID());
                    if(distributorName.contains(searchKey) || distributorID.contains(searchKey)){
                        matchDistributorFilter = true;
                        break;
                    }
                }
                if(!matchDistributorFilter){
                    result.remove(object);
                }
            }
        }
        
        if(bundleFilter != null){
            for (Object object : result) {
                Product product = (Product) object;
                boolean matchBundleFilter = false;
                for (Bundle bundle : product.getProductBundle()) {
                    String bundleName = bundle.getBundleName().toLowerCase();
                    String bundleID = Integer.toString(bundle.getBundleID());
                    if(bundleName.contains(searchKey) || bundleID.contains(searchKey)){
                        matchBundleFilter = true;
                        break;
                    }
                }
                if(!matchBundleFilter){
                    result.remove(object);
                }
            }
        }
        return result;
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