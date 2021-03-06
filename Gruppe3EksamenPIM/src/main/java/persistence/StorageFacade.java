package persistence;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import factory.SystemMode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.Part;
import persistence.Json.JsonHandler;
import persistence.mappers.AttributeMapper;
import persistence.mappers.BundleMapper;
import persistence.mappers.CategoryMapper;
import persistence.mappers.DistributorMapper;
import persistence.mappers.PictureMapper;
import persistence.mappers.ProductMapper;

public class StorageFacade {

    private SQLDatabase sqlDatabase;
    private CloudinaryDatabase cloudinaryDatabase;
    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;
    private AttributeMapper attributeMapper;
    private DistributorMapper distributorMapper;
    private BundleMapper bundleMapper;
    private PictureMapper pictureMapper;
    private JsonHandler jsonHandler;

    public StorageFacade(SystemMode systemMode) {
        this.sqlDatabase = new SQLDatabase(systemMode);
        this.cloudinaryDatabase = new CloudinaryDatabase();
        this.jsonHandler = new JsonHandler();
        this.categoryMapper = new CategoryMapper(sqlDatabase);
        this.productMapper = new ProductMapper(sqlDatabase);
        this.attributeMapper = new AttributeMapper(sqlDatabase);
        this.distributorMapper = new DistributorMapper(sqlDatabase);
        this.bundleMapper = new BundleMapper(sqlDatabase);
        this.pictureMapper = new PictureMapper(cloudinaryDatabase);
    }

    public SQLDatabase getSqlDatabase() {
        return sqlDatabase;
    }

    public TreeSet<Product> getProducts(TreeSet<Category> categoryList, TreeSet<Distributor> distributorList) {
        return productMapper.getProducts(categoryList, distributorList);
    }

    public Category addNewCategory(String categoryName, String categoryDescription, TreeSet<Attribute> attributeList) {
        return categoryMapper.addNewCategory(categoryName, categoryDescription, attributeList);
    }

    public TreeSet<Category> getCategories(TreeSet<Attribute> attributeList) {
        return categoryMapper.getCategories(attributeList);
    }

    public TreeSet<Attribute> getAttributes() {
        return attributeMapper.getAttributes();
    }

    public TreeSet<Distributor> getDistributors() {
        return distributorMapper.getDistributors();
    }

    public void deleteCategory(int categoryID) {
        categoryMapper.deleteCategory(categoryID);
    }

    public void editCategory(Category category) {
        categoryMapper.editCategory(category);
    }

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
        return attributeMapper.bulkEditOnProductIDs(productIDs, newAttributeValues);
    }

    public String uploadPicture(List<Part> requestParts) {
        return pictureMapper.uploadProductPicture(requestParts);
    }

    public File getJsonFile(TreeSet<Product> productList) {
        return jsonHandler.createJsonFileFromProductList(productList);
    }
}
