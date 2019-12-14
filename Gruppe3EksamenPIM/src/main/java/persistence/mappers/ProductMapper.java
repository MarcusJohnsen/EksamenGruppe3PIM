package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.SQLDatabase;

public class ProductMapper {

    private SQLDatabase database;

    public ProductMapper(SQLDatabase database) {
        this.database = database;
    }

    public TreeSet<Product> getProducts(TreeSet<Category> categoryList, TreeSet<Distributor> distributorList) {
        try {
            TreeSet<Product> productList = new TreeSet();
            HashMap<Integer, TreeSet<Category>> productCategoriesMap = new HashMap();
            HashMap<Integer, TreeSet<Distributor>> productDistributorsMap = new HashMap();

            String SQL = "SELECT * FROM Product_Distributor";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                if (productDistributorsMap.get(productID) == null) {
                    productDistributorsMap.put(productID, new TreeSet());
                }
                int distributorID = rs.getInt("Distributor_ID");
                for (Distributor distributor : distributorList) {
                    if (distributor.getObjectID() == distributorID) {
                        productDistributorsMap.get(productID).add(distributor);
                    }
                }
            }

            SQL = "SELECT * FROM Product_Categories";
            rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                if (productCategoriesMap.get(productID) == null) {
                    productCategoriesMap.put(productID, new TreeSet());
                }
                int categoryID = rs.getInt("Category_ID");
                for (Category category : categoryList) {
                    if (category.getObjectID() == categoryID) {
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
                TreeSet<Distributor> productDistributors = productDistributorsMap.get(product_ID);
                TreeSet<Category> productCategories = productCategoriesMap.get(product_ID);
                Product product = new Product(product_ID, name, description, picturePath, productDistributors, productCategories);
                productList.add(product);
            }
            return productList;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get products from database");
        }
    }

    public Product addNewProduct(String productName, String productDescription, String productPicturePath, TreeSet<Distributor> productDistributors, TreeSet<Category> productCategories) {
        try {
            database.setAutoCommit(false);

            String sqlInsertNewProduct = "INSERT INTO Product (Product_Name, Product_Description, picturePath) VALUES (?, ?, ?)";
            PreparedStatement psInsertNewProduct = database.getConnection().prepareStatement(sqlInsertNewProduct, Statement.RETURN_GENERATED_KEYS);
            psInsertNewProduct.setString(1, productName);
            psInsertNewProduct.setString(2, productDescription);
            psInsertNewProduct.setString(3, productPicturePath);
            psInsertNewProduct.executeUpdate();

            ResultSet rs = psInsertNewProduct.getGeneratedKeys();
            rs.next();
            int productID = rs.getInt(1);

            String sqlInsertProductDistributors = "INSERT INTO Product_Distributor (Product_ID, Distributor_ID) VALUES ";
            boolean firstline = true;
            for (Distributor distributor : productDistributors) {
                if (firstline) {
                    firstline = false;
                } else {
                    sqlInsertProductDistributors += ", ";
                }
                sqlInsertProductDistributors += "(" + productID + ", '" + distributor.getObjectID() + "')";
            }
            database.getConnection().prepareStatement(sqlInsertProductDistributors).executeUpdate();

            if (!productCategories.isEmpty()) {
                String sqlInsertProductCategories = "INSERT INTO Product_Categories (Product_ID, Category_ID) VALUES ";
                firstline = true;
                for (Category category : productCategories) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductCategories += ", ";
                    }
                    sqlInsertProductCategories += "(" + productID + ", '" + category.getObjectID() + "')";
                }
                database.getConnection().prepareStatement(sqlInsertProductCategories).executeUpdate();
            }

            TreeSet<Attribute> productAttributes = Category.getCategoryAttributesFromList(productCategories);

