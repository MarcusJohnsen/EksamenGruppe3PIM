package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcus
 */
public interface ProductMapperInterface {
    
    public ArrayList<HashMap<String, String>> getProducts();
    
    public int addNewProduct(Product product);
}