package businessLogic;

import persistence.Json.Exclude;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Category extends PIMObject {

    private static TreeSet<Category> categoryList = new TreeSet();

    @Exclude
    private TreeSet<Attribute> categoryAttributes;
    @Exclude
    private TreeSet<Product> categoryProducts;

    public Category(int categoryID, String categoryTitle, String categoryDescription, TreeSet<Attribute> categoryAttributes) {
        super(categoryID, categoryTitle, categoryDescription);
        if (categoryAttributes != null) {
            this.categoryAttributes = categoryAttributes;
        } else {
            this.categoryAttributes = new TreeSet();
        }
        this.categoryProducts = new TreeSet();
    }

    public static void setupCategoryListFromDB(TreeSet<Category> CategoryListFromDB) {
        categoryList = CategoryListFromDB;
    }

    public static void addToCategoryList(Category newCategory) {
        categoryList.add(newCategory);
    }

    /**
     * Find category with matching ID to search parameter.
     *
     * //TODO: move to SearchEngine.
     *
     * @param categoryID ID for desired category.
     * @return The category object if it is found. If not found, a null value is returned.
     */
    public static Category findCategoryOnID(int categoryID) {
        for (Category category : categoryList) {
            if (category.objectID == categoryID) {
                return category;
            }
        }
        return null;
    }

    /**
     * Find all attributes from relations to a set of categories.
     *
     * //TODO: move to SearchEngine.
     *
     * @param categoryList TreeSet of categories.
     * @return TreeSet of Attributes
     */
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

    public static boolean deleteCategory(int categoryID) {
        return categoryList.remove(findCategoryOnID(categoryID));
    }

    public void editCategory(String categoryTitle, String categoryDescription) {
        this.objectTitle = categoryTitle;
        this.objectDescription = categoryDescription;
    }

    /**
     * Check if user input is valid for businessLogic parameters for the category.
     *
     * @param categoryTitle User input for category title.
     * @param categoryDescription User input for category description.
     * @param categoryID ID if the validation is for edit on exising category, or null if validation is for new category.
     * @return boolean true if user input is valid.
     * @throws IllegalArgumentException if user input is not valid, with message explaining what businesslogic was violated.
     */
    public static boolean validateCategoryInput(String categoryTitle, String categoryDescription, Integer categoryID) throws IllegalArgumentException {
        if (categoryTitle.isEmpty()) {
            throw new IllegalArgumentException("please fill out category-name field");
        }
        if (categoryDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out category-description field");
        }

        for (Category categoryInList : categoryList) {
            if (categoryTitle.equals(categoryInList.objectTitle)) {
                if (categoryID != null) {
                    if (categoryID != categoryInList.objectID) {
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
     * Get TreeSet of Categories for all that have IDs matching an ID in the parameter list.
     * 
     * //TODO: move to SearchEngine.
     * 
     * @param categoryChoices String list of IDs
     * @return TreeSet with all categories with matching IDs to search.
     */
    public static TreeSet<Category> getMatchingCategoriesOnIDs(ArrayList<String> categoryChoices) {
        TreeSet<Category> result = new TreeSet();
        for (Category category : categoryList) {
            if (categoryChoices.contains(Integer.toString(category.objectID))) {
                result.add(category);
            }
        }
        return result;
    }

    public boolean addProductToCategory(Product product) {
        return categoryProducts.add(product);
    }

    public boolean removeProductFromCategory(Product product) {
        return categoryProducts.remove(product);
    }

    public static TreeSet<Category> getCategoryList() {
        return categoryList;
    }

    public TreeSet<Attribute> getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(TreeSet<Attribute> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    public static int getTotalCategoryCount() {
        return categoryList.size();
    }

    public TreeSet<Product> getCategoryProducts() {
        return categoryProducts;
    }

    /**
     * Get a sorted list of up to ten categories, based and sorted on amount of category relations to products.
     * 
     * @return sorted top ten categories list.
     */
    public static List<Category> topTenCategories() {
        List<Category> categoryProductCounts = new ArrayList(categoryList);

        Collections.sort(categoryProductCounts, new Comparator<Category>() {
            @Override
            public int compare(final Category o1, final Category o2) {
                int o1Size = o1.getCategoryProducts().size();
                int o2Size = o2.getCategoryProducts().size();
                if (o1Size > o2Size) {
                    return -1;
                } else if (o1Size < o2Size) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int subListEnd = 10;
        if (categoryProductCounts.size() < 10) {
            subListEnd = categoryProductCounts.size();
        }
        List<Category> result = new ArrayList(categoryProductCounts.subList(0, subListEnd));
        return result;
    }
}
