package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;

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
    ArrayList<Category> productCategories;

    private static ArrayList<Product> productList = new ArrayList();

    public Product(int productID, String name, String description, String picturePath, ArrayList<String> distributors, ArrayList<Category> productCategories) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
        if (productCategories != null) {
            this.productCategories = productCategories;
        } else {
            this.productCategories = new ArrayList();
        }

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

    public void setProductCategories(ArrayList<Category> productCategories) {
        this.productCategories = productCategories;
    }
    
}
