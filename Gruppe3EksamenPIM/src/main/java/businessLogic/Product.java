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

    public Product(int productID, String name, String description, String picturePath, ArrayList<String> distributors) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
    }
    
    private Product(String name, String description, String picturePath, ArrayList<String> distributors) {
        this.productID = productID;
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
        ProductList.add(product);
        return product;
    }

    public static void setupProductListFromDB() {
        ProductList = productMapper.getProducts();
    }

    static boolean deleteProductOnID(int productID) {
//        productMapper.deleteProduct(productID);
        return ProductList.remove(findProductOnID(productID));
    }

    public static Product findProductOnID(int productID) {
        for (Product product : ProductList) {
            if (product.productID == productID) {
                return product;
            }
        }
        return null;
    }

    public static void addImage(int productID, String picturePath) {
        findProductOnID(productID).picturePath = picturePath;
        productMapper.addImage(productID, picturePath);
    }
    
    public static void emptyProductList(){
        ProductList.clear();
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
