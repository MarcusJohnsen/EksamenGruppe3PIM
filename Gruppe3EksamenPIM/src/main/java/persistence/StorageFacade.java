package persistence;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import factory.SystemMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import persistence.mappers.AttributeMapper;
import persistence.mappers.BundleMapper;
import persistence.mappers.CategoryMapper;
import persistence.mappers.DistributorMapper;
import persistence.mappers.ProductMapper;

/**
 *
 * @author Michael N. Korsgaard
 */
public class StorageFacade {

    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;
    private AttributeMapper attributeMapper;
    private DistributorMapper distributorMapper;
    private BundleMapper bundleMapper;
    private DB database;

    public StorageFacade(SystemMode systemMode) {
        this.database = new DB(systemMode);
        this.categoryMapper = new CategoryMapper(database);
        this.productMapper = new ProductMapper(database);
        this.attributeMapper = new AttributeMapper(database);
        this.distributorMapper = new DistributorMapper(database);
        this.bundleMapper = new BundleMapper(database);
    }

    public DB getDatabase() {
        return database;
    }

    public TreeSet<Product> getProducts(TreeSet<Category> categoryList, TreeSet<Distributor> distributorList) {
        return productMapper.getProducts(categoryList, distributorList);
    }

    /**
     *
     * @param categoryName
     * @param categoryDescription Adds new category object and stores it in the
     * Database.
     * @return new category object
     *
     */
    public Category addNewCategory(String categoryName, String categoryDescription, TreeSet<Attribute> attributeList) {
        return categoryMapper.addNewCategory(categoryName, categoryDescription, attributeList);
    }

    /**
     *
     * @param attributeList
     * @return attributeList
     */
    public TreeSet<Category> getCategories(TreeSet<Attribute> attributeList) {
        return categoryMapper.getCategories(attributeList);
    }

    /**
     *
     * @return attribute objects
     */
    public TreeSet<Attribute> getAttributes() {
        return attributeMapper.getAttributes();
    }

    public TreeSet<Distributor> getDistributors() {
        return distributorMapper.getDistributors();
    }

    /**
     *
     * @param categoryID is used to find and delete the category object
     */
    public void deleteCategory(int categoryID) {
        categoryMapper.deleteCategory(categoryID);
    }

    /**
     * @param category edits the category object.
     */
    public void editCategory(Category category) {
        categoryMapper.editCategory(category);
    }

    /**
     *
     * @param productName
     * @param productDescription
     * @param noImageFileName
     * @return
     */
    public Product addNewProduct(String productName, String productDescription, String noImageFileName, TreeSet<Distributor> productDistributors, TreeSet<Category> productCategories) {
        return productMapper.addNewProduct(productName, productDescription, noImageFileName, productDistributors, productCategories);
    }

    public void deleteProduct(int productID) {
        productMapper.deleteProduct(productID);
    }

    public void updatePicturePath(int productID, String picturePath) {
        productMapper.updatePicturePath(productID, picturePath);
    }

    public void editProduct(Product product) {
        productMapper.editProduct(product);
    }

    public Attribute addNewAttribute(String AttributeName) {
        return attributeMapper.addNewAttribute(AttributeName);
    }
    
    public void editAttribute(Attribute attribute) {
        attributeMapper.editAttribute(attribute);
    }
    
    public void deleteAttribute(int attributeID) {
        attributeMapper.deleteAttribute(attributeID);
    }

    public void editCategoriesToProduct(Product product) {
        productMapper.editProductCategories(product);
    }

    public void editAttributeToCategory(Category category) {
        categoryMapper.editAttributeToCategories(category);
    }

    public Distributor addNewDistributor(String distributorName, String distributorDescription) {
        return distributorMapper.addNewDistributor(distributorName, distributorDescription);
    }

    public void deleteDistributor(int distributorID) {
        distributorMapper.deleteDistributor(distributorID);
    }

    public void editDistributor(Distributor distributor) {
        distributorMapper.editDistributor(distributor);
    }

    public void updateProductAttributeSelections(Product product) {
        attributeMapper.updateProductAttributeSelections(product);
    }

    public void updateProductAttributeSelections(TreeSet<Product> productsUpdated) {
        for (Product product : productsUpdated) {
            attributeMapper.updateProductAttributeSelections(product);
        }
    }

    public void updateProductAttributeValues(Product product) {
        attributeMapper.updateProductAttributeValues(product);
    }

    public Bundle addNewBundle(String bundleName, String bundleDescription, HashMap<Product, Integer> productListForBundle) {
        return bundleMapper.addNewBundle(bundleName, bundleDescription, productListForBundle);
    }

    public void deleteBundle(int bundleID) {
        bundleMapper.deleteBundle(bundleID);
    }

    public void editBundle(Bundle bundle) {
        bundleMapper.editBundle(bundle);
    }

    public TreeSet<Bundle> getBundles(TreeSet<Product> productList) {
        return bundleMapper.getBundle(productList);
    }
    
    public int bulkEditOnCategoryID(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues) {
        return attributeMapper.bulkEditOnCategoryID(productIDs, newAttributeValues);
    }
}