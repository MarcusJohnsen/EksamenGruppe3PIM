package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.mappers.ProductMapper;

/*
 @author Gruppe 3
 */
public class DB {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {

        if (conn == null) {
            try (InputStream f = DB.class.getResourceAsStream("/db.properties")) {
                Properties pros = new Properties();
                pros.load(f);
                URL = pros.getProperty("url");
                USER = pros.getProperty("user");
                PASSWORD = pros.getProperty("password");
            } catch (IOException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conn;
    }

    public static void executeUpdate(String sql) {
        try {
            getConnection().prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = getConnection().prepareStatement(sql).executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ProductMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
