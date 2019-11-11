package persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

public class ProductMapper implements ProductMapperInterface {

    @Override
    public ArrayList<HashMap<String, String>> getProducts() {
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
}