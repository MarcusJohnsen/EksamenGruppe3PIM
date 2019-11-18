/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Category;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapperTest {

    private static Connection testConnection;
    private final DB database = new DB(SystemMode.TEST);
    private final int numberOfCategoriesInDB = 3;

    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Categories");
                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");
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

    public CategoryMapperTest() {
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.
     */
    @Test
    public void testAddNewCategory() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "This is a new category";
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        Category result = instance.addNewCategory(categoryName, categoryDescription);
        
        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getCategoryID());
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
    }

    /**
     * Test of getCategories method, of class CategoryMapper.
     */
    @Test
    public void testGetCategories() {
        //arrange
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        ArrayList<Category> result = instance.getCategories();
        
        //assert
        assertEquals(numberOfCategoriesInDB, result.size());
    }

    /**
     * Test of deleteCategory method, of class CategoryMapper.
     */
    @Test
    public void testDeleteCategory() {
        int categoryID = 1;
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        int result = instance.deleteCategory(categoryID);
        
        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }

}
