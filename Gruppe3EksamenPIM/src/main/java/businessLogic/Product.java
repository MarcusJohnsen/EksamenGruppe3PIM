package businessLogic;

import java.util.ArrayList;
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
}