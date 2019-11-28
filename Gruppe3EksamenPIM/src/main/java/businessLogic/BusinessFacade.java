package businessLogic;

import static businessLogic.Attribute.findAttributeOnID;
import static businessLogic.Attribute.validateNewAttributeTitle;
import static businessLogic.Bundle.findBundleOnID;
import static businessLogic.Bundle.validateBundleInput;
import static businessLogic.Category.findCategoryOnID;
import static businessLogic.Category.validateCategoryInput;
import static businessLogic.Product.findProductOnID;
import static businessLogic.Product.validateProductInput;
import static businessLogic.Distributor.findDistributorOnID;
import static businessLogic.Distributor.validateDistributorInput;
import factory.SystemMode;
import java.util.ArrayList;
import java.util.HashMap;
import persistence.StorageFacade;

/**
 *
 * @author Michael N. Korsgaard
 */
public class BusinessFacade {

    private StorageFacade storageFacade;
    private final String noImageFileName = "no-img.png";

    public BusinessFacade(SystemMode systemMode) {
        storageFacade = new StorageFacade(systemMode);
    }

    public void setupListsFromDB() {
        ArrayList<Attribute> attributeList = storageFacade.getAttributes();
        ArrayList<Category> categoryList = storageFacade.getCategories(attributeList);
        ArrayList<Distributor> distributorList = storageFacade.getDistributors();
        ArrayList<Product> productList = storageFacade.getProducts(categoryList, distributorList);
        ArrayList<Bundle> bundleList = storageFacade.getBundles(productList);
        Attribute.setupAttributeListFromDB(attributeList);
        Distributor.setupDistributorListFromDB(distributorList);
        Category.setupCategoryListFromDB(categoryList);
        Product.setupProductListFromDB(productList);
        Bundle.setupBundleListFromDB(bundleList);
    }

    public Category createNewCategory(String categoryName, String categoryDescription) throws IllegalArgumentException {
        Category.validateCategoryInput(categoryName, categoryDescription, null);
        Category newCategory = storageFacade.addNewCategory(categoryName, categoryDescription);
        Category.addToCategoryList(newCategory);
        return newCategory;
    }

    public boolean deleteCategory(int categoryID) {
        storageFacade.deleteCategory(categoryID);
        ArrayList<Product> productsWithCategory = Product.findProductsOnCategoryID(categoryID);
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

    public Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributorStrings) {
        validateProductInput(productName, productDescription);
        ArrayList<Distributor> productDistributors = Distributor.getMatchingDistributorsOnIDs(productDistributorStrings);
        Product newProduct = storageFacade.addNewProduct(productName, productDescription, noImageFileName, productDistributors);
        Product.addToProductList(newProduct);
        return newProduct;
    }

    public boolean deleteProduct(int productID) {
        storageFacade.deleteProduct(productID);
        return Product.deleteProductOnID(productID);
    }

    public void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributorIDs, HashMap<Integer, String> productAttributeValues) throws IllegalArgumentException {
        validateProductInput(productName, productDescription);
        ArrayList<Distributor> productDistributors = Distributor.getMatchingDistributorsOnIDs(productDistributorIDs);
        Product product = findProductOnID(productID);
        product.updateProductValues(productAttributeValues);
        product.editProduct(productName, productDescription, productDistributors);
        storageFacade.editProduct(product);
        storageFacade.updateProductAttributeValues(product);
    }

    public void updatePicturePath(int productID, String picturePath) {
        Product product = Product.findProductOnID(productID);
        product.setPicturePath(picturePath);
        storageFacade.updatePicturePath(productID, picturePath);
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
        Attribute.findAttributeOnID(attributeID);
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
        Distributor.findDistributorOnID(distributorID);
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

    public ArrayList<Category> getCategoryList() {
        return Category.getCategoryList();
    }

    public Category getCategoryFromID(int categoryID) {
        return Category.findCategoryOnID(categoryID);
    }

    public ArrayList<Product> getProductList() {
        return Product.getProductList();
    }

    public Product getProductFromID(int productID) {
        return Product.findProductOnID(productID);
    }

    public ArrayList<Attribute> getAttributeList() {
        return Attribute.getAttributeList();
    }

    public Attribute getAttributeFromID(int attributeID) {
        return Attribute.findAttributeOnID(attributeID);
    }
    
    public ArrayList<Distributor> getDistributorList() {
        return Distributor.getDistributorList();
    }
    
    public Distributor getDistributorFromID(int distributorID) {
        return Distributor.findDistributorOnID(distributorID);
    }

    public void editCategoriesToProduct(Product product, ArrayList<String> categoryChoices) {
        ArrayList<Category> categoryList = Category.getMatchingCategoriesOnIDs(categoryChoices);
        product.editProductCategories(categoryList);
        storageFacade.editCategoriesToProduct(product);
        storageFacade.updateProductAttributeSelections(product);
    }

    public void editAttributesToCategory(Category category, ArrayList<String> attributeChoices) {
        ArrayList<Attribute> attributeList = Attribute.getMatchingAttributesOnStringIDs(attributeChoices);
        category.setCategoryAttributes(attributeList);
        ArrayList<Product> productsUpdated = Product.updateCategoryAttributes(category.getCategoryID());
        storageFacade.updateProductAttributeSelections(productsUpdated);
        storageFacade.editAttributeToCategory(category);
    }
    
    public Bundle createNewBundle(String bundleName, String bundleDescription, HashMap<Integer, Integer> productChoices) throws IllegalArgumentException {
        Bundle.validateBundleInput(bundleName, bundleDescription, null);
        HashMap<Product, Integer> productListForBundle = Product.getMatchingProductsOnIDs(productChoices);
        Bundle newBundle = storageFacade.addNewBundle(bundleName, bundleDescription, productListForBundle);
        Bundle.addToBundleList(newBundle);
        return newBundle;
    }
    
    public boolean deleteBundle(int bundleID) {
        storageFacade.deleteBundle(bundleID);
        ArrayList<Product> productsWithBundle = Product.findProductsOnCategoryID(bundleID);
        Bundle bundle = Bundle.findBundleOnID(bundleID);
        Product.deleteBundleFromProducts(bundle);
        boolean bundleWasDeleted = Bundle.deleteBundle(bundleID);
        return bundleWasDeleted;
    }
    
    public ArrayList<Bundle> getBundleList() {
        return Bundle.getBundleList();
    }
    
    public Bundle getBundleFromID(int bundleID) {
        return Bundle.findBundleOnID(bundleID);
    }
    
    public void editBundle(int bundleID, String bundleName, String bundleDescription, HashMap<Integer, Integer> productChoices) throws IllegalArgumentException {
        Bundle.validateBundleInput(bundleName, bundleDescription, bundleID);
        HashMap<Product, Integer> productListForBundle = Product.getMatchingProductsOnIDs(productChoices);
        Bundle bundle = findBundleOnID(bundleID);
        bundle.editBundle(bundleName, bundleDescription, productListForBundle);
        storageFacade.editBundle(bundle);
    }
    
    public void BulkEdit(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues){
        Attribute.bulkEditProducts(productIDs, newAttributeValues);
        storageFacade.bulkEditOnCategoryID(productIDs, newAttributeValues);
    }
    
}