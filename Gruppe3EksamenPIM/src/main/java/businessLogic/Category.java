package businessLogic;

import java.util.ArrayList;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 *
 * @author Marcus
 */
public class Category implements Comparable<Category> {

    private int categoryID;
    private String name;
    private String description;
    private TreeSet<Attribute> categoryAttributes;

    private static TreeSet<Category> categoryList = new TreeSet();

    public Category(int categoryID, String name, String description, TreeSet<Attribute> categoryAttributes) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        if (categoryAttributes != null) {
            this.categoryAttributes = categoryAttributes;
        } else {
            this.categoryAttributes = new TreeSet();
        }
    }

    /**
     *
     * @param CategoryListFromDB Gets the list of Category-objects from the DataBase and stores them in an TreeSet.
     */
    public static void setupCategoryListFromDB(TreeSet<Category> CategoryListFromDB) {
        categoryList = CategoryListFromDB;
    }

    /**
     *
     * @param newCategory Adds a new category-object to the categoryList.
     */
    public static void addToCategoryList(Category newCategory) {
        categoryList.add(newCategory);
    }

    /**
     *
     * @param categoryID Traverses through the categoryList in search of a specific category based on its ID
     * @return The category object if it is found. If not found, a null value is returned.
     */
    public static Category findCategoryOnID(int categoryID) {
        for (Category category : categoryList) {
            if (category.categoryID == categoryID) {
                return category;
            }
        }
        return null;
    }

    public static TreeSet<Attribute> getCategoryAttributesFromList(TreeSet<Category> categoryList) {
        TreeSet<Attribute> result = new TreeSet();
        for (Category category : categoryList) {
            result.addAll(getCategoryAttributes(category));
        }
        return result;
    }
    
    public static void deleteAttributeOnCategories(Attribute attribute) {
        for (Category category : categoryList) {
            category.categoryAttributes.remove(attribute);
        }
    }

    public static TreeSet<Attribute> getCategoryAttributes(Category category) {
        return category.categoryAttributes;
    }

    /**
     *
     * @param categoryID ¨categoryId is used to find the matching category Object and delete it from the categoryList.
     * @return Boolean true if it is deleted.
     */
    public static boolean deleteCategory(int categoryID) {
        return categoryList.remove(findCategoryOnID(categoryID));
    }

    /**
     *
     * @param name
     * @param description Edits the variables (name and description) by giving them new values.
     */
    public void editCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @param categoryName Validating the categoryInput. The categoryName must not be empty or already existing.
     * @param categoryDescription The categoryDescription must not be empty.
     * @param categoryID The categoryID must not be null or already existing.
     * @return Boolean true if name, description and ID fullfills the above-mentioned prerequisites.
     * @throws IllegalArgumentException if Boolean is not true.
     */
    public static boolean validateCategoryInput(String categoryName, String categoryDescription, Integer categoryID) throws IllegalArgumentException {
        if (categoryName.isEmpty()) {
            throw new IllegalArgumentException("please fill out category-name field");
        }
        if (categoryDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out category-description field");
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

    /**
     *
     * @param categoryChoices Traverses the categoryList in search of matching categoryID's. If matching occur, the category object is added to the result-List.
     * @return The result-List
     */
    public static TreeSet<Category> getMatchingCategoriesOnIDs(ArrayList<String> categoryChoices) {
        TreeSet<Category> result = new TreeSet();
        for (Category category : categoryList) {
            if (categoryChoices.contains(Integer.toString(category.getCategoryID()))) {
                result.add(category);
            }
        }
        return result;
    }

    /**
     *
     * @return The categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return The categoryList
     */
    public static TreeSet<Category> getCategoryList() {
        return categoryList;
    }

    /**
     *
     * @return The categoryAttributes
     */
    public TreeSet<Attribute> getCategoryAttributes() {
        return categoryAttributes;
    }

    /**
     *
     * @param categoryAttributes Sets the categoryAttributes
     */
    public void setCategoryAttributes(TreeSet<Attribute> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }
    
    public static int getTotalCategoryCount(){
        return categoryList.size();
    }
    
    /**
     * !! WORKING PROGRESS !!
     * 
     * @author Michael
     * @return 
     */
    public static ArrayList<Pair<Category, Integer>> topTenCategories(){
        ArrayList<Pair<Category, Integer>> result = new ArrayList();
        
        
        
        return result;
    }

    @Override
    public int compareTo(Category otherCategory) {

        int thisID = this.categoryID;
        int otherID = otherCategory.categoryID;
        return thisID - otherID;

    }
}
