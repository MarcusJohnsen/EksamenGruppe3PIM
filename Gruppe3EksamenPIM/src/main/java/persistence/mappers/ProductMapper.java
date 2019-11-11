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
                map.put("picturePath", rs.getString("picturePath"));
                products.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public int addNewProduct(Product product) {

        int newProductID = selectMax("Product_ID", "Product");
        //adding the basic product information

        try {
            
            // Insert the new Product into DB
            String sql = "INSERT INTO Product (Product_Name, Product_Description, picturePath) VALUES ('" + product.getName() + "', '"
                    + product.getDescription() + "', '" + product.getPicturePath() + "')";

            DB.getConnection().prepareStatement(sql).executeUpdate();

            // Find the new Product's ID
            sql = "SELECT Product_ID FROM Product, WHERE Product_Name = " + product.getName() + " AND Product_Description = "
                    + product.getDescription() + " AND picturePath = " + product.getPicturePath() + " AND Product_ID > " + newProductID;

            ResultSet rs = DB.getConnection().prepareStatement(sql).executeQuery();
            if (rs.next()) {
                newProductID = rs.getInt("Product_ID");
            }
            
            for (String distributor : product.getDistributors()) {
                sql = "";
                DB.getConnection().prepareStatement(sql).executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newProductID;
    }

    private int selectMax(String search_Column, String search_Table) {
        int maxInt = 1;
        String sql = "SELECT MAX(" + search_Column + ") as Max FROM " + search_Table;
        try {
            ResultSet rs = DB.getConnection().prepareStatement(sql).executeQuery();
            if (rs.next()) {
                maxInt = rs.getInt("Max");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxInt;
    }
}
