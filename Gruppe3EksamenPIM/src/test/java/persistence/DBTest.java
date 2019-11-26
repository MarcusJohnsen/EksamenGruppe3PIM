package persistence;

import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
public class DBTest {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROPERTIESFILEPATH = "/db.properties";
    private static final SystemMode systemMode = SystemMode.TEST;

    @Test
    public void testConstructorForTests() {
        //act
        DB result = new DB(systemMode);
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
        DB result = new DB(systemModeProduction);
        Connection testConnection = result.getConnection();

        //assert
        assertNotNull(result);
        assertNotNull(testConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoDBFile() {

        //arrange
        DB database = new DB(systemMode);
        String badPropertiesPathNoFile = "";

        //act
        database.createConnection(badPropertiesPathNoFile, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionEmptyDBFile() {

        //arrange
        DB database = new DB(systemMode);
        String badPropertiesPathEmptyFile = "/testEmptyDB.properties";

        //act
        database.createConnection(badPropertiesPathEmptyFile, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionBadInfoDBFile() {

        //arrange
        DB database = new DB(systemMode);
        String badPropertiesPathBadInfo = "/testBadInfoDB.properties";

        //act
        database.createConnection(badPropertiesPathBadInfo, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoSuitableDriver() {
        //arrange
        DB database = new DB(systemMode);
        String badDriver = "NoClassLikeThis";

        //act
        database.createConnection(PROPERTIESFILEPATH, badDriver);
    }

    @Test
    public void testGetConnectionNoConnection() {
        //arrange
        DB database = new DB(systemMode);
        database.setConnection(null);

        //act
        Connection result = database.getConnection();

        //assert
        assertNotNull(database);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSetAutoCommit() {
        //arrange
        DB database = new DB(systemMode);
        try {
            database.getConnection().close();
        } catch (SQLException ex) {
            fail("Could not close the database connection");
        }

        //act
        database.setAutoCommit(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeRollBack() {
        //arrange
        DB database = new DB(systemMode);
        try {
            database.getConnection().close();
        } catch (SQLException ex) {
            fail("Could not close the database connection");
        }

        //act
        database.rollBack();
    }

}
