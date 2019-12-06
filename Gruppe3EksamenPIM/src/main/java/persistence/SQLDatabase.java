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
public class SQLDatabase {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROPERTIESFILEPATH = "/db.properties";
    private String URL;
    private String USER;
    private String PASSWORD;
    private SystemMode systemMode;
    private Connection conn = null;

    public SQLDatabase(SystemMode systemMode) {
        this.systemMode = systemMode;
        createConnection(PROPERTIESFILEPATH, DRIVER);
    }

    public Connection getConnection() {
        if (conn == null) {
            createConnection(PROPERTIESFILEPATH, DRIVER);
        }
        return conn;
    }

    void createConnection(String propertiesFilePath, String driver) throws IllegalArgumentException {
        try {
            InputStream input = SQLDatabase.class.getResourceAsStream(propertiesFilePath);
            Properties pros = new Properties();

            pros.load(input);

            switch (systemMode) {
                case PRODUCTION:
                case DEVELOPMENT:
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
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Cannot connect to properties file");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Cannot find driver class for the database");
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Could not establish connection to the database");
        }
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void setAutoCommit(boolean autoCommitSetting) {
        try {
            if (conn.isClosed()) {
                createConnection(PROPERTIESFILEPATH, DRIVER);
            } else {
                conn.setAutoCommit(autoCommitSetting);
            }

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("An error occurred when change connection autoCommit status");
        }
    }

    public void rollBack() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("An error occurred when trying to roll back commits for the database connection");
        }
    }

}
