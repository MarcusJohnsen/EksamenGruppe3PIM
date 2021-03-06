package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

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
        TreeSet<Attribute> categoryAttributes = new TreeSet();

        //act
        Category result = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        //assert
        assertEquals(categoryID, result.objectID);
        assertTrue(categoryName.equals(result.objectTitle));
        assertTrue(categoryDescription.equals(result.objectDescription));
    }

    /**
     * Testing if the constructor creates an empty arraylist of categoryAttributes if it's initially null
     */
    @Test
    public void testCreateCategoryAttributesListNull() {
        //arrange
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        TreeSet<Attribute> categoryAttributes = null;

        //act
        Category result = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        //assert
        assertTrue(result.getCategoryAttributes().isEmpty());
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
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        String categoryName2 = "bold";
        String categoryDescription2 = "kast den";
        Category result = new Category(categoryID1, categoryName1, categoryDescription1, categoryAttributes);
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
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);
        Category.addToCategoryList(category);

        //act
        Category result = Category.findCategoryOnID(5);

        //assert
        assertNull(result);

    }

    /**
     * making 3 categories and adding to list then making a new list choosing two category IDs <br>
     * And asserting if the 2 created categories are returned when checking categoryChoices
     */
    @Test
    public void getMatchingCategoriesOnIDs() {
        //arrange
        int categoryID1 = 1;
        int categoryID2 = 2;
        int categoryID3 = 3;

        Category category = new Category(categoryID1, "kæledyr", "ting til kæledyr", new TreeSet());
        Category category2 = new Category(categoryID2, "Skildpadder", "ting til skildpadder", new TreeSet());
        Category category3 = new Category(categoryID3, "Edderkopper", "ting til edderkopper", new TreeSet());

        Category.addToCategoryList(category);
        Category.addToCategoryList(category2);
        Category.addToCategoryList(category3);

        ArrayList<String> categoryChoices = new ArrayList(Arrays.asList(new String[]{Integer.toString(categoryID1), Integer.toString(categoryID2)}));

        //act
        TreeSet<Category> result = Category.getMatchingCategoriesOnIDs(categoryChoices);

        //assert
        assertTrue(result.contains(category));
        assertTrue(result.contains(category2));
        assertFalse(result.contains(category3));
    }

    @Test
    public void testEditCategory() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        String catName = "kat";
        String catDesc = "hund";

        category.editCategory(catName, catDesc);

        assertTrue(catName.equals(category.objectTitle));
        assertTrue(catDesc.equals(category.objectDescription));
    }

    @Test
    public void testSetCategoryAttributes() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        TreeSet<Attribute> categoryAttributes2 = new TreeSet(Arrays.asList(new String[]{"hej"}));
        category.setCategoryAttributes(categoryAttributes2);

        assertEquals(categoryAttributes2, category.getCategoryAttributes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditCategoryNameAlreadyExists() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);
        Category.addToCategoryList(category);

        int categoryID2 = 2;
        String categoryName2 = "kæledyr2";
        String categoryDescription2 = "ting til kæledyr2";
        TreeSet<Attribute> categoryAttributes2 = new TreeSet();
        Category category2 = new Category(categoryID2, categoryName2, categoryDescription2, categoryAttributes2);
        Category.addToCategoryList(category2);

        Category.validateCategoryInput(categoryName, categoryDescription2, categoryID2);
    }

    @Test
    public void testDeleteAttributeOnCategories() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        Attribute attribute1 = new Attribute(2, "Att. 2", new HashMap());
        Attribute attribute2 = new Attribute(3, "Att. 3", new HashMap());
        TreeSet<Attribute> categoryAttributes = new TreeSet(Arrays.asList(new Attribute[]{attribute1, attribute2}));
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);
        Category.addToCategoryList(category);

        Category.deleteAttributeOnCategories(attribute1);

        assertTrue(category.getCategoryAttributes().contains(attribute2));
        assertFalse(category.getCategoryAttributes().contains(attribute1));

    }
}
