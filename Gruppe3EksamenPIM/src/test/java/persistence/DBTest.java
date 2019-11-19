/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import factory.SystemMode;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
public class DBTest {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROPERTIESFILEPATH = "/db.properties";

    @Test
    public void testConstructorForTests() {
        try {
            //arrange
            SystemMode systemMode = SystemMode.TEST;

            //act
            DB result = new DB(systemMode);
            Connection testConnection = result.getConnection();

            //assert
            assertNotNull(result);
            assertNotNull(testConnection);

        } catch (SQLException ex) {
            fail("An SQL exception was caught unexpectedly");
        }
    }

    @Test
    public void testConstructorForProduction() {
        try {
            //arrange
            SystemMode systemMode = SystemMode.PRODUCTION;

            //act
            DB result = new DB(systemMode);
            Connection testConnection = result.getConnection();

            //assert
            assertNotNull(result);
            assertNotNull(testConnection);

        } catch (SQLException ex) {
            fail("An SQL exception was caught unexpectedly");
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoDBFile() {

        //arrange
        SystemMode systemMode = SystemMode.TEST;
        DB database = new DB(systemMode);
        String badPropertiesPathNoFile = "";

        //act
        database.createConnection(badPropertiesPathNoFile, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionEmptyDBFile() {

        //arrange
        SystemMode systemMode = SystemMode.TEST;
        DB database = new DB(systemMode);
        String badPropertiesPathEmptyFile = "/testEmptyDB.properties";

        //act
        database.createConnection(badPropertiesPathEmptyFile, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionBadInfoDBFile() {

        //arrange
        SystemMode systemMode = SystemMode.TEST;
        DB database = new DB(systemMode);
        String badPropertiesPathBadInfo = "/testBadInfoDB.properties";

        //act
        database.createConnection(badPropertiesPathBadInfo, DRIVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCreateConnectionNoSuitableDriver() {
        //arrange
        SystemMode systemMode = SystemMode.TEST;
        DB database = new DB(systemMode);
        String badDriver = "NoClassLikeThis";

        //act
        database.createConnection(PROPERTIESFILEPATH, badDriver);
    }

    @Test
    public void testGetConnectionNoConnection() {
        try {
            //arrange
            SystemMode systemMode = SystemMode.TEST;
            DB database = new DB(systemMode);
            database.setConn(null);

            //act
            Connection result = database.getConnection();

            //assert
            assertNotNull(database);
            assertNotNull(result);

        } catch (SQLException ex) {
            fail("An SQL exception was caught unexpectedly");
        }
    }

}
