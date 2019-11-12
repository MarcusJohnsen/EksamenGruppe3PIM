package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import persistence.mappers.ProductMapperInterface;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Product {

    private static ProductMapperInterface productMapper;

    private int productID;
    private String name;
    private String description;
    private String picturePath;
    private ArrayList<String> distributors;
    
    private static ArrayList<Product> ProductList = new ArrayList();

    private Product(String name, String description, String picturePath, ArrayList<String> distributors) {
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
    }

    public static void setProductMapper(ProductMapperInterface newMapper) {
        productMapper = newMapper;
    }

    public static Product createNewProduct(String name, String description, String picturePath, ArrayList<String> distributors) {
        Product product = new Product(name, description, picturePath, distributors);
        int newProductID = productMapper.addNewProduct(product);
        product.productID = newProductID;
        return product;
    }
    
    public static void setupProductsFromDB() {
        ProductList.clear();
        for (HashMap<String, Object> productsMap : productMapper.getProducts()) {
            int product_ID = (int) productsMap.get("product_ID");
            String product_Name = (String) productsMap.get("product_Name");
            String product_Description = (String) productsMap.get("product_Description");
            String picturePath = (String) productsMap.get("picturePath");
            ArrayList<String> distributors = (ArrayList<String>) productsMap.get("distributor");
            Product product = new Product(product_Name, product_Description, picturePath, distributors);
            product.productID = product_ID;
            ProductList.add(product);
        }
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
        return ProductList;
    }
}