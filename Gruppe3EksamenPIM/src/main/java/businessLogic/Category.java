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

    public Category (int categoryID, String name, String description) {
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
    
    public static Category createNewCategory (String name, String description) {
        Category category = new Category (name, description);
        //int newCategoryID = CategoryMapper.addcategory(category);
        category.categoryID = newCategoryID;
        categoryList.add(category);
        return category;
    }
    
    public static void setupCategoryListFromDB() {
        categoryList = categoryMapper.getCategories();
    }
    
    public static Category findCategoryOnID(int productID) {
        for (Category category : categoryList) {
            if (category.categoryID == categoryID) {
                return category;
            }
        }
        return null;
    }
    
    public static void emptyCategoryList() {
        CategoryList.clear();
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