package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcus
 */
public interface ProductMapperInterface {
    
    public ArrayList<Product> getProducts();
    
    public int addNewProduct(Product product);

    public void addImage(int productID, String picturePath);
    
    public ArrayList<String> getProductDistributors(int product_ID);

    public void deleteProduct(int productID);
}