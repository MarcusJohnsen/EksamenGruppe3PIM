package persistence.mappers;

import businessLogic.Category;
import java.util.ArrayList;

/**
 *
 * @author Marcus
 */
public class FakeCategoryMapper implements CategoryMapperInterface{

    private ArrayList<Category> categoryInformation;
    private int newHighestCategoryID;

    public FakeCategoryMapper() {
        categoryInformation = new ArrayList();
        newHighestCategoryID = 1;
    }

    @Override
    public ArrayList<Category> getCategories() {
        return categoryInformation;
    }

    @Override
    public int addNewCategory(String categoryName, String categoryDescription) {
        int newID = newHighestCategoryID++;
        Category category = new Category(newID, categoryName, categoryDescription);
        categoryInformation.add(category);
        return newID;
    }

    public ArrayList<Category> getCategoryInformation() {
        return categoryInformation;
    }

    public int setProductInformation(ArrayList<Category> categoryInformation) {
        this.categoryInformation = categoryInformation;
        newHighestCategoryID = findHighestID();
        return newHighestCategoryID - 1;
    }

    private int findHighestID() {
        int newHighest = 0;
        for (Category category : categoryInformation) {
            if (category.getCategoryID() > newHighest) {
                newHighest = category.getCategoryID();
            }
        }
        newHighest++;
        return newHighest;
    }
}