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
    
    
    /**
     * 
     * @param category Traverses through the productList in order to find certain category object, which 
     * is deleted once it is found.
     */
    
    public static void deleteCategoryFromProducts(Category category) {
        for (Product product : productList) {
            product.productCategories.remove(category);
        }
    }

    
    /**
     * Traverses the productCategories-List and adds all the unique categoryAttributes to new HashSet.
     */
    
    private void createAttributesFromCategories() {
        Set<Attribute> attributeSet = new HashSet();
        for (Category productCategory : productCategories) {
            attributeSet.addAll(productCategory.getCategoryAttributes());
        }
        this.productAttributes = new ArrayList(attributeSet);
    }
    
    
    /**
     * 
     * @param products Traverses the products-List and calls the createAttributesFromCategories() method
     * in order to add the productAttributes to the product object.
     * 
     */
    
    public static void createAttributesFromCategories(ArrayList<Product> products){
        for (Product product : products) {
            product.createAttributesFromCategories();
        }
    }
    
    /**
     * 
     * @param categoryID The returned product object from the called method findProductsOnCategoryID(categoryID) 
     * is used to call the method createAttributesFromCategories() in order to add/update the productAttributes 
     * to the product object. 
     * @return the updated product object.
     */
    
    public static ArrayList<Product> updateCategoryAttributes(int categoryID) {
        ArrayList<Product> result = findProductsOnCategoryID(categoryID);
        for (Product productsNeedingUpdatedAttribute : result) {
            productsNeedingUpdatedAttribute.createAttributesFromCategories();
        }
        return result;
    }
    
    
    /**
     * 
     * @param categoryID Traverses the productList with given categoryID parameter.
     * If the parameter categoryId matches the categoryId for the  product, the product is
     * added to the result-List.
     * @return The result-List
     */

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

    /**
     * @param productListFromDB
     * Gets the list of product objects from the DataBase and stores them in a list. 
     */
    
    public static void setupProductListFromDB(ArrayList<Product> productListFromDB) {
        productList = productListFromDB;
    }

    /**
     * @param product
     * product object is added to the productList.
     */
    
    public static void addToProductList(Product product) {
        productList.add(product);
    }

    /**
     * 
     * @param productID Is used to call the method findProductOnID(productID). The returned product object
     * is then deleted.
     * @return The new productList.
     */
    
    public static boolean deleteProductOnID(int productID) {
        return productList.remove(findProductOnID(productID));
    }

    
    /**
     * 
     * @param productID Traversing through the productList and using the productID param to find product object.
     * @return The found product object.
     */
    
    public static Product findProductOnID(int productID) {
        for (Product product : productList) {
            if (product.productID == productID) {
                return product;
            }
        }
        return null;
    }

    
    /**
     * 
     * @param name Is edited.
     * @param description Is edited.
     * @param distributors Is edited.
     */
    
    public void editProduct(String name, String description, ArrayList<String> distributors) {
        this.name = name;
        this.description = description;
        this.distributors = distributors;
    }

    
    /**
     * @param productAttributeValues 
     * Mangler
     * 
     */
    
    public void updateProductValues(HashMap<Integer, String> productAttributeValues) {
        for (Attribute productAttribute : this.productAttributes) {
            int attributeID = productAttribute.getAttributeID();
            String newValue = productAttributeValues.get(attributeID);
            productAttribute.insertValueIntoAttribute(newValue, this.productID);
        }
    }

    
    /**
     * Validating productInput by checking for empty fields.
     * @param productName - Must not be empty.
     * @param productDescription - Must not be empty.
     * @param productDistributors - Must not be empty.
     * @return Boolean true if name, description and distributors dont have empty field-values.
     * @throws IllegalArgumentException if returned boolean is false.
     */
    
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

    
    /**
     * @param productCategories
     * Calls the createAttributesFromCategories() method
     * in order to edit the productCategories.
     */
    
    public void editProductCategories(ArrayList<Category> productCategories) {
        this.productCategories = productCategories;
        createAttributesFromCategories();
    }

    
    /**
     * 
     * @return productID
     */
    
    
    public int getProductID() {
        return productID;
    }

    
    /**
     * 
     * @return name
     */
    
    public String getName() {
        return name;
    }

    
    /**
     * 
     * @return description
     */
    
    public String getDescription() {
        return description;
    }

    
    /**
     * 
     * @return picturePath
     */
    
    public String getPicturePath() {
        return picturePath;
    }

    
    /**
     * 
     * @return distributors
     */
    
    public ArrayList<String> getDistributors() {
        return distributors;
    }

    
    /**
     * 
     * @return productList
     */
    
    public static ArrayList<Product> getProductList() {
        return productList;
    }

    
    /**
     * 
     * @param picturePath Sets new value for picturePath
     */
    
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    
    /**
     * 
     * @return productCategories
     */
    
    public ArrayList<Category> getProductCategories() {
        return productCategories;
    }

    
    /**
     * 
     * @return productAttributes
     */
    
    public ArrayList<Attribute> getProductAttributes() {
        return productAttributes;
    }
    
    public ArrayList<Bundle> getProductBundle() {
        return productBundle;
    }

}
