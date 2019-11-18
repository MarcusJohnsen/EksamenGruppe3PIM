package persistence.mappers;

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
 * @author Marcus
 */
public class ProductMapper {

    DB database;

    public ProductMapper(DB database) {
        this.database = database;
    }

    public ArrayList<Product> getProducts() {
        try {
            ArrayList<Product> productList = new ArrayList();
            String SQL = "SELECT * FROM Product";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int product_ID = rs.getInt("Product_ID");
                String name = rs.getString("Product_Name");
                String description = rs.getString("Product_Description");
                String picturePath = rs.getString("picturePath");
                ArrayList<String> distributors = getProductDistributors(product_ID);
                Product product = new Product(product_ID, name, description, picturePath, distributors);
                productList.add(product);
            }
            return productList;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get products from database");
        }

    }

    public Product addNewProduct(String productName, String productDescription, String productPicturePath, ArrayList<String> productDistributors) {
        try {
            String SQL = "INSERT INTO Product (Product_Name, Product_Description, picturePath) VALUES (?, ?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            ps.setString(3, productPicturePath);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int newProductID = rs.getInt(1);

            addProductDistributors(productDistributors, newProductID);

            Product newProduct = new Product(newProductID, productName, productDescription, productPicturePath, productDistributors);
            return newProduct;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't add product to database");
        }

    }

    public int updatePicturePath(int productID, String picturePath) {
        try {
            String SQL = "UPDATE Product SET picturePath = ? WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, picturePath);
            ps.setInt(2, productID);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update product's picturePath in database");
        }
    }

    public int deleteProduct(int productID) {
        //Delete all distributers for product in Product_distributers table
        deleteProductDistributors(productID);

        try {
            String SQL = "DELETE FROM Product WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, productID);
            return ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't delete product from database");
        }

    }

    public int editProduct(Product product) {
        try {
            //Update product in product table
            String SQL = "UPDATE Product SET Product_Name = ?, Product_Description = ? WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getProductID());
            int result = ps.executeUpdate();

            //Delete all old distributers for product in Product_distributers table
            deleteProductDistributors(product.getProductID());

            //Insert all new distributers for product in Product_distributers table
            addProductDistributors(product.getDistributors(), product.getProductID());
            
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update product in database");
        }

    }

    public ArrayList<String> getProductDistributors(int product_ID) {
        try {
            ArrayList<String> productDistributors = new ArrayList();
            String SQL = "SELECT Product_Distributor_Name FROM Product_Distributor WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, product_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productDistributors.add(rs.getString("Product_Distributor_Name"));
            }
            return productDistributors;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get distributors for productID " + product_ID);
        }

    }

    public int addProductDistributors(ArrayList<String> productDistributors, int productID) {
        try {
            String SQL = "INSERT INTO Product_Distributor (Product_ID, Product_Distributor_Name) VALUES ";
            boolean firstline = true;
            for (String distributor : productDistributors) {
                if (firstline) {
                    firstline = false;
                } else {
                    SQL += ", ";
                }
                SQL += "(" + productID + ", '" + distributor + "')";
            }
            return database.getConnection().prepareStatement(SQL).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't add distributers to productID " + productID);
        }
    }

    public int deleteProductDistributors(int productID) {
        try {
            String SQL = "DELETE FROM Product_Distributor WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setInt(1, productID);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't delete distributors for productID " + productID);
        }
    }

}
