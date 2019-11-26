package persistence.mappers;

import businessLogic.Category;
import businessLogic.Distributor;
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
 * @author Marcus
 */
public class ProductMapper {

    private DB database;

    public ProductMapper(DB database) {
        this.database = database;
    }

    public ArrayList<Product> getProducts(ArrayList<Category> categoryList, ArrayList<Distributor> distributorList) {
        try {
            ArrayList<Product> productList = new ArrayList();
            HashMap<Integer, ArrayList<Category>> productCategoriesMap = new HashMap();
            HashMap<Integer, ArrayList<Distributor>> productDistributorsMap = new HashMap();
            
            String SQL = "SELECT * FROM Product_Distributor";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                if (productDistributorsMap.get(productID) == null) {
                    productDistributorsMap.put(productID, new ArrayList());
                } 
                int distributorID = rs.getInt("Distributor_ID");
                for (Distributor distributor : distributorList)
                    if (distributor.getDistributorID() == distributorID) {
                        productDistributorsMap.get(productID).add(distributor);
                }
            }

            SQL = "SELECT * FROM Product_Categories";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                if (productCategoriesMap.get(productID) == null) {
                    productCategoriesMap.put(productID, new ArrayList());
                }
                int categoryID = rs.getInt("Category_ID");
                for (Category category : categoryList) {
                    if (category.getCategoryID() == categoryID) {
                        productCategoriesMap.get(productID).add(category);
                    }
                }
            }

            SQL = "SELECT * FROM Product";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int product_ID = rs.getInt("Product_ID");
                String name = rs.getString("Product_Name");
                String description = rs.getString("Product_Description");
                String picturePath = rs.getString("picturePath");
                ArrayList<Distributor> productDistributors = productDistributorsMap.get(product_ID);
                ArrayList<Category> productCategories = productCategoriesMap.get(product_ID);
                Product product = new Product(product_ID, name, description, picturePath, productDistributors, productCategories);
                productList.add(product);
            }
            return productList;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get products from database");
        }
    }

    public Product addNewProduct(String productName, String productDescription, String productPicturePath) {
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

            ArrayList<Category> productCategories = new ArrayList();
            ArrayList<Distributor> productDistributors = new ArrayList();
            Product newProduct = new Product(newProductID, productName, productDescription, productPicturePath, productDistributors, productCategories);
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
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            //Delete all distributers for product in Product_Distributers table
            String sqlDeleteProductDistributors = "DELETE FROM Product_Distributor WHERE product_ID = ?";
            PreparedStatement psDeleteProductDistributors = database.getConnection().prepareStatement(sqlDeleteProductDistributors);
            psDeleteProductDistributors.setInt(1, productID);
            rowsAffected += psDeleteProductDistributors.executeUpdate();

            //Delete all connections for the product and it's attributes in Product_Attributes table
            String sqlDeleteProductAttributes = "DELETE FROM Product_Attributes WHERE product_ID = ?";
            PreparedStatement psDeleteProductAttributes = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            psDeleteProductAttributes.setInt(1, productID);
            rowsAffected += psDeleteProductAttributes.executeUpdate();

            //DELETE all connections for the product and it's categories in Product_Categories table
            String sqlDeleteProductCategories = "DELETE FROM product_categories WHERE product_ID = ?";
            PreparedStatement psDeleteProductCategories = database.getConnection().prepareStatement(sqlDeleteProductCategories);
            psDeleteProductCategories.setInt(1, productID);
            rowsAffected += psDeleteProductCategories.executeUpdate();
            
            //DELETE all connections for the product and it's categories in Product_Categories table
            String sqlDeleteProductBundles = "DELETE FROM product_bundles WHERE product_ID = ?";
            PreparedStatement psDeleteProductBundles = database.getConnection().prepareStatement(sqlDeleteProductBundles);
            psDeleteProductBundles.setInt(1, productID);
            rowsAffected += psDeleteProductBundles.executeUpdate();

            //DELETE all products from Product table, after having removed all connections to other tables
            String sqlDeleteProduct = "DELETE FROM Product WHERE product_ID = ?";
            PreparedStatement psDeleteProduct = database.getConnection().prepareStatement(sqlDeleteProduct);
            psDeleteProduct.setInt(1, productID);
            rowsAffected += psDeleteProduct.executeUpdate();

            database.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete product from database");
        }

        database.setAutoCommit(true);
        return rowsAffected;

    }

    public int editProduct(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            //Update product in product table
            String sqlUpdateProduct = "UPDATE Product SET Product_Name = ?, Product_Description = ? WHERE product_ID = ?";
            PreparedStatement psUpdateProduct = database.getConnection().prepareStatement(sqlUpdateProduct);
            psUpdateProduct.setString(1, product.getName());
            psUpdateProduct.setString(2, product.getDescription());
            psUpdateProduct.setInt(3, product.getProductID());
            rowsAffected += psUpdateProduct.executeUpdate();
            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update product in database");
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }
    
    public int editProductDistributors(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            int productID = product.getProductID();
            //Delete all old distributors for product in Product_distributors table
            String sqlDeleteDistributors = "DELETE FROM Product_Distributor WHERE product_ID = ?";
            PreparedStatement psDeleteProductDistributors = database.getConnection().prepareStatement(sqlDeleteDistributors);
            psDeleteProductDistributors.setInt(1, productID);
            psDeleteProductDistributors.executeUpdate();

            
            if (!product.getProductDistributors().isEmpty()) {
                String sqlInsertProductDistributors = "INSERT INTO Product_Categories (Product_ID, Category_ID) VALUES ";
                boolean firstline = true;
                for (Distributor distributor : product.getProductDistributors()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductDistributors += ", ";
                    }
                    sqlInsertProductDistributors += "(" + productID + ", '" + distributor.getDistributorID() + "')";
                }
                database.getConnection().prepareStatement(sqlInsertProductDistributors).executeUpdate();
            }
            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the distributors tied to productID " + product.getProductID());
        }
        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int editProductCategories(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            int productID = product.getProductID();
            String sqlDeleteProductCategories = "DELETE FROM Product_Categories WHERE product_ID = ?";
            PreparedStatement psDeleteProductCategories = database.getConnection().prepareStatement(sqlDeleteProductCategories);
            psDeleteProductCategories.setInt(1, productID);
            psDeleteProductCategories.executeUpdate();

            if (!product.getProductCategories().isEmpty()) {
                String sqlInsertProductCategories = "INSERT INTO Product_Categories (Product_ID, Category_ID) VALUES ";
                boolean firstline = true;
                for (Category category : product.getProductCategories()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductCategories += ", ";
                    }
                    sqlInsertProductCategories += "(" + productID + ", '" + category.getCategoryID() + "')";
                }
                database.getConnection().prepareStatement(sqlInsertProductCategories).executeUpdate();
            }

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the categories tied to productID " + product.getProductID());
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }
}