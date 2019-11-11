package businessLogic;

import java.util.ArrayList;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Product {
    
    //private static ProductMapperInterface productMapper;
    private static ArrayList<Product> productList = new ArrayList();
    
    private int productID;
    private String name;
    private String description;

    private Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
//    public void setProductMapper(ProductMapperInterface newMapper){
//        productMapper = newMapper;
//    }
    
    public static Product createNewProduct(String name, String description){
        Product product = new Product(name, description);
        // 
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

    public static ArrayList<Product> getProductList() {
        return productList;
    }
}