/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Category;
import businessLogic.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapper {

    private DB database;

    public CategoryMapper(DB database) {
        this.database = database;
    }

    public Category addNewCategory(String categoryName, String categoryDescription) {
        try {
            String SQL = "INSERT INTO Categories (Category_Name, Category_Description) VALUES (?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoryName);
            ps.setString(2, categoryDescription);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            Category category = new Category(id, categoryName, categoryDescription);
            return category;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Category cannot be inserted in the database");
        }
    }

    public ArrayList<Category> getCategories() {
        try {
            ArrayList<Category> categoryList = new ArrayList();

            String SQL = "SELECT * FROM Categories";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int category_ID = rs.getInt("Category_ID");
                String category_Name = rs.getString("Category_Name");
                String category_Description = rs.getString("Category_Description");

                Category category = new Category(category_ID, category_Name, category_Description);
                categoryList.add(category);
            }
            return categoryList;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get categories from Database");
        }
    }

    public int deleteCategory(int categoryID) {
        try {
            String SQL = "DELETE FROM Categories WHERE Category_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, categoryID);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't delete selected category from DB");
        }
    }
    
    public int editCategory(Category category) {
        try {
            //Update category in category table
            String SQL = "UPDATE Categories SET Category_Name = ?, Category_Description = ? WHERE Category_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryID());
            int result = ps.executeUpdate();
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update category in database");
        }
    }
}