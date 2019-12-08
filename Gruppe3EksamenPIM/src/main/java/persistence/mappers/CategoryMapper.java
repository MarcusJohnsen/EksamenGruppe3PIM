package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.SQLDatabase;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapper {

    private SQLDatabase database;

    public CategoryMapper(SQLDatabase database) {
        this.database = database;
    }

    /**
     * Creates new category object and stores it in the database. Get's the
     * CategoryID from the database, and returns the Category object with the
     * new ID
     *
     * @param categoryName String with max length of 255 characters
     * @param categoryDescription String with max length of 2550 characters
     *
     * @return the category object with an ID given from the database.
     * @throws IllegalArgumentException stating that category object could not
     * be inserted, due to a sql error with the database.
     */
   public Category addNewCategory(String categoryName, String categoryDescription, TreeSet<Attribute> attributeList) {
        try {
            database.setAutoCommit(false);
            String SQL = "INSERT INTO Categories (Category_Name, Category_Description) VALUES (?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoryName);
            ps.setString(2, categoryDescription);
            ps.executeUpdate();
            
            

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            if (!attributeList.isEmpty()) {
                SQL = "INSERT INTO Category_Attributes (Category_ID, Attribute_ID) VALUES ";
                boolean firstline = true;
                for (Attribute attribute : attributeList) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        SQL += ", ";
                    }
                    SQL += "(" + id + ", '" + attribute.getObjectID() + "')";
                }
                database.getConnection().prepareStatement(SQL).executeUpdate();
            }
            database.getConnection().commit();
            database.setAutoCommit(true);
            
            Category category = new Category(id, categoryName, categoryDescription, attributeList);
            return category;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Category cannot be inserted in the database");
           
        }
    }

  
   /**
    * Gets the attributeList in order to traverse it, and find the cross-values
    * for attributeID and categoryID from the database. Returns a list of category 
    * objects
    * 
    * @param attributeList ???
    * 
    * @return a list of category objects.
    * @throws IllegalArgumentException stating that the category list can't be 
    * returned due to a sql error with the database.
    */
    public TreeSet<Category> getCategories(TreeSet<Attribute> attributeList) {
        try {
            TreeSet<Category> categoryList = new TreeSet();
            HashMap<Integer, TreeSet<Attribute>> categoryAttributesMap = new HashMap();

            String SQL = "SELECT * FROM Category_Attributes";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int categoryID = rs.getInt("Category_ID");
                if (categoryAttributesMap.get(categoryID) == null) {
                    categoryAttributesMap.put(categoryID, new TreeSet());
                }
                int attributeID = rs.getInt("Attribute_ID");
                for (Attribute attribute : attributeList) {
                    if (attribute.getObjectID() == attributeID) {
                        categoryAttributesMap.get(categoryID).add(attribute);
                    }
                }
            }

            SQL = "SELECT * FROM Categories";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int category_ID = rs.getInt("Category_ID");
                String category_Name = rs.getString("Category_Name");
                String category_Description = rs.getString("Category_Description");

                TreeSet<Attribute> categoryAttributes = categoryAttributesMap.get(category_ID);
                Category category = new Category(category_ID, category_Name, category_Description, categoryAttributes);
                categoryList.add(category);
            }
            return categoryList;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get categories from Database");
        }
    }

    
    /**
     * Deletes category object with given ID from the database, starting with foreign keys
     * and ending with primary key.
     * Returns the total amount of rows affected in the database.
     * 
     * @param categoryID int unique, not null auto_increment.
     * 
     * @return the integer value of executed updates in the database.
     * @throws IllegalArgumentException stating that selected rows cant be deleted
     * due to a sql error in the database.
     * 
     */
    public int deleteCategory(int categoryID) {
        int rowsAffected = 0;
        try {
            database.setAutoCommit(false);

            String sqlDeleteCategoryAttributes = "DELETE FROM Category_Attributes WHERE Category_ID = ?";
            PreparedStatement psDeleteCategoryAttributes = database.getConnection().prepareStatement(sqlDeleteCategoryAttributes);
            psDeleteCategoryAttributes.setInt(1, categoryID);
            rowsAffected += psDeleteCategoryAttributes.executeUpdate();

            String sqlDeleteProductCategories = "DELETE FROM Product_Categories WHERE Category_ID = ?";
            PreparedStatement psDeleteProductCategories = database.getConnection().prepareStatement(sqlDeleteProductCategories);
            psDeleteProductCategories.setInt(1, categoryID);
            rowsAffected += psDeleteProductCategories.executeUpdate();

            String sqlDeleteCategory = "DELETE FROM Categories WHERE Category_ID = ?";
            PreparedStatement psDeleteCategory = database.getConnection().prepareStatement(sqlDeleteCategory);
            psDeleteCategory.setInt(1, categoryID);
            rowsAffected += psDeleteCategory.executeUpdate();

            database.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete selected category from DB");
        }
        database.setAutoCommit(true);

        return rowsAffected;
    }

    
    /**
     * Updating the database (categories table) with the given category values.
     * 
     * @param category object which is used in order to update the database
     * 
     * @return the integer value of affected updated-rows in the database.
     * @throws IllegalArgumentException stating that requested category-update cant be made
     * due to a sql error in the database.
     */
    public int editCategory(Category category) {
        try {
            //Update category in category table
            String SQL = "UPDATE Categories SET Category_Name = ?, Category_Description = ? WHERE Category_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, category.getObjectTitle());
            ps.setString(2, category.getObjectDescription());
            ps.setInt(3, category.getObjectID());
            int result = ps.executeUpdate();
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update category in database");
        }
    }

    
    
    /**
     * Updating the database (category_attributes table) with the given category values.
     * Uses categoryID variable to find and delete requested row(s) in order to fill the updated values
     * into the database.
     * 
     * @param category object which is used in order to update the database
     * 
     * @return the integer number of executed updates.
     * @throws IllegalArgumentException stating that requested update not possible due to 
     * error in the database 
     */
    public int editAttributeToCategories(Category category) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);
            int categoryID = category.getObjectID();
            String SQL = "DELETE FROM Category_Attributes WHERE category_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, categoryID);
            rowsAffected += ps.executeUpdate();

            if (!category.getCategoryAttributes().isEmpty()) {
                SQL = "INSERT INTO Category_Attributes (Category_ID, Attribute_ID) VALUES ";
                boolean firstline = true;
                for (Attribute attribute : category.getCategoryAttributes()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        SQL += ", ";
                    }
                    SQL += "(" + categoryID + ", '" + attribute.getObjectID() + "')";
                }
                rowsAffected += database.getConnection().prepareStatement(SQL).executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the attributes tied to categoryID " + category.getObjectID());
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }
}
