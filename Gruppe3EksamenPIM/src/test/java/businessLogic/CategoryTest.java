package businessLogic;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Marcus
 */
public class CategoryTest {
    
    @Before
    public void setup() {
        Category.getCategoryList().clear();
    }
    
    @Test
    public void testCreateCategory() {
        //arrange
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
    
        //act
        Category result = new Category (categoryID, categoryName, categoryDescription);
    
        //assert
        assertEquals(categoryID, result.getCategoryID());
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void negativeTestCreateCategoryNoName() {
        //arrange
        String categoryName = "";
        String categoryDescription = "Category has no name";
        
        //act
        Category.validateCategoryInput(categoryName, categoryDescription, null);
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void negativeTestCreateCategoryNoDescription() {
        //arrange
        String categoryName = "Category has no description";
        String categoryDescription = "";
        
        //act
        Category.validateCategoryInput(categoryName, categoryDescription, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestCreateCategoryDuplicateName() {
        //arrange
        int categoryID1 = 1;
        String categoryName1 = "bold";
        String categoryDescription1 = "spark til den";
        String categoryName2 = "bold";
        String categoryDescription2 = "kast den";
        Category result = new Category (categoryID1, categoryName1, categoryDescription1);
        Category.addToCategoryList(result);
    
        //act
        Category.validateCategoryInput(categoryName2, categoryDescription2, null);
    }
    
    @Test
    public void negativeTestFindCategoryOnIDNull() {
        //arrange
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        Category category = new Category (categoryID, categoryName, categoryDescription);
        Category.addToCategoryList(category);
        
        //act
        Category result = Category.findCategoryOnID(5);
        
        //assert
        assertNull(result);
        
    }
}