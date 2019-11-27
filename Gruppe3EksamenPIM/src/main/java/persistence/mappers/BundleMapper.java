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

    public Bundle addNewBundle(String bundleName, String bundleDescription, HashMap<Product, Integer> productListForBundle) {
        try {
            database.setAutoCommit(false);
            String SQL = "INSERT INTO Bundles (Bundle_Name, Bundle_Description) VALUES (?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bundleName);
            ps.setString(2, bundleDescription);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int bundleID = rs.getInt(1);

            if (!productListForBundle.isEmpty()) {
                boolean firstline = true;
                for (Product product : productListForBundle.keySet()) {
                    int productAmount = productListForBundle.get(product);
                    if (firstline) {
                        SQL = "INSERT INTO Product_Bundles (Bundle_ID, Product_ID, Product_Amount) VALUES ";
                        firstline = false;
                    } else {
                        SQL += ", ";
                    }
                    SQL += "(" + bundleID + ", " + product.getProductID() + ", " + productAmount + ")";
                }
                database.getConnection().prepareStatement(SQL).executeUpdate();
            }

            Bundle bundle = new Bundle(bundleID, bundleName, bundleDescription, productListForBundle);

            database.getConnection().commit();
            database.setAutoCommit(true);
            return bundle;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Bundle cannot be inserted in the database");
        }
    }

    public ArrayList<Bundle> getBundle(ArrayList<Product> productList) {
        try {
            ArrayList<Bundle> bundleList = new ArrayList();
            HashMap<Integer, HashMap<Product, Integer>> bundleProductMap = new HashMap();

            String SQL = "SELECT * FROM Product_Bundles";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int bundleID = rs.getInt("Bundle_ID");
                if (bundleProductMap.get(bundleID) == null) {
                    bundleProductMap.put(bundleID, new HashMap<Product, Integer>());
                }
                int productID = rs.getInt("Product_ID");
                int productAmount = rs.getInt("Product_amount");
                for (Product product : productList) {
                    if (product.getProductID() == productID) {
                        bundleProductMap.get(bundleID).put(product, productAmount);
                    }
                }
            }

            SQL = "SELECT * FROM Bundles";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int bundle_ID = rs.getInt("Bundle_ID");
                String bundle_Name = rs.getString("Bundle_Name");
                String bundle_Description = rs.getString("Bundle_Description");

                HashMap<Product, Integer> bundleProducts = bundleProductMap.get(bundle_ID);
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

        try {
            int rowsAffected = 0;
            database.setAutoCommit(false);

            String sqlDeleteProductBundles = "DELETE FROM product_bundles WHERE Bundle_ID = ?";
            PreparedStatement psDeleteProductBundles = database.getConnection().prepareStatement(sqlDeleteProductBundles);
            psDeleteProductBundles.setInt(1, bundleID);
            rowsAffected += psDeleteProductBundles.executeUpdate();

            String sqlDeleteBundle = "DELETE FROM Bundles WHERE Bundle_ID = ?";
            PreparedStatement psDeleteBundle = database.getConnection().prepareStatement(sqlDeleteBundle);
            psDeleteBundle.setInt(1, bundleID);
            rowsAffected += psDeleteBundle.executeUpdate();

            database.getConnection().commit();
            database.setAutoCommit(true);

            return rowsAffected;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete selected bundle from DB");
        }

    }

    public int editBundle(Bundle bundle) {
        try {
            int rowsAffected = 0;
            database.setAutoCommit(false);

            String sqlUpdateBundle = "UPDATE Bundles SET Bundle_Name = ?, Bundle_Description = ? WHERE Bundle_ID = ?";
            PreparedStatement psUpdateBundle = database.getConnection().prepareStatement(sqlUpdateBundle);
            psUpdateBundle.setString(1, bundle.getBundleName());
            psUpdateBundle.setString(2, bundle.getBundleDescription());
            psUpdateBundle.setInt(3, bundle.getBundleID());
            rowsAffected += psUpdateBundle.executeUpdate();

            String sqlDeleteProductBundles = "DELETE FROM Product_Bundles WHERE bundle_ID = ?";
            PreparedStatement psDeleteProductBundles = database.getConnection().prepareStatement(sqlDeleteProductBundles);
            psDeleteProductBundles.setInt(1, bundle.getBundleID());
            rowsAffected += psDeleteProductBundles.executeUpdate();

            if (!bundle.getBundleProducts().isEmpty()) {
                String sqlInsertProductBundles = "";
                boolean firstline = true;
                for (Product product : bundle.getBundleProducts().keySet()) {
                    int productAmount = bundle.getBundleProducts().get(product);
                    if (firstline) {
                        sqlInsertProductBundles += "INSERT INTO Product_Bundles (Bundle_ID, Product_ID, Product_Amount) VALUES ";
                        firstline = false;
                    } else {
                        sqlInsertProductBundles += ", ";
                    }
                    sqlInsertProductBundles += "(" + bundle.getBundleID() + ", " + product.getProductID() + ", " + productAmount + ")";
                }
                rowsAffected += database.getConnection().prepareStatement(sqlInsertProductBundles).executeUpdate();
            }

            database.getConnection().commit();
            database.setAutoCommit(true);

            return rowsAffected;

        } catch (SQLException ex) {
            Logger.getLogger(BundleMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update bundle in database");
        }
    }

}
