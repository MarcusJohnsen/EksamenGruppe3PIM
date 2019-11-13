package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;

/**
 *
 * @author Marcus
 */
public interface ProductMapperInterface {
    
    public ArrayList<Product> getProducts();
    
    public int addNewProduct(Product product);

    public void updatePicturePath(int productID, String picturePath);
    
    public ArrayList<String> getProductDistributors(int product_ID);

    public void deleteProduct(int productID);
    
    public void editProduct(int productID, String name, String description, ArrayList<String> distributors);
}