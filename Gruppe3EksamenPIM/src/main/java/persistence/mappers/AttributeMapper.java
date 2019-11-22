/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Product;
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
 * @author Andreas
 */
public class AttributeMapper {

    private DB database;

    public AttributeMapper(DB database) {
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

    public ArrayList<Attribute> getAttributes() {
        try {
            String SQL = "SELECT * FROM product_attributes";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer, HashMap<Integer, String>> productAttributeValues = new HashMap();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                int attributeID = rs.getInt("Attribute_ID");
                String productAttributeValue = rs.getString("Attribute_Info");
                if(productAttributeValues.get(attributeID) != null){
                    productAttributeValues.get(attributeID).put(productID, productAttributeValue);
                } else {
                    HashMap<Integer, String> value = new HashMap();
                    value.put(productID, productAttributeValue);
                    productAttributeValues.put(attributeID, value);
                }
            }
            
            ArrayList<Attribute> attributeList = new ArrayList();
            SQL = "SELECT * FROM Attributes";
            ps = database.getConnection().prepareStatement(SQL);

            rs = ps.executeQuery();
            while (rs.next()) {
                int attribute_ID = rs.getInt("Attribute_ID");
                String attribute_Name = rs.getString("Attribute_Name");
                HashMap<Integer, String> value = productAttributeValues.get(attribute_ID);
                if(value == null){
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
        try {
            String SQL = "DELETE FROM category_attributes WHERE Attribute_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, attributeID);
            ps.executeUpdate();
            
            SQL = "DELETE FROM product_attributes WHERE Attribute_ID = ?";
            ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, attributeID);
            ps.executeUpdate();
            
            SQL = "DELETE FROM Attributes WHERE Attribute_ID = ?";
            ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, attributeID);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't delete selected attribute from DB");
        }
    }

    public void updateProductAttributeSelections(Product product) {
        try {
            int productID = product.getProductID();
            String SQL = "DELETE FROM Product_Attributes WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, productID);
            ps.executeUpdate();

            if (!product.getProductAttributes().isEmpty()) {
                SQL = "INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES ";
                boolean firstline = true;
                for (Attribute productAttribute : product.getProductAttributes()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        SQL += ", ";
                    }
                    String attributeValue = productAttribute.getAttributeValueForID(productID);
                    if(attributeValue == null){
                        attributeValue = "";
                    }
                    SQL += "(" + productID + ", " + productAttribute.getAttributeID() + ", '" + attributeValue + "')";

                }
                database.getConnection().prepareStatement(SQL).executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }
    }

    public void updateProductAttributeValues(Product product) {
        try {
            int productID = product.getProductID();
            for (Attribute productAttribute : product.getProductAttributes()) {
                String SQL = "UPDATE product_attributes SET Attribute_Info = ? WHERE Product_ID = ? AND Attribute_ID = ?";
                PreparedStatement ps = database.getConnection().prepareStatement(SQL);
                ps.setString(1, productAttribute.getAttributeValueForID(productID));
                ps.setInt(2, productID);
                ps.setInt(3, productAttribute.getAttributeID());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }
    }

}
