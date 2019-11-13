/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapper implements CategoryMapperInterface {

    @Override
    public int addNewCategory(String categoryName, String cateoryDescription) {

        int newCategoryID = 1;

        String sql = "INSERT INTO Categories (Catagory_Name, Catagory_Description) VALUES ('" + categoryName + "', '" + cateoryDescription + "')";
        DB.executeUpdate(sql);

        sql = "SELECT Category_ID FROM Categories WHERE Catagory_Name = '" + categoryName + "' AND Catagory_Description = '" + cateoryDescription + "'";
        ResultSet rs = DB.executeQuery(sql);

        try {
            if (rs.next()) {
                newCategoryID = rs.getInt("Category_ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newCategoryID;
    }

}
