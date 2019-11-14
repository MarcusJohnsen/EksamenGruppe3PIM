package businessLogic;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import persistence.mappers.FakeCategoryMapper;

/**
 *
 * @author Marcus
 */
public class CategoryTest {

    private static FakeCategoryMapper mapper;
    private static int highestCategoryIDInDB;

    private final static int DbCategoryID1 = 1;
    private final static int DbCategoryID2 = 2;
    private final static int DbCategoryID3 = 3;

    private final static String DbCategoryName1 = "Category 1";
    private final static String DbCategoryName2 = "Category 2";
    private final static String DbCategoryName3 = "Category 3";

    private final static String DbCategoryDescription1 = "Description 1 used for test";
    private final static String DbCategoryDescription2 = "Description 2 used for test";
    private final static String DbCategoryDescription3 = "Description 3 used for test";

    private final static int amountOfCategoriesInitiallyInDB = 3;

    @Before
    public void setup() {
        Category.emptyCategoryList();
        FakeCategoryMapper fakeMapper = new FakeCategoryMapper();

        ArrayList<Category> categoryList = new ArrayList();

        categoryList.add(new Category(DbCategoryID1, DbCategoryName1, DbCategoryDescription1));
        categoryList.add(new Category(DbCategoryID2, DbCategoryName2, DbCategoryDescription2));
        categoryList.add(new Category(DbCategoryID3, DbCategoryName3, DbCategoryDescription3));

        highestCategoryIDInDB = fakeMapper.setProductInformation(categoryList);
        mapper = fakeMapper;
        Category.setCategoryMapper(mapper);
    }

    @Test
    public void testCreateNewCategory() {
        //arrange
        String name = "badeværelse";
        String description = "artikler der bruges til badeværelsesbrug";
        int expectedID = highestCategoryIDInDB + 1;

        //add
        Category result = Category.createNewCategory(name, description);

        //assert
        assertTrue(name.equals(result.getName()));
        assertTrue(description.equals(result.getDescription()));
        assertEquals(expectedID, result.getCategoryID());
    }

    @Test
    public void testCreate3NewCategories() {
        //arrange
        String name1 = "køkken";
        String name2 = "gang";
        String name3 = "stue";
        String description1 = "artikler der bruges til køkkenbrug";
        String description2 = "artikler der bruges til gangbrug";
        String description3 = "artikler der bruges til stuebrug";
        int expectedID1 = highestCategoryIDInDB + 1;
        int expectedID2 = highestCategoryIDInDB + 2;
        int expectedID3 = highestCategoryIDInDB + 3;

        //add
        Category result1 = Category.createNewCategory(name1, description1);
        Category result2 = Category.createNewCategory(name2, description2);
        Category result3 = Category.createNewCategory(name3, description3);

        //assert
        assertTrue(name1.equals(result1.getName()));
        assertTrue(name2.equals(result2.getName()));
        assertTrue(name3.equals(result3.getName()));
        assertTrue(description1.equals(result1.getDescription()));
        assertTrue(description2.equals(result2.getDescription()));
        assertTrue(description3.equals(result3.getDescription()));
        assertEquals(expectedID1, result1.getCategoryID());
        assertEquals(expectedID2, result2.getCategoryID());
        assertEquals(expectedID3, result3.getCategoryID());
    }

    @Test
    public void testSetupCategoriesFromDB() {
        //act
        Category.setupCategoryListFromDB();
        ArrayList<Category> result = Category.getCategoryList();
        int expectedCategoryListSize = amountOfCategoriesInitiallyInDB;

        //assert
        assertEquals(expectedCategoryListSize, result.size());
        assertEquals(DbCategoryID1, result.get(0).getCategoryID());
        assertEquals(DbCategoryID2, result.get(1).getCategoryID());
        assertEquals(DbCategoryID3, result.get(2).getCategoryID());
        assertTrue(DbCategoryName1.equals(result.get(0).getName()));
        assertTrue(DbCategoryName2.equals(result.get(1).getName()));
        assertTrue(DbCategoryName3.equals(result.get(2).getName()));
        assertTrue(DbCategoryDescription1.equals(result.get(0).getDescription()));
        assertTrue(DbCategoryDescription2.equals(result.get(1).getDescription()));
        assertTrue(DbCategoryDescription3.equals(result.get(2).getDescription()));
    }

    @Test
    public void testSetupCategoriesFromDBMultipleTimes() {
        //act
        Category.setupCategoryListFromDB();
        Category.setupCategoryListFromDB();
        Category.setupCategoryListFromDB();
        ArrayList<Category> result = Category.getCategoryList();

        //assert
        int expectedCategoryListSize = amountOfCategoriesInitiallyInDB;
        assertEquals(expectedCategoryListSize, result.size());
        assertEquals(DbCategoryID1, result.get(0).getCategoryID());
        assertEquals(DbCategoryID2, result.get(1).getCategoryID());
        assertEquals(DbCategoryID3, result.get(2).getCategoryID());
        assertTrue(DbCategoryName1.equals(result.get(0).getName()));
        assertTrue(DbCategoryName2.equals(result.get(1).getName()));
        assertTrue(DbCategoryName3.equals(result.get(2).getName()));
        assertTrue(DbCategoryDescription1.equals(result.get(0).getDescription()));
        assertTrue(DbCategoryDescription2.equals(result.get(1).getDescription()));
        assertTrue(DbCategoryDescription3.equals(result.get(2).getDescription()));
    }

    @Test
    public void testDeleteCategory() {
        //arrange
        Category category = Category.createNewCategory("New Category Test", "This category is only for tests");
        int categoryID = category.getCategoryID();

        //act
        boolean result = Category.deleteCategory(categoryID);

        //assert
        assertTrue(result);
        assertFalse(Category.getCategoryList().contains(category));
        assertFalse(mapper.getCategoryInformation().contains(category));
    }

    @Test
    public void testDeleteCategoryNotFoundNotEmptyList() {
        //arrange
        Category category = Category.createNewCategory("New Category Test", "This category is only for tests");
        int categoryID = category.getCategoryID();

        //act
        boolean result = Category.deleteCategory(categoryID + 1);

        //assert
        assertFalse(result);
        assertTrue(Category.getCategoryList().contains(category));
        assertTrue(mapper.getCategoryInformation().contains(category));
    }
    
        @Test
    public void testDeleteCategoryNotFoundEmptyList() {
        //arrange
        mapper.getCategoryInformation().clear();

        //act
        boolean result = Category.deleteCategory(5);

        //assert
        assertFalse(result);
    }
}
