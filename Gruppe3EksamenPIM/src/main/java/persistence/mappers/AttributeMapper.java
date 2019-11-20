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
            ArrayList<Attribute> attributeList = new ArrayList();
            HashMap<Integer, String> value = new HashMap();
            String SQL = "SELECT * FROM Attributes";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int attribute_ID = rs.getInt("Attribute_ID");
                String attribute_Name = rs.getString("Attribute_Name");

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
            String SQL = "DELETE FROM Attribute WHERE Attribute_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
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
                    SQL += "(" + productID + ", " + productAttribute.getAttributeID() + ", '" + productAttribute.getAttributeValueForID(productID) + "')";

                }
                database.getConnection().prepareStatement(SQL).executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }
    }

}
