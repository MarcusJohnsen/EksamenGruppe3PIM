package businessLogic;

import java.util.ArrayList;

/**
 *
 * @author Marcus
 */
public class Category {

    private int categoryID;
    private String name;
    private String description;

    private static ArrayList<Category> categoryList = new ArrayList();

    public Category(int categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public static void setupCategoryListFromDB(ArrayList<Category> CategoryListFromDB) {
        categoryList = CategoryListFromDB;
    }

    public static void addToCategoryList(Category newCategory) {
        categoryList.add(newCategory);
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
        return categoryList.remove(findCategoryOnID(categoryID));
    }

    public void editCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static boolean validateCategoryInput(String categoryName, String categoryDescription, Integer categoryID) throws IllegalArgumentException {
        if (categoryName.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (categoryDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }

        for (Category categoryInList : categoryList) {
            if (categoryName.equals(categoryInList.name)) {
                if (categoryID != null) {
                    if (categoryID != categoryInList.getCategoryID()) {
                        throw new IllegalArgumentException("name already in use");
                    }
                } else {
                    throw new IllegalArgumentException("name already in use");
                }
            }
        }
        return true;
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
