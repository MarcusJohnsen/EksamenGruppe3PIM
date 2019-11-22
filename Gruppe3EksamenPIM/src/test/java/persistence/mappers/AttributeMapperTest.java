package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Product;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import persistence.DB;

public class AttributeMapperTest {

    private static Connection testConnection;
    private final DB database = new DB(SystemMode.TEST);
    private final AttributeMapper attributeMapper = new AttributeMapper(database);

    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");
                stmt.execute("drop table if exists Categories");
                stmt.execute("drop table if exists Attributes");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");

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

    @Test
    public void testSetUpOK() {
        // Just check that we have a connection.
        assertNotNull(testConnection);
    }
    
    /**
     * Trying to insert a null name value into the database, and thus expecting a crash
     */
    @Test (expected = IllegalArgumentException.class)
    public void negativeTestAddNewAttribute() {
        //arrange
        String attributeName = null;
        AttributeMapper instance = new AttributeMapper(database);
        
        //act
        Attribute result = instance.addNewAttribute(attributeName);
    }
    
    /**
     * Negative Test of getAttributes method, from class AttributeMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Product_Attributes table before running the test.
     */
    @Test (expected = IllegalArgumentException.class)
    public void negativeTestGetAttributes() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        ArrayList<Attribute> result = attributeMapper.getAttributes();
    }
    
    @Test
    public void testDeleteAttribute() {
        //arrange
        int attributeID = 1;
        
        //act
        int result = attributeMapper.deleteAttribute(attributeID);
        
        //assert
        int expResult = 1;
        assertEquals(expResult, result); 
    }
    
    /**
     * Negative Test of deleteAttribute method, from class AttributeMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Attributes table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestDeleteAttribute() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        int attributeID = 1;
        
        //act
        int result = attributeMapper.deleteAttribute(attributeID);
    }
    
    /**
     * this test creates an attribute and puts it in a category, and then creates a category and puts it in a product
     * the value of the attribute is cleared and the method is run. The result shows that if the value is empty
     * then the method creates an empty String.
     */
    @Test
    public void testUpdateProductAttributeSelectionsAttributeValuesNull() {
        //arrange
        int attributeID = 6;
        String attributeName = "hej";
        HashMap<Integer, String> attributeValues = new HashMap();
        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);
        
        int categoryID = 3;
        String categoryName = "New Category";
        String categoryDescription = "New Description";
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        categoryAttributes.add(attribute);
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);
        
        int productID = 2;
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList();
        ArrayList<Category> productCategories = new ArrayList();
        productCategories.add(category);
        Product product = new Product(productID, productName,productDescription, productPicturePath, productDistributors, productCategories);
        
        //act
        attribute.getAttributeValues().clear();
        attributeMapper.updateProductAttributeSelections(product);
        
        //assert
        assertNotNull(attribute.getAttributeValues());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void negativeTestUpdateProductAttributeSelections() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Attributes");
            database.getConnection().createStatement().execute("drop table if exists Product");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        
        /* int attributeID = 6;
        String attributeName = null;
        HashMap<Integer, String> attributeValues = new HashMap();
        Attribute attribute = new Attribute(attributeID, attributeName, attributeValues);
        
        int categoryID = 3;
        String categoryName = "New Category";
        String categoryDescription = "New Description";
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        categoryAttributes.add(attribute);
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes); */
        
        int productID = 2;
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList();
        ArrayList<Category> productCategories = new ArrayList();
       // productCategories.add(category);
        Product product = new Product(productID, productName,productDescription, productPicturePath, productDistributors, productCategories);
        
        attributeMapper.updateProductAttributeValues(product);
        
    }
}