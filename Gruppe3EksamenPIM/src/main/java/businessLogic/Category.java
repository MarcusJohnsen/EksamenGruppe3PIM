package businessLogic;

import java.util.ArrayList;
import persistence.mappers.CategoryMapperInterface;

/**
 *
 * @author Marcus
 */
public class Category {

    private static CategoryMapperInterface categoryMapper;

    private int categoryID;
    private String name;
    private String description;

    private static ArrayList<Category> categoryList = new ArrayList();

    public Category(int categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    private Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static void setCategoryMapper(CategoryMapperInterface newMapper) {
        categoryMapper = newMapper;
    }

    public static Category createNewCategory(String name, String description) throws IllegalArgumentException {
        Category category = new Category(name, description);

        if (validateCategoryInput(category)) {
            int newCategoryID = categoryMapper.addNewCategory(category);
            category.categoryID = newCategoryID;
            categoryList.add(category);
            return category;
        }
        
        return null;
    }

    public static void setupCategoryListFromDB() {
        categoryList = categoryMapper.getCategories();
    }

    public static Category findCategoryOnID(int categoryID) {
        for (Category category : categoryList) {
            if (category.categoryID == categoryID) {
                return category;
            }
        }
        return null;
    }

    public static boolean deleteCategory(int categoryID) {
        categoryMapper.deleteCategory(categoryID);
        return categoryList.remove(findCategoryOnID(categoryID));
    }

    public static boolean validateCategoryInput(Category category) throws IllegalArgumentException {

        if (category.name.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (category.description.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }

        for (Category categoryInList : categoryList) {
            if (category.name.equals(categoryInList.name)) {
                throw new IllegalArgumentException("name already in use");
            }
        }
        return true;
    }

    public static void emptyCategoryList() {
        categoryList.clear();
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<Category> getCategoryList() {
        return categoryList;
    }
}
