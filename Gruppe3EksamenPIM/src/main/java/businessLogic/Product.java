package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Product implements Comparable<Product> {

    private int productID;
    private String name;
    private String description;
    private String picturePath;
    private TreeSet<Distributor> productDistributors;
    private TreeSet<Category> productCategories;
    private TreeSet<Attribute> productAttributes;
    private TreeSet<Bundle> productBundle;

    private static TreeSet<Product> productList = new TreeSet();

    
    /**
     * Constructor
     * 
     * @param productID  int unique, not null auto_increment
     * @param name String, not null
     * @param description String, not null
     * @param picturePath String
     * @param productDistributors TreeSet of distributor-Objects
     * @param productCategories  TreeSet of category Objects
     */
    
    public Product(int productID, String name, String description, String picturePath, TreeSet<Distributor> productDistributors, TreeSet<Category> productCategories) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.productDistributors = productDistributors;
        if (productCategories != null) {
            this.productCategories = productCategories;
            createAttributesFromCategories();
        } else {
            this.productAttributes = new TreeSet();
            this.productCategories = new TreeSet();
        }
    }

     /**
     * Creates new category object and stores it in the database. Get's the
     * CategoryID from the database, and returns the Category object with the
     * new ID
     *
     * @param categoryName String with max length of 255 characters
     * @param categoryDescription String with max length of 2550 characters
     *
     * @return the category object with an ID given from the database.
     * @throws IllegalArgumentException stating that category object could not
     * be inserted, due to a sql error with the database.
     */
    
    /**
     * Traverses through the productList in order to find certain category object, 
     * which is deleted once it is found.
     
     * @param category category-Object stored in a TreeSet
     */
    public static void deleteCategoryFromProducts(Category category) {
        for (Product product : productList) {
            product.productCategories.remove(category);
        }
    }
    
    
    /**
     * Traverses through the productList in order to find certain distributor object,
     * which is deleted once it is found
     * 
     * @param distributor distributor-object stored in a TreeSet
     */
    
    public static void deleteDistributorFromProducts(Distributor distributor) {
        for (Product product : productList) {
            product.productDistributors.remove(distributor);
        }
    }

    /**
     * Traverses the productCategories-List and adds all the unique categoryAttributes to new TreeSet.
     */
    private void createAttributesFromCategories() {
        TreeSet<Attribute> attributeSet = new TreeSet();
        for (Category productCategory : productCategories) {
            attributeSet.addAll(productCategory.getCategoryAttributes());
        }

        if (productAttributes != null) {
            for (Attribute productAttribute : productAttributes) {
                if (!attributeSet.contains(productAttribute)) {
                    productAttribute.getAttributeValues().remove(productID);
                }
            }
        }

        for (Attribute attribute : attributeSet) {
            if (attribute.getAttributeValueForID(productID) == null) {
                attribute.insertValueIntoAttribute("", productID);
            }
        }
        this.productAttributes = attributeSet;
    }
    
    public static void deleteAttributeOnProducts(Attribute attribute) {
        for (Product product : productList) {
            product.productAttributes.remove(attribute);
        }
    }

    
    /**
     * Takes a HashMap of productChoices with key integer and 
     * 
     * @param productChoices
     * 
     * @return 
     */
    
    public static HashMap<Product, Integer> getMatchingProductsOnIDsWithProductAmountConnected(HashMap<Integer, Integer> productChoices) {
        HashMap<Product, Integer> result = new HashMap();
        if (productChoices != null) {
            for (Product product : productList) {
                if (productChoices.get(product.getProductID()) != null) {
                    result.put(product, productChoices.get(product.getProductID()));
                }
            }
        }
        return result;
    }

    /**
     *
     * @param products Traverses the products-List and calls the createAttributesFromCategories() method in order to add the productAttributes to the product object.
     *
     */
    public static void createAttributesFromCategories(TreeSet<Product> products) {
        for (Product product : products) {
            product.createAttributesFromCategories();
        }
    }

    /**
     *
     * @param categoryID The returned product object from the called method findProductsOnCategoryID(categoryID) is used to call the method createAttributesFromCategories() in order to add/update the productAttributes to the product object.
     * @return the updated product object.
     */
    public static TreeSet<Product> updateCategoryAttributes(int categoryID) {
        TreeSet<Product> result = findProductsOnCategoryID(categoryID);
        for (Product productsNeedingUpdatedAttribute : result) {
            productsNeedingUpdatedAttribute.createAttributesFromCategories();
        }
        return result;
    }

    /**
     *
     * @param categoryID Traverses the productList with given categoryID parameter. If the parameter categoryId matches the categoryId for the product, the product is added to the result-List.
     * @return The result-List
     */
    public static TreeSet<Product> findProductsOnCategoryID(int categoryID) {
        TreeSet<Product> result = new TreeSet();
        for (Product product : productList) {
            for (Category productCategory : product.getProductCategories()) {
                if (productCategory.getCategoryID() == categoryID) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    public static void deleteBundleFromProducts(Bundle bundle) {
        for (Product product : productList) {
            product.productCategories.remove(bundle);
        }
    }

    public static TreeSet<Product> findProductsOnBundleID(int bundleID) {
        TreeSet<Product> result = new TreeSet();
        for (Product product : productList) {
            for (Bundle productBundle : product.getProductBundle()) {
                if (productBundle.getBundleID() == bundleID) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param productListFromDB Gets the list of product objects from the DataBase and stores them in a list.
     */
    public static void setupProductListFromDB(TreeSet<Product> productListFromDB) {
        productList = productListFromDB;
    }

    /**
     * @param product product object is added to the productList.
     */
    public static void addToProductList(Product product) {
        productList.add(product);
    }

    /**
     *
     * @param productID Is used to call the method findProductOnID(productID). The returned product object is then deleted.
     * @return The new productList.
     */
    public static boolean deleteProductOnID(int productID) {
        return productList.remove(findProductOnID(productID));
    }

    /**
     *
     * @param productID Traversing through the productList and using the productID param to find product object.
     * @return The found product object.
     */
    public static Product findProductOnID(int productID) {
        for (Product product : productList) {
            if (product.productID == productID) {
                return product;
            }
        }
        return null;
    }

    public static TreeSet<Product> findProductsOnIDs(ArrayList<Integer> productIDs) {
        TreeSet<Product> result = new TreeSet();
        for (Integer productID : productIDs) {
            result.add(findProductOnID(productID));
        }
        result.removeAll(Arrays.asList(new Product[]{null}));
        return result;
    }

    /**
     *
     * @param name Is edited.
     * @param description Is edited.
     */
    public void editProduct(String name, String description, TreeSet<Distributor> productDistributors) {
        this.name = name;
        this.description = description;
        this.productDistributors = productDistributors;
    }

    /**
     * @param productAttributeValues Mangler
     *
     */
    public void updateProductValues(HashMap<Integer, String> productAttributeValues) {
        for (Attribute productAttribute : this.productAttributes) {
            int attributeID = productAttribute.getAttributeID();
            String newValue = productAttributeValues.get(attributeID);
            productAttribute.insertValueIntoAttribute(newValue, this.productID);
        }
    }

    /**
     * Validating productInput by checking for empty fields.
     *
     * @param productName - Must not be empty.
     * @param productDescription - Must not be empty.
     * @return Boolean true if name and description don't have empty field-values.
     * @throws IllegalArgumentException if returned boolean is false.
     */
    public static boolean validateProductInput(String productName, String productDescription) throws IllegalArgumentException {
        //Remove all empty fields from distributors
        //productDistributors.removeAll(Arrays.asList("", null));

        if (productName.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (productDescription.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }
        return true;
    }

    /**
     * @param productCategories Calls the createAttributesFromCategories() method in order to edit the productCategories.
     */
    public void editProductCategories(TreeSet<Category> productCategories) {
        this.productCategories = productCategories;
        createAttributesFromCategories();
    }

    public void editProductDistributors(TreeSet<Distributor> productDistributors) {
        this.productDistributors = productDistributors;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public static TreeSet<Product> getProductList() {
        return productList;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public TreeSet<Category> getProductCategories() {
        return productCategories;
    }

    public TreeSet<Attribute> getProductAttributes() {
        return productAttributes;
    }

    public TreeSet<Distributor> getProductDistributors() {
        return productDistributors;
    }

    public TreeSet<Bundle> getProductBundle() {
        return productBundle;
    }
    
    public static int getTotalProductCount(){
        return productList.size();
    }

    @Override
    public int compareTo(Product otherProduct) {

        int thisID = this.productID;
        int otherID = otherProduct.productID;
        return thisID - otherID;

    }
}
