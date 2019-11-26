package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Product {

    private int productID;
    private String name;
    private String description;
    private String picturePath;
    private ArrayList<String> distributors;
    private ArrayList<Category> productCategories;
    private ArrayList<Attribute> productAttributes;
    private ArrayList<Bundle> productBundle;

    private static ArrayList<Product> productList = new ArrayList();

    public Product(int productID, String name, String description, String picturePath, ArrayList<String> distributors, ArrayList<Category> productCategories) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
        if (productCategories != null) {
            this.productCategories = productCategories;
            createAttributesFromCategories();
        } else {
            this.productAttributes = new ArrayList();
            this.productCategories = new ArrayList();
        }

    }
    
    public static void deleteCategoryFromProducts(Category category) {
        for (Product product : productList) {
            product.productCategories.remove(category);
        }
    }

    private void createAttributesFromCategories() {
        Set<Attribute> attributeSet = new HashSet();
        for (Category productCategory : productCategories) {
            attributeSet.addAll(productCategory.getCategoryAttributes());
        }
        this.productAttributes = new ArrayList(attributeSet);
    }
    
    public static void createAttributesFromCategories(ArrayList<Product> products){
        for (Product product : products) {
            product.createAttributesFromCategories();
        }
    }

    public static ArrayList<Product> updateCategoryAttributes(int categoryID) {
        ArrayList<Product> result = findProductsOnCategoryID(categoryID);
        for (Product productsNeedingUpdatedAttribute : result) {
            productsNeedingUpdatedAttribute.createAttributesFromCategories();
        }
        return result;
    }

    public static ArrayList<Product> findProductsOnCategoryID(int categoryID) {
        ArrayList<Product> result = new ArrayList();
        for (Product product : productList) {
            for (Category productCategory : product.getProductCategories()) {
                if (productCategory.getCategoryID() == categoryID) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }
    public static void deleteBundleFromProducts(Bundle bundle) {
        for (Product product : productList) {
            product.productCategories.remove(bundle);
        }
    }
    
    public static ArrayList<Product> findProductsOnBundleID(int bundleID) {
        ArrayList<Product> result = new ArrayList();
        for (Product product : productList) {
            for (Bundle productBundle : product.getProductBundle()) {
                if (productBundle.getBundleID() == bundleID) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    public static void setupProductListFromDB(ArrayList<Product> productListFromDB) {
        productList = productListFromDB;
    }

    public static void addToProductList(Product product) {
        productList.add(product);
    }

    public static boolean deleteProductOnID(int productID) {
        return productList.remove(findProductOnID(productID));
    }

    public static Product findProductOnID(int productID) {
        for (Product product : productList) {
            if (product.productID == productID) {
                return product;
            }
        }
        return null;
    }

    public void editProduct(String name, String description, ArrayList<String> distributors) {
        this.name = name;
        this.description = description;
        this.distributors = distributors;
    }

    public void updateProductValues(HashMap<Integer, String> productAttributeValues) {
        for (Attribute productAttribute : this.productAttributes) {
            int attributeID = productAttribute.getAttributeID();
            String newValue = productAttributeValues.get(attributeID);
            productAttribute.insertValueIntoAttribute(newValue, this.productID);
        }
    }

    public static boolean validateProductInput(String productName, String productDescription, ArrayList<String> productDistributors) throws IllegalArgumentException {
        //Remove all empty fields from distributors
        productDistributors.removeAll(Arrays.asList("", null));

        if (productName.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (productDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }
        if (productDistributors.isEmpty()) {
            throw new IllegalArgumentException("please fill out at least one distributor field");
        }

        return true;
    }

    public void editProductCategories(ArrayList<Category> productCategories) {
        this.productCategories = productCategories;
        createAttributesFromCategories();
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public ArrayList<String> getDistributors() {
        return distributors;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public ArrayList<Category> getProductCategories() {
        return productCategories;
    }

    public ArrayList<Attribute> getProductAttributes() {
        return productAttributes;
    }
    
    public ArrayList<Bundle> getProductBundle() {
        return productBundle;
    }

}
