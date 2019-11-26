package persistence.mappers;

import businessLogic.Distributor;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import persistence.DB;

/**
 * 
 * @author Marcus
 */
public class DistributorMapperTest {

    private final DB database = new DB(SystemMode.TEST);
    private final DistributorMapper distributorMapper = new DistributorMapper(database);
    private static Connection testConnection;
    private final int AmountOfDistributorsInDB = 3;
    
    private final String distributorDescription = "First new description";
    private final String distributorName = "Arla";


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
                stmt.execute("drop table if exists Distributor");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");
                
                stmt.execute("create table Distributor like Distributor_Test");
                stmt.execute("insert into Distributor select * from Distributor_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
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
        // checking that we have a connection.
        assertNotNull(testConnection);
    }
    
    @Test
    public void testGetDistributors() {
        //act
        ArrayList<Distributor> result = distributorMapper.getDistributors();
        
        //assert
        assertEquals(AmountOfDistributorsInDB, result.size());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void negativeTestGetDistributors() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Distributor");
            database.getConnection().createStatement().execute("drop table if exists Distributor");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Distributor");
        }
        ArrayList<Distributor> result = distributorMapper.getDistributors();
    }
    
    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * DistributorName field in DB is made to be not null, varchar(255). Therefore adding exactly 255 characters should not crash the program.
     */
    @Test
    public void testAddNewDistributorNameAtLimit() {
        //arrange
        String distributorName = "";
        for (int i = 0; i < 255; i++) {
            distributorName += "n";
        }
        //act
        Distributor result = distributorMapper.addNewDistributor(distributorName, distributorDescription);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getDistributorID());
        assertTrue(distributorName.equals(result.getDistributorName()));
        assertTrue(distributorDescription.equals(result.getDistributorDescription()));
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * DistributorName field in DB is made to be not null, varchar(255).<br>
     * DistributorName exceeding the 255 varchar limit should cause an exception to be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewDistributorNameExceedLimit() {
        //arrange
        String distributorName = "";
        for (int i = 0; i < 256; i++) {
            distributorName += "n";
        }
        //act
        Distributor result = distributorMapper.addNewDistributor(distributorName, distributorDescription);
    }
    
    @Test
    public void testAddNewDistributorDescriptionAtLimit() {
        //arrange
        String distributorDescription = "";
        for (int i = 0; i < 2550; i++) {
            distributorDescription += "n";
        }
        //act
        Distributor result = distributorMapper.addNewDistributor(distributorName, distributorDescription);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getDistributorID());
        assertTrue(distributorName.equals(result.getDistributorName()));
        assertTrue(distributorDescription.equals(result.getDistributorDescription()));
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * DistributorName field in DB is made to be not null, varchar(255).<br>
     * DistributorName exceeding the 255 varchar limit should cause an exception to be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewDistributorDescriptionExceedLimit() {
        //arrange
        String distributorDescription = "";
        for (int i = 0; i < 2551; i++) {
            distributorDescription += "n";
        }
        //act
        Distributor result = distributorMapper.addNewDistributor(distributorName, distributorDescription);
    }
    
    @Test
    public void testDeleteDistributor() {
        //arrange
        int distributorID = 1;
        
        //act
        int result = distributorMapper.deleteDistributor(distributorID);
        
        //assert
        int expResult = 2;
        assertEquals(expResult, result);
    }
}