package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.SQLDatabase;

/**
 *
 * @author Andreas
 */
public class AttributeMapper {

    private SQLDatabase database;

    public AttributeMapper(SQLDatabase database) {
        this.database = database;
    }

    public Attribute addNewAttribute(String attributeName) {
        try {
            String SQL = "INSERT INTO Attributes (Attribute_Name) VALUES (?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            HashMap<Integer, String> value = new HashMap();
            ps.setString(1, attributeName);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            Attribute attribute = new Attribute(id, attributeName, value);
            return attribute;

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Attribute cannot be inserted in the database");
        }
    }

    public TreeSet<Attribute> getAttributes() {
        try {
            String SQL = "SELECT * FROM Product_Attributes";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer, HashMap<Integer, String>> productAttributeValues = new HashMap();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                int attributeID = rs.getInt("Attribute_ID");
                String productAttributeValue = rs.getString("Attribute_Info");
                if (productAttributeValues.get(attributeID) != null) {
                    productAttributeValues.get(attributeID).put(productID, productAttributeValue);
                } else {
                    HashMap<Integer, String> value = new HashMap();
                    value.put(productID, productAttributeValue);
                    productAttributeValues.put(attributeID, value);
                }
            }

            TreeSet<Attribute> attributeList = new TreeSet();
            SQL = "SELECT * FROM Attributes";
            ps = database.getConnection().prepareStatement(SQL);

            rs = ps.executeQuery();
            while (rs.next()) {
                int attribute_ID = rs.getInt("Attribute_ID");
                String attribute_Name = rs.getString("Attribute_Name");
                HashMap<Integer, String> value = productAttributeValues.get(attribute_ID);
                if (value == null) {
                    value = new HashMap();
                }

                Attribute attribute = new Attribute(attribute_ID, attribute_Name, value);
                attributeList.add(attribute);
            }
            return attributeList;

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get attributes from Database");
        }
    }

    public int deleteAttribute(int attributeID) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            String sqlDeleteCategoryAttributes = "DELETE FROM Category_Attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteCategoryAttributes = database.getConnection().prepareStatement(sqlDeleteCategoryAttributes);
            psDeleteCategoryAttributes.setInt(1, attributeID);
            rowsAffected += psDeleteCategoryAttributes.executeUpdate();

            String sqlDeleteProductAttributes = "DELETE FROM Product_Attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteProductAttributes = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            psDeleteProductAttributes.setInt(1, attributeID);
            rowsAffected += psDeleteProductAttributes.executeUpdate();

            String sqlDeleteAttribute = "DELETE FROM Attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteAttribute = database.getConnection().prepareStatement(sqlDeleteAttribute);
            psDeleteAttribute.setInt(1, attributeID);
            rowsAffected += psDeleteAttribute.executeUpdate();

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete selected attribute from DB");
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int editAttribute(Attribute attribute) {
        try {
            String SQL = "UPDATE Attributes SET Attribute_Name = ? WHERE Attribute_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, attribute.getAttributeName());
            ps.setInt(2, attribute.getAttributeID());
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update selected attribute from DB");
        }
    }

    public int updateProductAttributeSelections(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);
            int productID = product.getProductID();
            String sqlDeleteProductAttributes = "DELETE FROM Product_Attributes WHERE Product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            ps.setInt(1, productID);
            rowsAffected += ps.executeUpdate();

            if (!product.getProductAttributes().isEmpty()) {
                String sqlInsertProductAttributes = "INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES ";
                boolean firstline = true;
                for (Attribute productAttribute : product.getProductAttributes()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductAttributes += ", ";
                    }
                    String attributeValue = productAttribute.getAttributeValueForID(productID);
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    sqlInsertProductAttributes += "(" + productID + ", " + productAttribute.getAttributeID() + ", '" + attributeValue + "')";

                }
                rowsAffected += database.getConnection().prepareStatement(sqlInsertProductAttributes).executeUpdate();
            }

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int updateProductAttributeValues(Product product) {
        int rowsAffacted = 0;

        try {
            database.setAutoCommit(false);
            int productID = product.getProductID();
            for (Attribute productAttribute : product.getProductAttributes()) {
                String SQL = "UPDATE Product_Attributes SET Attribute_Info = ? WHERE Product_ID = ? AND Attribute_ID = ?";
                PreparedStatement ps = database.getConnection().prepareStatement(SQL);
                ps.setString(1, productAttribute.getAttributeValueForID(productID));
                ps.setInt(2, productID);
                ps.setInt(3, productAttribute.getAttributeID());
                rowsAffacted += ps.executeUpdate();
            }
            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }

        database.setAutoCommit(true);
        return rowsAffacted;
    }

    /**
     * Method to update values for multiple product Attributes tied to a specific category. Used for bulk edit function.
     * 
     * @param productIDs List of productIDs that match products that should have their attributes affected in the bulk edit
     * @param newAttributeValues Each attribute changed with the new value. Key is the attributeID for each new String value.
     * @param categoryID ID matching category these attributes should be changed on, in case of multiple categories using the same attributes
     * @return int count of rows affected in database from sql updates.
     */
    public int bulkEditOnProductIDs(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues) {
        try {
            int rowsAffected = 0;
            database.setAutoCommit(false);
            
            if(productIDs.isEmpty()){
                throw new IllegalArgumentException("No products selected to be updated");
            }

            String sqlUpdateCoreStatement = "UPDATE Product_Attributes JOIN Product_Categories ON Product_Attributes.Product_ID = Product_Categories.Product_ID "
                    + "SET Attribute_Info = ? WHERE Product_Attributes.Attribute_ID = ? AND ";
            String sqlProductIDCondition = "";
            boolean firstLine = true;
            for (Integer productID : productIDs) {
                if(firstLine){
                    firstLine = false;
                    sqlProductIDCondition += "( Product_Attributes.Product_ID = " + productID;
                } else {
                    sqlProductIDCondition += " OR Product_Attributes.Product_ID = " + productID;
                }
            }
            sqlProductIDCondition += " )";
            String sqlFinal = sqlUpdateCoreStatement + sqlProductIDCondition;
            
            for (Integer attributeID : newAttributeValues.keySet()) {
                PreparedStatement ps = database.getConnection().prepareStatement(sqlFinal);
                ps.setString(1, newAttributeValues.get(attributeID));
                ps.setInt(2, attributeID);
                rowsAffected += ps.executeUpdate();
            }
            
            database.getConnection().commit();
            database.setAutoCommit(true);

            return rowsAffected;
        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update the attribute values of the bulk edit into the database");
        }
    }

}
