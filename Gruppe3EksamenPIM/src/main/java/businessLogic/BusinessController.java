package businessLogic;

import static businessLogic.Category.*;
import static businessLogic.Attribute.*;
import static businessLogic.Product.*;
import static businessLogic.Bundle.*;
import static businessLogic.Distributor.*;
import factory.SystemMode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.Part;
import persistence.StorageFacade;

/**
 *
 * @author Michael N. Korsgaard
 */
public class BusinessController {

    private final String noImageFileName = "https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/no-img_vlrttr.png";
    private final StorageFacade storageFacade;
    private final SearchEngine searchEngine;

    public BusinessController(SystemMode systemMode) {
        this.storageFacade = new StorageFacade(systemMode);
        this.searchEngine = new SearchEngine();
    }

    public void setupListsFromDB() {
        TreeSet<Attribute> attributeList = storageFacade.getAttributes();
        TreeSet<Category> categoryList = storageFacade.getCategories(attributeList);
        TreeSet<Distributor> distributorList = storageFacade.getDistributors();
        TreeSet<Product> productList = storageFacade.getProducts(categoryList, distributorList);
        TreeSet<Bundle> bundleList = storageFacade.getBundles(productList);
        Attribute.setupAttributeListFromDB(attributeList);
        Distributor.setupDistributorListFromDB(distributorList);
        Category.setupCategoryListFromDB(categoryList);
        Product.setupProductListFromDB(productList);
        Bundle.setupBundleListFromDB(bundleList);
        searchEngine.setupSearchEngine(productList, categoryList, distributorList, bundleList);
    }

    public Category createNewCategory(String categoryName, String categoryDescription, ArrayList<String> attributeIdStrings) throws IllegalArgumentException {
        Category.validateCategoryInput(categoryName, categoryDescription, null);
        TreeSet<Attribute> attributeList = Attribute.getMatchingAttributesOnStringIDs(attributeIdStrings);
        Category newCategory = storageFacade.addNewCategory(categoryName, categoryDescription, attributeList);
        Category.addToCategoryList(newCategory);
        return newCategory;
    }

    public boolean deleteCategory(int categoryID) {
        storageFacade.deleteCategory(categoryID);
        TreeSet<Product> productsWithCategory = Product.findProductsOnCategoryID(categoryID);
        Category category = Category.findCategoryOnID(categoryID);
        Product.deleteCategoryFromProducts(category);
        boolean categoryWasDeleted = Category.deleteCategory(categoryID);
        Product.createAttributesFromCategories(productsWithCategory);
        storageFacade.updateProductAttributeSelections(productsWithCategory);
        return categoryWasDeleted;
    }

    public void editCategory(int categoryID, String categoryName, String categoryDescription) throws IllegalArgumentException {
        validateCategoryInput(categoryName, categoryDescription, categoryID);
        Category category = findCategoryOnID(categoryID);
        category.editCategory(categoryName, categoryDescription);
        storageFacade.editCategory(category);
    }

