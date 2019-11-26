package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Creates new category object and stores it in the database. Get's the
     * CategoryID from the database, and returns the Category object with the
     * new ID
     *
     * @param categoryName String with length no longer than 255 charactors
     * @param categoryDescription String with length no longer than 2550 charactors
     *
     * @return the category object with an ID given from the database.
     * @throws IllegalArgumentException stating that category object could not
     * be inserted, due to a sql error with the database.
     */
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

            ArrayList<Attribute> categoryAttributes = new ArrayList();
            Category category = new Category(id, categoryName, categoryDescription, categoryAttributes);
            return category;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Category cannot be inserted in the database");
        }
    }

  
   /**
    * Gets the categoryList from the Database 
    * @param attributeList
    * @return 
    */
    public ArrayList<Category> getCategories(ArrayList<Attribute> attributeList) {
        try {
            ArrayList<Category> categoryList = new ArrayList();
            HashMap<Integer, ArrayList<Attribute>> categoryAttributesMap = new HashMap();

            String SQL = "SELECT * FROM category_attributes";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int categoryID = rs.getInt("Category_ID");
                if (categoryAttributesMap.get(categoryID) == null) {
                    categoryAttributesMap.put(categoryID, new ArrayList());
                }
                int attributeID = rs.getInt("Attribute_ID");
                for (Attribute attribute : attributeList) {
                    if (attribute.getAttributeID() == attributeID) {
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

                ArrayList<Attribute> categoryAttributes = categoryAttributesMap.get(category_ID);
                Category category = new Category(category_ID, category_Name, category_Description, categoryAttributes);
                categoryList.add(category);
            }
            return categoryList;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get categories from Database");
        }
    }

    public int deleteCategory(int categoryID) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            String sqlDeleteCategoryAttributes = "DELETE FROM category_attributes WHERE Category_ID = ?";
            PreparedStatement psDeleteCategoryAttributes = database.getConnection().prepareStatement(sqlDeleteCategoryAttributes);
            psDeleteCategoryAttributes.setInt(1, categoryID);
            rowsAffected += psDeleteCategoryAttributes.executeUpdate();

            String sqlDeleteProductCategories = "DELETE FROM product_categories WHERE Category_ID = ?";
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

    public int editAttributeToCategories(Category category) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);
            int categoryID = category.getCategoryID();
            String SQL = "DELETE FROM category_attributes WHERE category_ID = ?";
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
                    SQL += "(" + categoryID + ", '" + attribute.getAttributeID() + "')";
                }
                rowsAffected += database.getConnection().prepareStatement(SQL).executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the attributes tied to categoryID " + category.getCategoryID());
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }
}
