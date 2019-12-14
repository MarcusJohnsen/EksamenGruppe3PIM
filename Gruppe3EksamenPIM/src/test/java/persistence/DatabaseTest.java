package persistence;

import com.cloudinary.Cloudinary;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SQLPROPERTIESFILEPATH = "/db.properties";
    private static final String CLOUDINARYPROPERTIESFILEPATH = "/cloudinary.properties";
    private static final String BAD_PROPERTIESPATH_BAD_INFO = "/testBadInfoDB.properties";
    private static final String BAD_PROPERTIESPATH_EMPTY_FILE = "/testEmptyDB.properties";
    private static final SystemMode systemMode = SystemMode.TEST;

    @Test

    public void testConstructorForTests() {
        //act
        SQLDatabase result = new SQLDatabase(systemMode);
        Connection testConnection = result.getConnection();

        //assert
        assertNotNull(result);
        assertNotNull(testConnection);
    }

    @Test
    public void testConstructorForProduction() {
        //arrange
        SystemMode systemModeProduction = SystemMode.PRODUCTION;

        //act
        SQLDatabase result = new SQLDatabase(systemModeProduction);
        Connection testConnection = result.getConnection();

        //assert
        assertNotNull(result);
        assertNotNull(testConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoDBFile() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);
        String badPropertiesPathNoFile = "";

        //act
        database.createConnection(badPropertiesPathNoFile, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionEmptyDBFile() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);

        //act
        database.createConnection(BAD_PROPERTIESPATH_EMPTY_FILE, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionBadInfoDBFile() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);

        //act
        database.createConnection(BAD_PROPERTIESPATH_BAD_INFO, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoSuitableDriver() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);
        String badDriver = "NoClassLikeThis";

        //act
        database.createConnection(SQLPROPERTIESFILEPATH, badDriver);
    }

    @Test
    public void testGetConnectionNoConnection() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);
        database.setConnection(null);

        //act
        Connection result = database.getConnection();

        //assert
        assertNotNull(database);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSetAutoCommit() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);

        database.setConnection(null);

        //act
        database.setAutoCommit(true);
    }

    @Test
    public void testSetAutoCommitClosed() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);

        try {
            database.getConnection().close();
        } catch (SQLException ex) {
            fail("Could not close the database connection for the test");
        }

        //act
        database.setAutoCommit(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeRollBack() {
        //arrange
        SQLDatabase database = new SQLDatabase(systemMode);
        try {
            database.getConnection().close();
        } catch (SQLException ex) {
            fail("Could not close the database connection");
        }

        //act
        database.rollBack();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateCloudinaryEmptyFile() {
        //arrange
        CloudinaryDatabase database = new CloudinaryDatabase();

        //act
        database.createCloudinary(BAD_PROPERTIESPATH_EMPTY_FILE);
    }

    @Test
    public void testGetCloudinartConnection() {
        CloudinaryDatabase database = new CloudinaryDatabase();

        Cloudinary conn = database.getCloudinaryConnection();

        assertNotNull(conn);
    }

    @Test
    public void testGetCloudinartConnectionSetNull() {
        CloudinaryDatabase database = new CloudinaryDatabase();
        database.setCloudinary(null);

        Cloudinary conn = database.getCloudinaryConnection();

        assertNotNull(conn);
    }

}
