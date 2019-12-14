package persistence.mappers;

import businessLogic.Bundle;
import businessLogic.Product;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import persistence.SQLDatabase;

public class BundleMapperTest {

    private final static SQLDatabase database = new SQLDatabase(SystemMode.TEST);
    private final BundleMapper bundleMapper = new BundleMapper(database);
    private static Connection testConnection;

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
                stmt.addBatch("drop table if exists Product_Bundles");
                stmt.addBatch("drop table if exists Bundles");

                stmt.addBatch("create table Bundles like Bundles_Test");
                stmt.addBatch("insert into Bundles select * from Bundles_Test");

                stmt.addBatch("create table Product_Bundles like Product_Bundles_Test");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Bundles select * from Product_Bundles_Test");
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
    public void testNegativeAddNewBundle() {
        String bundleTitle = "Bundle title 1";
        String bundleDescription = "This is a description";
        HashMap<Product, Integer> productListForBundle = new HashMap();

        //Placing a product in the productListForBundle that has an ID not matching anything in the database
        Product product = new Product(500, "title", "description", "picturePath", new TreeSet(), new TreeSet());
        int productAmount = 5;
        productListForBundle.put(product, productAmount);

        bundleMapper.addNewBundle(bundleTitle, bundleDescription, productListForBundle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetBundle() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Bundles");
            database.getConnection().createStatement().execute("drop table if exists Bundles");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Bundles");
        }

        //
        TreeSet<Product> productList = new TreeSet();
        bundleMapper.getBundle(productList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeleteBundle() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Bundles");
            database.getConnection().createStatement().execute("drop table if exists Bundles");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Bundles");
        }
        int bundleID = 1;

        bundleMapper.deleteBundle(bundleID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeEditBundle() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Bundles");
            database.getConnection().createStatement().execute("drop table if exists Bundles");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Bundles");
        }

        Bundle bundle = new Bundle(1, "Title", "Description", null);

        bundleMapper.editBundle(bundle);
    }

}
