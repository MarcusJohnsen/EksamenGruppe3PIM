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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.SQLDatabase;

public class AttributeMapperTest {

    private static Connection testConnection;
    private final static SQLDatabase database = new SQLDatabase(SystemMode.TEST);
    private final AttributeMapper attributeMapper = new AttributeMapper(database);

    //setting up common variables so I won't have to write them for every single test
    private final int attributeID = 1;
    private String attributeName = "Height";
    private final HashMap<Integer, String> attributeValues = new HashMap();

    /**
     * running this setup just once before the test class is run to make sure the database is entirely cleared and functioning
     */
    @BeforeClass
    public static void oneTimeSetup() {
        try {
            testConnection = database.getConnection();
            try (Statement stmt = testConnection.createStatement()) {
                stmt.addBatch("drop table if exists Product_Distributor");
                stmt.addBatch("drop table if exists Product_Categories");
                stmt.addBatch("drop table if exists Product_Attributes");
                stmt.addBatch("drop table if exists Product_Bundles");
                stmt.addBatch("drop table if exists Category_Attributes");
                stmt.addBatch("drop table if exists Product");
                stmt.addBatch("drop table if exists Categories");
                stmt.addBatch("drop table if exists Attributes");
                stmt.addBatch("drop table if exists Distributor");
                stmt.addBatch("drop table if exists Bundles");

                stmt.addBatch("create table Product like Product_Test");
                stmt.addBatch("insert into Product select * from Product_Test");

                stmt.addBatch("create table Categories like Categories_Test");
                stmt.addBatch("insert into Categories select * from Categories_Test");

                stmt.addBatch("create table Bundles like Bundles_Test");
                stmt.addBatch("insert into Bundles select * from Bundles_Test");

                stmt.addBatch("create table Distributor like Distributor_Test");
                stmt.addBatch("insert into Distributor select * from Distributor_Test");

                stmt.addBatch("create table Product_Distributor like Product_Distributor_Test");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.addBatch("insert into Product_Distributor select * from Product_Distributor_Test");

                stmt.addBatch("create table Product_Bundles like Product_Bundles_Test");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Bundles select * from Product_Bundles_Test");

                stmt.addBatch("create table Product_Categories like Product_Categories_Test");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Categories select * from Product_Categories_Test");
                stmt.executeBatch();
                stmt.close();
            }
        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    /**
     * Running these statements before each test to make sure we have cleared and functioning tables to avoid duplicate entries.
     */
    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.addBatch("drop table if exists Product_Categories");
                stmt.addBatch("drop table if exists Product_Attributes");
                stmt.addBatch("drop table if exists Category_Attributes");
                stmt.addBatch("drop table if exists Categories");
                stmt.addBatch("drop table if exists Attributes");

                stmt.addBatch("create table Categories like Categories_Test");
                stmt.addBatch("insert into Categories select * from Categories_Test");

                stmt.addBatch("create table Attributes like Attributes_Test");
                stmt.addBatch("insert into Attributes select * from Attributes_Test");

                stmt.addBatch("create table Product_Attributes like Product_Attributes_Test");
                stmt.addBatch("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.addBatch("insert into Product_Attributes select * from Product_Attributes_Test");

                stmt.addBatch("create table Category_Attributes like Category_Attributes_Test");
                stmt.addBatch("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.addBatch("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.addBatch("insert into Category_Attributes select * from Category_Attributes_Test");
                stmt.executeBatch();
                stmt.close();
            }
        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    @Test
    public void testSetUpOK() {
        // Just checking that we have a connection.
        assertNotNull(testConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestAddNewAttribute() {
        //arrange
        attributeName = null;
        AttributeMapper instance = new AttributeMapper(database);

        //act
        instance.addNewAttribute(attributeName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestGetAttributes() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        attributeMapper.getAttributes();
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestDeleteAttribute() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        //act
        attributeMapper.deleteAttribute(attributeID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestEditAttribute() {
        //arrange
        attributeName = null;
        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);

        //act
        attributeMapper.editAttribute(attribute);
    }

    @Test
    public void testUpdateProductAttributeSelectionsAttributeValuesNull() {
        //arrange
        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);

        int categoryID = 3;
        String categoryName = "New Category";
        String categoryDescription = "New Description";
        TreeSet<Attribute> categoryAttributes = new TreeSet(Arrays.asList(new Attribute[]{attribute}));
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        int productID = 2;
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        TreeSet<Distributor> productDistributors = new TreeSet();
        TreeSet<Category> productCategories = new TreeSet(Arrays.asList(new Category[]{category}));
        Product product = new Product(productID, productName, productDescription, productPicturePath, productDistributors, productCategories);

        //act
        attribute.getAttributeValues().clear();
        attributeMapper.updateProductAttributeSelections(product);

        //assert
        assertNotNull(attribute.getAttributeValues());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestUpdateProductAttributionValues() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);

        int categoryID = 3;
        String categoryName = "New Category";
        String categoryDescription = "New Description";
        TreeSet<Attribute> categoryAttributes = new TreeSet(Arrays.asList(new Attribute[]{attribute}));
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        int productID = 1;
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        TreeSet<Distributor> productDistributors = new TreeSet();
        TreeSet<Category> productCategories = new TreeSet(Arrays.asList(new Category[]{category}));
        Product product = new Product(productID, productName, productDescription, productPicturePath, productDistributors, productCategories);

        //act
        attributeMapper.updateProductAttributeValues(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestUpdateProductAttributeSelections() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        int productID = 2;
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        TreeSet<Distributor> productDistributors = new TreeSet();
        TreeSet<Category> productCategories = new TreeSet();
        Product product = new Product(productID, productName, productDescription, productPicturePath, productDistributors, productCategories);

        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);
        product.getProductAttributes().add(attribute);

        //act
        attributeMapper.updateProductAttributeSelections(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeBulkEditOnProductIDsNoProductIDs() {
        //arrange
        ArrayList<Integer> productIDs = new ArrayList();

        //act
        attributeMapper.bulkEditOnProductIDs(productIDs, attributeValues);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeBulkEditOnProductIDsNoAttributeValues() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
            database.getConnection().createStatement().execute("drop table if exists Category_Attributes");
            database.getConnection().createStatement().execute("drop table if exists Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        ArrayList<Integer> productIDs = new ArrayList();
        HashMap<Integer, String> attributeValues2 = new HashMap();
        attributeValues2.put(4, "hej");

        //act
        attributeMapper.bulkEditOnProductIDs(productIDs, attributeValues2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeBulkEditOnProductIDs() {
        ArrayList<Integer> productIDs = new ArrayList();
        int productIDNotInDatabase = 123;
        productIDs.add(productIDNotInDatabase);
        HashMap<Integer, String> newAttributeValues = new HashMap();
        newAttributeValues.put(attributeID, "New value");

        attributeMapper.bulkEditOnProductIDs(productIDs, newAttributeValues);
    }
}
