/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Category;
import businessLogic.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapper implements CategoryMapperInterface {

    @Override
    public int addNewCategory(String categoryName, String categoryDescription) {

        int newCategoryID = 1;

        String sql = "INSERT INTO Categories (Category_Name, Category_Description) VALUES ('" + categoryName + "', '" + categoryDescription + "')";
        DB.executeUpdate(sql);

        sql = "SELECT Category_ID FROM Categories WHERE Category_Name = '" + categoryName + "' AND Category_Description = '" + categoryDescription + "'";
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
    
    @Override
    public ArrayList<Category> getCategories() {
        //getting all the products from the database
        ArrayList<Category> categoryList = new ArrayList();

        String sql = "SELECT * FROM PIM_Database.Categories";

        try {
            ResultSet rs = DB.getConnection().prepareStatement(sql).executeQuery();
            while (rs.next()) {
                int category_ID = rs.getInt("Category_ID");
                String category_Name = rs.getString("Category_Name");
                String category_Description = rs.getString("Category_Description");
                Category category = new Category (category_ID, category_Name, category_Description);
                categoryList.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }
}