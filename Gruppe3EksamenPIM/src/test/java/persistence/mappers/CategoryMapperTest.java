package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import factory.SystemMode;
import java.sql.Connection;
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
    private ArrayList<Attribute> attributeList = new ArrayList();

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
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name is unique, so Category Mapper should throw IllegalArgumentException if dublicate name is attempted to be uploaded to DB
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryDublicateName() {
        //arrange
        String categoryName = "New Category";
        String categoryDescriptionNr1 = "First new description";
        String categoryDescriptionNr2 = "Second new description";
        CategoryMapper instance = new CategoryMapper(database);

        //act
        instance.addNewCategory(categoryName, categoryDescriptionNr1);
        instance.addNewCategory(categoryName, categoryDescriptionNr2);
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).<br>
     * Names with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNullName() {
        //arrange
        String categoryName = null;
        String categoryDescription = "new description";
        CategoryMapper instance = new CategoryMapper(database);

        //act
        instance.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).
     */
    @Test
    public void testAddNewCategoryNameLengthAtLimit() {
        //arrange
        String categoryName = "";
        for (int i = 0; i < 255; i++) {
            categoryName += "n";
        }
        String categoryDescription = "new description";
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
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).<br>
     * Names exceeding the 255 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNameLengthExceedLimit() {
        //arrange
        String categoryName = "";
        for (int i = 0; i < 256; i++) {
            categoryName += "n";
        }
        String categoryDescription = "new description";
        CategoryMapper instance = new CategoryMapper(database);

        //act
        instance.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Descriptions with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNullDesciption() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = null;
        CategoryMapper instance = new CategoryMapper(database);

        //act
        instance.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).
     */
    @Test
    public void testAddNewCategoryDesciptionLengthAtLimit() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "";
        for (int i = 0; i < 2550; i++) {
            categoryDescription += "n";
        }
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
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Descriptions exceeding the 2550 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryDesciptionLengthExceedLimit() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "";
        for (int i = 0; i < 2551; i++) {
            categoryDescription += "n";
        }
        CategoryMapper instance = new CategoryMapper(database);

        //act
        instance.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of getCategories method, of class CategoryMapper.
     */
    @Test
    public void testGetCategories() {
        //arrange
        CategoryMapper instance = new CategoryMapper(database);

        //act
        ArrayList<Category> result = instance.getCategories(attributeList);

        //assert
        assertEquals(numberOfCategoriesInDB, result.size());
    }

    /**
     * Negative Test of getCategories method, of class CategoryMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetCategoriesNoCategoriesTableInDB() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Categories");
        }
        CategoryMapper instance = new CategoryMapper(database);

        //act
        ArrayList<Category> result = instance.getCategories(attributeList);

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
    
    /**
     * Negative Test of deleteCategory method, of class CategoryMapper.<br>
     * If given an ID that does not match an CategoryID in the DB, it will affect 0 rows, and should therefore return 0.
     */
    @Test
    public void testNegativeDeleteCategoryNoMatchingID() {
        int categoryID = 0;
        CategoryMapper instance = new CategoryMapper(database);

        //act
        int result = instance.deleteCategory(categoryID);

        //assert
        int expResult = 0;
        assertEquals(expResult, result);
    }

    /**
     * Negative Test of deleteCategory method, of class CategoryMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeleteCategoryNoCategoriesTableInDB() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Categories");
        }

        int categoryID = 1;
        CategoryMapper instance = new CategoryMapper(database);

        //act
        int result = instance.deleteCategory(categoryID);
    }
    
    @Test
    public void testEditCategory() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        Category category = new Category (categoryID, categoryName, categoryDescription, categoryAttributes);
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        int result = instance.editCategory(category);
        
        //assert
        int expresult = 1;
        assertEquals(expresult, result);
    }
    
   /* @Test
    public void testEditAttributesToCategory() {
        int categoryID = 1;
        String categoryName = "hej";
        String categoryDescription = "hejhej";
        ArrayList<Attribute> categoryAttributes = new ArrayList(Arrays.asList(new String[] {"hej", "hejhejhej"}));
        Category category = new Category(categoryID, categoryName, categoryDescription, attributeList);
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        instance.editAttributeToCategories(category);
        ArrayList<Attribute> attributeList = storageFacade.getAttributes();
        
        //assert
        assertTrue()
    } */
    
    /**
     * Trying to update a category with a null value and expecting a crash due to it.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void negativeTestEditCategory() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = null;
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        Category category = new Category (categoryID, categoryName, categoryDescription, categoryAttributes);
        CategoryMapper instance = new CategoryMapper(database);
        
        //act
        int result = instance.editCategory(category);
    }
}
