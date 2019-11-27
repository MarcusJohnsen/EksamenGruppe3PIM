/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Bundle;
import businessLogic.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;
import java.util.HashMap;

/**
 *
 * @author Andreas
 */
public class BundleMapper {
    private DB database;

    public BundleMapper(DB database) {
        this.database = database;
    }
    
    public Bundle addNewBundle(String bundleName, String bundleDescription) {
        try {
            String SQL = "INSERT INTO Bundles (Bundle_Name, Bundle_Description) VALUES (?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bundleName);
            ps.setString(2, bundleDescription);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            ArrayList<Product> bundleProduct = new ArrayList();
            Bundle bundle = new Bundle(id, bundleName, bundleDescription, bundleProduct);
            return bundle;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Bundle cannot be inserted in the database");
        }
    }
    
    public ArrayList<Bundle> getBundle(ArrayList<Product> productList) {
        try {
            ArrayList<Bundle> bundleList = new ArrayList();
            HashMap<Integer, ArrayList<Product>> bundleProductMap = new HashMap();

            String SQL = "SELECT * FROM Product_Bundles";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int bundleID = rs.getInt("Bundle_ID");
                if (bundleProductMap.get(bundleID) == null) {
                    bundleProductMap.put(bundleID, new ArrayList());
                }
                int productID = rs.getInt("Product_ID");
                for (Product product : productList) {
                    if (product.getProductID() == productID) {
                        bundleProductMap.get(bundleID).add(product);
                    }
                }
            }

            SQL = "SELECT * FROM Bundles";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int bundle_ID = rs.getInt("Bundle_ID");
                String bundle_Name = rs.getString("Bundle_Name");
                String bundle_Description = rs.getString("Bundle_Description");

                ArrayList<Product> bundleProducts = bundleProductMap.get(bundle_ID);
                Bundle bundle = new Bundle(bundle_ID, bundle_Name, bundle_Description, bundleProducts);
                bundleList.add(bundle);
            }
            return bundleList;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get bundles from Database");
        }
    }
    
    public int deleteBundle(int bundleID) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

//            String sqlDeleteBundleProducts = "DELETE FROM bundle_products WHERE Bundle_ID = ?";
//            PreparedStatement psDeleteBundleProducts = database.getConnection().prepareStatement(sqlDeleteBundleProducts);
//            psDeleteBundleProducts.setInt(1, bundleID);
//            rowsAffected += psDeleteBundleProducts.executeUpdate();
            
            String sqlDeleteProductBundles = "DELETE FROM product_bundles WHERE Bundle_ID = ?";
            PreparedStatement psDeleteProductBundles = database.getConnection().prepareStatement(sqlDeleteProductBundles);
            psDeleteProductBundles.setInt(1, bundleID);
            rowsAffected += psDeleteProductBundles.executeUpdate();

            String sqlDeleteBundle = "DELETE FROM Bundles WHERE Bundle_ID = ?";
            PreparedStatement psDeleteBundle = database.getConnection().prepareStatement(sqlDeleteBundle);
            psDeleteBundle.setInt(1, bundleID);
            rowsAffected += psDeleteBundle.executeUpdate();

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete selected bundle from DB");
        }
        database.setAutoCommit(true);

        return rowsAffected;
    }
    
    public int editBundle(Bundle bundle) {
        try {
            //Update bundle in bundle table
            String SQL = "UPDATE Bundles SET Bundle_Name = ?, Bundle_Description = ? WHERE Bundle_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, bundle.getBundleName());
            ps.setString(2, bundle.getBundleDescription());
            ps.setInt(3, bundle.getBundleID());
            int result = ps.executeUpdate();
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update bundle in database");
        }
    }
    
    public int editAttributeToCategories(Bundle bundle) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);
            int bundleID = bundle.getBundleID();
            String SQL = "DELETE FROM bundle_products WHERE bunlde_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, bundleID);
            rowsAffected += ps.executeUpdate();

            if (!bundle.getBundleProducts().isEmpty()) {
                SQL = "INSERT INTO Bundle_Products (Bundle_ID, Product_ID) VALUES ";
                boolean firstline = true;
                for (Product product : bundle.getBundleProducts()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        SQL += ", ";
                    }
                    SQL += "(" + bundleID + ", '" + product.getProductID() + "')";
                }
                rowsAffected += database.getConnection().prepareStatement(SQL).executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the attributes tied to categoryID " + bundle.getBundleID());
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }

}
