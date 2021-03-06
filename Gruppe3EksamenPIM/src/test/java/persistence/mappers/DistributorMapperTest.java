package persistence.mappers;

import businessLogic.Distributor;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.SQLDatabase;

public class DistributorMapperTest {

    private final static SQLDatabase database = new SQLDatabase(SystemMode.TEST);
    private final DistributorMapper distributorMapper = new DistributorMapper(database);
    private static Connection testConnection;

    //setting up common variables so I won't have to write them for every single test
    private String distributorName = "Arla";
    private String distributorDescription = "First new description";

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

                stmt.addBatch("create table Attributes like Attributes_Test");
                stmt.addBatch("insert into Attributes select * from Attributes_Test");

                stmt.addBatch("create table Product_Bundles like Product_Bundles_Test");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Bundles select * from Product_Bundles_Test");

                stmt.addBatch("create table Product_Categories like Product_Categories_Test");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Categories select * from Product_Categories_Test");

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

    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.addBatch("drop table if exists Product_Distributor");
                stmt.addBatch("drop table if exists Distributor");

                stmt.addBatch("create table Distributor like Distributor_Test");
                stmt.addBatch("insert into Distributor select * from Distributor_Test");

                stmt.addBatch("create table Product_Distributor like Product_Distributor_Test");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.addBatch("insert into Product_Distributor select * from Product_Distributor_Test");
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
        // checking that we have a connection.
        assertNotNull(testConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestGetDistributors() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Distributor");
            database.getConnection().createStatement().execute("drop table if exists Distributor");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Distributor");
        }
        distributorMapper.getDistributors();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeEditDistributor() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Distributor");
            database.getConnection().createStatement().execute("drop table if exists Distributor");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Distributor");
        }

        Distributor distributor = new Distributor(1, "Distributor Name, NEW FOR TEST", "Distributor description, NEW FOR TEST");
        distributorMapper.editDistributor(distributor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewDistributor() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Distributor");
            database.getConnection().createStatement().execute("drop table if exists Distributor");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Distributor");
        }

        distributorMapper.addNewDistributor(distributorName, distributorDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeleteDistributor() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Distributor");
            database.getConnection().createStatement().execute("drop table if exists Distributor");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Distributor");
        }

        int distributorID = 1;
        distributorMapper.deleteDistributor(distributorID);
    }
}
