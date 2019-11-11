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
    private ArrayList<String> distributers;

    private Product(String name, String description, String picturePath, ArrayList<String> distributers) {
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributers = distributers;
    }

    public static void setProductMapper(ProductMapperInterface newMapper) {
        productMapper = newMapper;
    }

    public static Product createNewProduct(String name, String description, String picturePath, ArrayList<String> distributers) {
        Product product = new Product(name, description, picturePath, distributers);
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

    public ArrayList<String> getDistributers() {
        return distributers;
    }
}