            if (!productAttributes.isEmpty()) {
                String sqlInsertProductAttributes = "INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES ";
                firstline = true;
                for (Attribute productAttribute : productAttributes) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductAttributes += ", ";
                    }
                    String attributeValue = productAttribute.getAttributeValueForID(productID);
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    sqlInsertProductAttributes += "(" + productID + ", " + productAttribute.getObjectID() + ", '" + attributeValue + "')";

                }
                database.getConnection().prepareStatement(sqlInsertProductAttributes).executeUpdate();
            }

            database.getConnection().commit();
            database.setAutoCommit(true);

            Product product = new Product(productID, productName, productDescription, productPicturePath, productDistributors, productCategories);
            return product;

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't add product to database");
        }

    }

    public int updatePicturePath(int productID, String picturePath) {
        try {
            String SQL = "UPDATE Product SET picturePath = ? WHERE Product_ID = ?";
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
            String sqlDeleteProductDistributors = "DELETE FROM Product_Distributor WHERE Product_ID = ?";
            PreparedStatement psDeleteProductDistributors = database.getConnection().prepareStatement(sqlDeleteProductDistributors);
            psDeleteProductDistributors.setInt(1, productID);
            rowsAffected += psDeleteProductDistributors.executeUpdate();

            //Delete all connections for the product and it's attributes in Product_Attributes table
            String sqlDeleteProductAttributes = "DELETE FROM Product_Attributes WHERE Product_ID = ?";
            PreparedStatement psDeleteProductAttributes = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            psDeleteProductAttributes.setInt(1, productID);
            rowsAffected += psDeleteProductAttributes.executeUpdate();

            //DELETE all connections for the product and it's categories in Product_Categories table
            String sqlDeleteProductCategories = "DELETE FROM Product_Categories WHERE Product_ID = ?";
            PreparedStatement psDeleteProductCategories = database.getConnection().prepareStatement(sqlDeleteProductCategories);
            psDeleteProductCategories.setInt(1, productID);
            rowsAffected += psDeleteProductCategories.executeUpdate();

            //DELETE all connections for the product and it's categories in Product_Categories table
            String sqlDeleteProductBundles = "DELETE FROM Product_Bundles WHERE Product_ID = ?";
            PreparedStatement psDeleteProductBundles = database.getConnection().prepareStatement(sqlDeleteProductBundles);
            psDeleteProductBundles.setInt(1, productID);
            rowsAffected += psDeleteProductBundles.executeUpdate();

            //DELETE all products from Product table, after having removed all connections to other tables
            String sqlDeleteProduct = "DELETE FROM Product WHERE Product_ID = ?";
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
        try {
            int rowsAffected = 0;

            database.setAutoCommit(false);
            //Update product in product table
            String sqlUpdateProduct = "UPDATE Product SET Product_Name = ?, Product_Description = ? WHERE product_ID = ?";
            PreparedStatement psUpdateProduct = database.getConnection().prepareStatement(sqlUpdateProduct);
            psUpdateProduct.setString(1, product.getObjectTitle());
            psUpdateProduct.setString(2, product.getObjectDescription());
            psUpdateProduct.setInt(3, product.getObjectID());
            rowsAffected += psUpdateProduct.executeUpdate();

            //Delete old product distributor connections
            String sqlDeleteProductDistributors = "DELETE FROM Product_Distributor WHERE Product_ID = ?";
            PreparedStatement psDeleteProductDistributors = database.getConnection().prepareStatement(sqlDeleteProductDistributors);
            psDeleteProductDistributors.setInt(1, product.getObjectID());
            rowsAffected += psDeleteProductDistributors.executeUpdate();

            //Create new product distributor connections
            String sqlInsertProductDistributors = "INSERT INTO Product_Distributor (Product_ID, Distributor_ID) VALUES ";
            boolean firstline = true;
            for (Distributor distributor : product.getProductDistributors()) {
                if (firstline) {
                    firstline = false;
                } else {
                    sqlInsertProductDistributors += ", ";
                }
                sqlInsertProductDistributors += "(" + product.getObjectID() + ", '" + distributor.getObjectID() + "')";
            }
            rowsAffected += database.getConnection().prepareStatement(sqlInsertProductDistributors).executeUpdate();

            database.getConnection().commit();
            database.setAutoCommit(true);

            return rowsAffected;
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update product in database");
        }
    }

    public int editProductCategories(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            int productID = product.getObjectID();
            String sqlDeleteProductCategories = "DELETE FROM Product_Categories WHERE Product_ID = ?";
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
                    sqlInsertProductCategories += "(" + productID + ", '" + category.getObjectID() + "')";
                }
                database.getConnection().prepareStatement(sqlInsertProductCategories).executeUpdate();
            }

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't change the categories tied to productID " + product.getObjectID());
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }
}
