package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import persistence.SQLDatabase;

/**
 *
 * @author Marcus
 */
public class ProductMapperTest {

    private final static SQLDatabase database = new SQLDatabase(SystemMode.TEST);
    private final ProductMapper productMapper = new ProductMapper(database);
    private static Connection testConnection;

    //setting up common variables so I won't have to write them for every single test
    private final int numberOfProductsInDB = 3;
    private final int productID = 1;
    private String productName = "Cykel";
    private String productDescription = "This is a new product";
    private String productPicturePath = "newProduct.img";
    
    private Distributor distributor = new Distributor(1, "Company", "Test company");
    private final TreeSet<Distributor> productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor}));
    
    private final TreeSet<Category> categoryList = new TreeSet();
        
    @BeforeClass
    public static void oneTimeSetup() {
        try {
            testConnection = database.getConnection();
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Product_Bundles");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");
                stmt.execute("drop table if exists Categories");
                stmt.execute("drop table if exists Attributes");
                stmt.execute("drop table if exists Distributor");
                stmt.execute("drop table if exists Bundles");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");
                
                stmt.execute("create table Bundles like Bundles_Test");
                stmt.execute("insert into Bundles select * from Bundles_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");
                
                stmt.execute("create table Distributor like Distributor_Test");
                stmt.execute("insert into Distributor select * from Distributor_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");
                
                stmt.execute("create table Product_Bundles like Product_Bundles_Test");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Bundles select * from Product_Bundles_Test");

                stmt.execute("create table Product_Categories like Product_Categories_Test");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Categories select * from Product_Categories_Test");

                stmt.execute("create table Product_Attributes like Product_Attributes_Test");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Product_Attributes select * from Product_Attributes_Test");
                
                stmt.execute("create table Category_Attributes like Category_Attributes_Test");
                stmt.execute("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE category_attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Category_Attributes select * from Category_Attributes_Test");
            }
        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }
    
    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Product_Bundles");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");
                
                stmt.execute("create table Product_Bundles like Product_Bundles_Test");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Bundles select * from Product_Bundles_Test");

                stmt.execute("create table Product_Categories like Product_Categories_Test");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Categories select * from Product_Categories_Test");

                stmt.execute("create table Product_Attributes like Product_Attributes_Test");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Product_Attributes select * from Product_Attributes_Test");
                
                stmt.execute("create table Category_Attributes like Category_Attributes_Test");
                stmt.execute("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE category_attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Category_Attributes select * from Category_Attributes_Test");
            }
            categoryList.clear();
        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    @Test
    public void testSetUpOK() {
        // checking that we have a connection.
        assertNotNull(testConnection);
    }

    /**
     * Test of getProducts method, of class ProductMapper.
     */
    @Test
    public void testGetProducts() {
        //act
        TreeSet<Product> result = productMapper.getProducts(categoryList, productDistributors);

        //assert
        assertEquals(numberOfProductsInDB, result.size());
    }

    /**
     * Negative Test of getProducts method, from class ProductMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Product_Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetProductsNoProductsTableInDB() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Product");
        }
        //act
        productMapper.getProducts(categoryList, productDistributors);
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.
     */
    @Test
    public void testAddNewProduct() {
        //act
        Attribute attribute1 = new Attribute(1, "attributeTitle 1", new HashMap<Integer, String>());
        Attribute attribute2 = new Attribute(2, "attributeTitle 2", new HashMap<Integer, String>());
        Attribute attribute3 = new Attribute(3, "attributeTitle 3", new HashMap<Integer, String>());
        categoryList.add(new Category(1, "Category 1", "First test category", new TreeSet(Arrays.asList(new Attribute[]{attribute1, attribute2}))));
        categoryList.add(new Category(2, "Category 2", "Second test category", new TreeSet(Arrays.asList(new Attribute[]{attribute2, attribute3}))));
        
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors, categoryList);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
    }

    /**
     * Negative Test of addNewProduct method, of class ProductMapper.<br>
     * Name field in DB is made to be not null, unique varchar(255).<br>
     * Names with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductNullName() {
        //arrange
        productName = null;

        //act
        productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors, categoryList);
    }

    /**
     * Negative Test of addNewProduct method, of class ProductMapper.<br>
     * Description field in DB is made to be not null, unique varchar(255).<br>
     * Descriptions with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductNullDescription() {
        //arrange
        productDescription = null;
        
        //act
        productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors, categoryList);
    }

    /**
     * Test of updatePicturePath method, of class ProductMapper.
     */
    @Test
    public void testUpdatePicturePath() {
        //arrange
        String picturePath = "";

        //act
        int result = productMapper.updatePicturePath(productID, picturePath);

        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }


    /**
     * Test of deleteProduct method, of class ProductMapper.
     */
    @Test
    public void testDeleteProduct() {
        //act
        int result = productMapper.deleteProduct(productID);

        //assert
        int expResult = 13;
        assertEquals(expResult, result);
    }

    /**
     * Negative test of deleteProduct method, from class ProductMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Product_Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestDeleteProduct() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Product");
        }
        //act
        productMapper.deleteProduct(productID);
    }

    /**
     * Test of editProduct method, of class ProductMapper.
     */
    @Test
    public void testEditProduct() {
        //arrange
        Product product = new Product(productID, "newTitle", "newDescription", "newPic.img", productDistributors, categoryList);

        //act
        int result = productMapper.editProduct(product);

        //assert
        int expResult = 5;
        assertEquals(expResult, result);
    }

    /**
     * Test of editProductCategories method, of class ProductMapper.
     */
    @Test
    public void testEditProductCategories() {
        //arrange
        TreeSet<Distributor> distributors = new TreeSet(Arrays.asList(new String[]{}));
        int[] categoryID = new int[]{1, 2};
        Category category1 = new Category(categoryID[0], "Tests", "These are tests", new TreeSet<Attribute>());
        Category category2 = new Category(categoryID[1], "Tests", "These are tests", new TreeSet<Attribute>());
        categoryList.add(category1);
        categoryList.add(category2);
        int productID = 1;
        Product product = new Product(productID, "Test Product", "This product is for testing", "test.jpg", distributors, categoryList);
        AttributeMapper attributeMapper = new AttributeMapper(database);
        CategoryMapper categoryMapper = new CategoryMapper(database);
        DistributorMapper distributorMapper = new DistributorMapper(database);

        //act
        productMapper.editProductCategories(product);
        TreeSet<Attribute> attributeListFromDB = attributeMapper.getAttributes();
        TreeSet<Category> categoryListFromDB = categoryMapper.getCategories(attributeListFromDB);
        TreeSet<Distributor> distributorListFromDB = distributorMapper.getDistributors();
        TreeSet<Product> resultList = productMapper.getProducts(categoryListFromDB, distributorListFromDB);
        Product result = null;
        for (Product resultProduct : resultList) {
            if(resultProduct.getProductID() == productID){
                result = resultProduct;
                break;
            }
        }
        if(result == null){
            fail("Did not find the product that was editted in the resultList");
        }

        //assert
        assertEquals(categoryList.size(), result.getProductCategories().size());
        assertTrue(result.getProductCategories().contains(category1));
        assertTrue(result.getProductCategories().contains(category2));
    }
}