    public Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributorIDStrings, ArrayList<String> productCategoryIDStrings, List<Part> parts) {
        validateProductInput(productName, productDescription);
        TreeSet<Category> productCategories = Category.getMatchingCategoriesOnIDs(productCategoryIDStrings);
        TreeSet<Distributor> productDistributors = Distributor.getMatchingDistributorsOnIDs(productDistributorIDStrings);
        Product newProduct = storageFacade.addNewProduct(productName, productDescription, noImageFileName, productDistributors, productCategories);
        Product.addToProductList(newProduct);
        String picturePath = storageFacade.uploadPicture(parts);
        if (picturePath != null) {
            newProduct.setPicturePath(picturePath);
            storageFacade.updatePicturePath(newProduct.objectID, picturePath);
        }
        return newProduct;
    }

    public boolean deleteProduct(int productID) {
        storageFacade.deleteProduct(productID);
        return Product.deleteProductOnID(productID);
    }

    public void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributorIDs, HashMap<Integer, String> productAttributeValues) throws IllegalArgumentException {
        validateProductInput(productName, productDescription);
        TreeSet<Distributor> productDistributors = Distributor.getMatchingDistributorsOnIDs(productDistributorIDs);
        Product product = findProductOnID(productID);
        product.updateProductValues(productAttributeValues);
        product.editProduct(productName, productDescription, productDistributors);
        storageFacade.editProduct(product);
        storageFacade.updateProductAttributeValues(product);
    }

    public Attribute createNewAttribute(String attributeTitle) throws IllegalArgumentException {
        Attribute.validateNewAttributeTitle(attributeTitle);
        Attribute newAttribute = storageFacade.addNewAttribute(attributeTitle);
        Attribute.addToAttributeList(newAttribute);
        return newAttribute;
    }

    public void editAttribute(int attributeID, String attributeName) {
        validateNewAttributeTitle(attributeName);
        Attribute attribute = findAttributeOnID(attributeID);
        attribute.editAttribute(attributeName);
        storageFacade.editAttribute(attribute);
    }

    public boolean deleteAttribute(int attributeID) {
        storageFacade.deleteAttribute(attributeID);
        Attribute attribute = findAttributeOnID(attributeID);
        Category.deleteAttributeOnCategories(attribute);
        Product.deleteAttributeOnProducts(attribute);
        boolean attributeWasDeleted = Attribute.deleteAttribute(attributeID);
        return attributeWasDeleted;
    }

    public Distributor createNewDistributor(String distributorName, String distributorDescription) {
        Distributor.validateDistributorInput(distributorName, distributorDescription);
        Distributor newDistributor = storageFacade.addNewDistributor(distributorName, distributorDescription);
        Distributor.addToDistributorList(newDistributor);
        return newDistributor;
    }

    public boolean deleteDistributor(int distributorID) {
        storageFacade.deleteDistributor(distributorID);
        Distributor distributor = Distributor.findDistributorOnID(distributorID);
        Product.deleteDistributorFromProducts(distributor);
        return Distributor.deleteDistributor(distributorID);
    }

    public void editDistributor(int distributorID, String distributorName, String distributorDescription) throws IllegalArgumentException {
        validateDistributorInput(distributorName, distributorDescription);
        Distributor distributor = findDistributorOnID(distributorID);
        distributor.editDistributor(distributorName, distributorDescription);
        storageFacade.editDistributor(distributor);
    }

    public StorageFacade getStorageFacade() {
        return storageFacade;
    }

    public TreeSet<Category> getCategoryList() {
        return Category.getCategoryList();
    }

    public Category getCategoryFromID(int categoryID) {
        return Category.findCategoryOnID(categoryID);
    }

    public TreeSet<Product> getProductList() {
        return Product.getProductList();
    }

    public Product getProductFromID(int productID) {
        return Product.findProductOnID(productID);
    }

    public TreeSet<Attribute> getAttributeList() {
        return Attribute.getAttributeList();
    }

    public Attribute getAttributeFromID(int attributeID) {
        return Attribute.findAttributeOnID(attributeID);
    }

    public TreeSet<Distributor> getDistributorList() {
        return Distributor.getDistributorList();
    }

    public Distributor getDistributorFromID(int distributorID) {
        return Distributor.findDistributorOnID(distributorID);
    }

    public void editCategoriesToProduct(Product product, ArrayList<String> categoryChoices) {
        TreeSet<Category> categoryList = Category.getMatchingCategoriesOnIDs(categoryChoices);
        product.editProductCategories(categoryList);
        storageFacade.editCategoriesToProduct(product);
        storageFacade.updateProductAttributeSelections(product);
    }

    public void editAttributesToCategory(Category category, ArrayList<String> attributeChoices) {
        TreeSet<Attribute> attributeList = Attribute.getMatchingAttributesOnStringIDs(attributeChoices);
        category.setCategoryAttributes(attributeList);
        TreeSet<Product> productsUpdated = Product.updateCategoryAttributes(category.getObjectID());
        storageFacade.updateProductAttributeSelections(productsUpdated);
        storageFacade.editAttributeToCategory(category);
    }

    public Bundle createNewBundle(String bundleName, String bundleDescription, HashMap<Integer, Integer> productChoices) throws IllegalArgumentException {
        Bundle.validateBundleInput(bundleName, bundleDescription, null);
        HashMap<Product, Integer> productListForBundle
                = Product.getMatchingProductsOnIDsWithProductAmountConnected(productChoices);
        Bundle newBundle = storageFacade.addNewBundle(bundleName, bundleDescription, productListForBundle);
        Bundle.addToBundleList(newBundle);
        return newBundle;
    }

    public boolean deleteBundle(int bundleID) {
        storageFacade.deleteBundle(bundleID);
        Bundle bundle = Bundle.findBundleOnID(bundleID);
        Product.deleteBundleFromProducts(bundle);
        boolean bundleWasDeleted = Bundle.deleteBundle(bundleID);
        return bundleWasDeleted;
    }

    public TreeSet<Bundle> getBundleList() {
        return Bundle.getBundleList();
    }

    public Bundle getBundleFromID(int bundleID) {
        return Bundle.findBundleOnID(bundleID);
    }

    public void editBundle(int bundleID, String bundleName, String bundleDescription, HashMap<Integer, Integer> productChoices) throws IllegalArgumentException {
        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
        HashMap<Product, Integer> productListForBundle = Product.getMatchingProductsOnIDsWithProductAmountConnected(productChoices);
        Bundle bundle = findBundleOnID(bundleID);
        bundle.editBundle(bundleName, bundleDescription, productListForBundle);
        storageFacade.editBundle(bundle);
    }

    public void bulkEdit(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues) {
        Attribute.bulkEditProducts(productIDs, newAttributeValues);
        storageFacade.bulkEditOnCategoryID(productIDs, newAttributeValues);
    }

    public TreeSet<Product> findProductsOnCategoryID(int categoryID) {
        return Product.findProductsOnCategoryID(categoryID);
    }

    /**
     * !! WORKING PROGRESS !!
     *
     * @author Michael
     * @return
     */
    public HashMap<String, Object> getSystemStatistics() {
        HashMap<String, Object> statistics = new HashMap();
        statistics.put("productCount", Product.getTotalProductCount());
        statistics.put("categoryCount", Category.getTotalCategoryCount());
        statistics.put("distributorCount", Distributor.getTotalDistributorCount());
        statistics.put("bundleCount", Bundle.getTotalBundleCount());
        statistics.put("topTenCategories", Category.topTenCategories());
        statistics.put("topTenDistributors", Distributor.topTenDistributors());
        statistics.put("topTenBundles", Bundle.topTenBundles());
        return statistics;
    }

    public TreeSet<Product> searchProduct(String searchString) {
        return searchEngine.simpleProductSearch(searchString);
    }
    
    public TreeSet<Object> advancedSearch(String searchString, String searchOnObject, String bundleFilter, String categoryFilter, String distributorFilter, String productFilter) {
        HashMap<String, String> filterValues = SearchEngine.makeFilterMap(bundleFilter, categoryFilter, distributorFilter, productFilter);
        return searchEngine.advancedSearch(searchString, searchOnObject, filterValues);
    }

    public File getJsonFileAllProducts() {
        TreeSet<Product> productList = Product.getProductList();
        return storageFacade.getJsonFile(productList);
    }
    
    public File getJsonFileCategoryProducts(int categoryID) {
        Category category = Category.findCategoryOnID(categoryID);
        TreeSet<Product> productList = category.getCategoryProducts();
        return storageFacade.getJsonFile(productList);
    }

}
