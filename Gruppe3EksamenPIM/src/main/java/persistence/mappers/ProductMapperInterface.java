package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcus
 */
public interface ProductMapperInterface {
    
    public ArrayList<HashMap<String, Object>> getProducts();
    
    public int addNewProduct(Product product);

    public void addImage(int productID, String picturePath);
    
    public ArrayList<String> getProductDistributors(int product_ID);
}