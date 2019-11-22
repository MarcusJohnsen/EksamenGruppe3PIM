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
                stmt.execute("drop table if exists Attributes");
                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");
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
     * We will try to simulate this change by removing the Attributes table before running the test.
     */
    @Test (expected = IllegalArgumentException.class)
    public void negativeTestGetAttributes() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Attributes");
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