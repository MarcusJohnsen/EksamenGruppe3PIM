package persistence.mappers;

import businessLogic.Attribute;
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
    private final int numberOfAttributesInDB = 3;

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
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Attributes");
        }
        AttributeMapper instance = new AttributeMapper(database);
        ArrayList<Attribute> result = instance.getAttributes();
    }
    
    @Test
    public void testDeleteAttribute() {
        //arrange
        int attributeID = 1;
        
        //act
        int result = attributeMapper.deleteAttribute(attributeID);
        //ArrayList<Attribute> result2 = attributeMapper.getAttributes();
        
        //assert
        int expResult = 1;
        assertEquals(expResult, result); 
        //assertEquals(result2.size(), 5);
    }
}