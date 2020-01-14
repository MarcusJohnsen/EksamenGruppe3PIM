package businessLogic;

import factory.UserInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Product extends PIMObject {

    private String picturePath;
    private TreeSet<Distributor> productDistributors;
    private TreeSet<Category> productCategories;
    private TreeSet<Attribute> productAttributes;
    private TreeSet<Bundle> productBundle;

    private static TreeSet<Product> productList = new TreeSet();

    public Product(int productID, String productTitle, String productDescription, String picturePath, TreeSet<Distributor> productDistributors, TreeSet<Category> productCategories) {
        super(productID, productTitle, productDescription);
        this.picturePath = picturePath;
        if (productDistributors != null) {
            this.productDistributors = productDistributors;
        } else {
            this.productDistributors = new TreeSet();
        }

        if (productCategories != null) {
            this.productCategories = productCategories;
            createAttributesFromCategories();
        } else {
            this.productAttributes = new TreeSet();
            this.productCategories = new TreeSet();
        }

        this.productBundle = new TreeSet();
        addProductRelationToCategoriesAndDistributors();
    }

    /**
     * Establishes a double link between the product and each category & distributor relation.
     */
    private void addProductRelationToCategoriesAndDistributors() {

        for (Distributor distributor : productDistributors) {
            distributor.addProductToDistributor(this);
        }

        for (Category category : productCategories) {
            category.addProductToCategory(this);
        }
    }

    public static void deleteCategoryFromProducts(Category category) {
        for (Product product : productList) {
            product.productCategories.remove(category);
        }
    }

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
                    productAttribute.getAttributeValues().remove(this.objectID);
                }
            }
        }

        for (Attribute attribute : attributeSet) {
            if (attribute.getAttributeValueForID(this.objectID) == null) {
                attribute.insertValueIntoAttribute("", this.objectID);
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
     * Takes a HashMap with integer keys for product IDs, and replaces the keys with the products the keys matches.
     *
     * @param productChoices HashMap with productIDs as int keys, with values as int amounts of each product.
     *
     * @return HashMap with keys for product IDs replaced with the products.
     */
    public static HashMap<Product, Integer> getMatchingProductsOnIDsWithProductAmountConnected(HashMap<Integer, Integer> productChoices) {
        HashMap<Product, Integer> result = new HashMap();
        if (productChoices != null) {
            for (Product product : productList) {
                if (productChoices.get(product.objectID) != null) {
                    result.put(product, productChoices.get(product.objectID));
                }
            }
        }
        return result;
    }

    public static void createAttributesFromCategories(TreeSet<Product> products) {
        for (Product product : products) {
            product.createAttributesFromCategories();
        }
    }

    public static TreeSet<Product> updateCategoryAttributes(int categoryID) {
        TreeSet<Product> result = findProductsOnCategoryID(categoryID);
        for (Product productsNeedingUpdatedAttribute : result) {
            productsNeedingUpdatedAttribute.createAttributesFromCategories();
        }
        return result;
    }

    /**
     * Get a TreeSet of Products that have a relation to a category with the matching ID.
     *
     * //TODO: Move to SearchEngine.
     *
     * @param categoryID ID for category relation for products.
     * @return TreeSet of Products, empty if no products with relations to the category with ID was found.
     */
    public static TreeSet<Product> findProductsOnCategoryID(int categoryID) {
        TreeSet<Product> result = new TreeSet();
        for (Product product : productList) {
            for (Category productCategory : product.getProductCategories()) {
                if (productCategory.objectID == categoryID) {
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

    /**
     * Get a TreeSet of Products that have a relation to a bundle with the matching ID.
     *
     * //TODO: Move to SearchEngine.
     *
     * @param categoryID ID for bundle relation for products.
     * @return TreeSet of Products, empty if no products with relations to the bundle with ID was found.
     */
    public static TreeSet<Product> findProductsOnBundleID(int bundleID) {
        TreeSet<Product> result = new TreeSet();
        for (Product product : productList) {
            for (Bundle productBundle : product.getProductBundle()) {
                if (productBundle.getObjectID() == bundleID) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    public static void setupProductListFromDB(TreeSet<Product> productListFromDB) {
        productList = productListFromDB;
    }

    public static void addToProductList(Product product) {
        productList.add(product);
    }

    /**
     * Delete a product with a given ID, after removing all product relations to other PIM objects.
     *
     * @param productID ID for product to be deleted
     * @return boolean true if product was removed from static productList.
     */
    public static boolean deleteProductOnID(int productID) {
        Product product = findProductOnID(productID);
        for (Distributor distributor : product.productDistributors) {
            distributor.removeProductFromDistributor(product);
        }
        for (Category category : product.productCategories) {
            category.removeProductFromCategory(product);
        }
        for (Attribute attribute : product.productAttributes) {
            attribute.removeValueFromAttribute(productID);
        }
        for (Bundle bundle : product.productBundle) {
            bundle.removeProductFromBundle(product);
        }
        return productList.remove(product);
    }

    /**
     * Traversing through the productList and using the productID param to find product object.
     *
     * @param productID ID for desired product.
     * @return The found product object, or null if not found
     */
    public static Product findProductOnID(int productID) {
        for (Product product : productList) {
            if (product.objectID == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Traversing through the productList and using the productID param to find product object.
     *
     * @param productID IDs for desired products
     * @return TreeSet for products found with matching IDs, or empty if none were found.
     */
    public static TreeSet<Product> findProductsOnIDs(ArrayList<Integer> productIDs) {
        TreeSet<Product> result = new TreeSet();
        for (Integer productID : productIDs) {
            Product product = findProductOnID(productID);
            if (product != null) {
                result.add(product);
            }
        }
        return result;
    }

    public void editProduct(String productTitle, String productDescription, TreeSet<Distributor> productDistributors) {
        this.objectTitle = productTitle;
        this.objectDescription = productDescription;
        this.productDistributors = productDistributors;
    }

    public void updateProductValues(HashMap<Integer, String> productAttributeValues) {
        for (Attribute productAttribute : this.productAttributes) {
            int attributeID = productAttribute.objectID;
            String newValue = productAttributeValues.get(attributeID);
            productAttribute.insertValueIntoAttribute(newValue, this.objectID);
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
    public static boolean validateProductInput(String productName, String productDescription) throws UserInputException {
        //Remove all empty fields from distributors
        //productDistributors.removeAll(Arrays.asList("", null));

        if (productName.isEmpty()) {
            throw new UserInputException("please fill out product-name field");
        }
        if (productDescription.isEmpty()) {
            throw new UserInputException("please fill out product-description field");
        }
        return true;
    }

    public void editProductCategories(TreeSet<Category> productCategories) {
        this.productCategories = productCategories;
        createAttributesFromCategories();
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

    public boolean addBundleToProduct(Bundle bundle) {
        return productBundle.add(bundle);
    }

    public void removeBundleFromProduct(Bundle bundle) {
        this.productBundle.remove(bundle);
    }

    public static int getTotalProductCount() {
        return productList.size();
    }

}
