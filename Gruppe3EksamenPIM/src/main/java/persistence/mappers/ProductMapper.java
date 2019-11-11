package persistence.mappers;

import businessLogic.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 *
 * @author Marcus
 */
public class ProductMapper implements ProductMapperInterface {

    @Override
    public ArrayList<HashMap<String, String>> getProducts() {
        //getting all the products from the database
        ArrayList<HashMap<String, String>> products = new ArrayList();

        String sql = "SELECT * FROM PIM_Database.Product";

        try {
            ResultSet rs = DB.getConnection().prepareStatement(sql).executeQuery();
            while (rs.next()) {
                HashMap<String, String> map = new HashMap();
                map.put("Product_ID", rs.getString("Product_ID"));
                map.put("Product_Name", rs.getString("Product_Name"));
                map.put("Product_Description", rs.getString("Product_Description"));
                products.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public int addNewProduct(Product product) {
        //adding the basic product information
        String sql = "INSERT INTO Product (Product_Name, Product_Description) VALUES ('" + product.getName() + "', '"
                + product.getDescription() + "')";
        
        try {
            DB.getConnection().prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        //get the new Product_ID number
        int highestProductIDNumber = 1;
        String sql2 = "SELECT MAX(Product_ID) as Product_ID FROM Product";
        try {
            ResultSet rs = DB.getConnection().prepareStatement(sql2).executeQuery();
            while (rs.next()) {
                highestProductIDNumber = rs.getInt("Product_ID") + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return highestProductIDNumber;
    }
}