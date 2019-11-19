package persistence;

import factory.SystemMode;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 @author Gruppe 3
 */
public class DB {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROPERTIESFILEPATH = "/db.properties";
    private String URL;
    private String USER;
    private String PASSWORD;
    private SystemMode systemMode;
    private Connection conn = null;

    public DB(SystemMode systemMode) {
        this.systemMode = systemMode;
        createConnection(PROPERTIESFILEPATH, DRIVER);
    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            createConnection(PROPERTIESFILEPATH, DRIVER);
        }
        return conn;
    }

    void createConnection(String propertiesFilePath, String driver) throws IllegalArgumentException {
        try {
            InputStream f = DB.class.getResourceAsStream(propertiesFilePath);
            Properties pros = new Properties();
            
            pros.load(f);
            
            switch (systemMode) {
                case PRODUCTION:
                    URL = pros.getProperty("url");
                    break;
                case TEST:
                    URL = pros.getProperty("fakeurl");
                    break;
            }

            USER = pros.getProperty("user");
            PASSWORD = pros.getProperty("password");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new IOException("Could not find all necessary fields in the properties file");
            }

            Class.forName(driver);

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (IOException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Cannot connect to properties file");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Cannot find driver class for the database");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Could not establish connection to the database");
        }
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
