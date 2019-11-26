package persistence;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import factory.SystemMode;
import java.util.ArrayList;
import persistence.mappers.AttributeMapper;
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
    private DB database;

    public StorageFacade(SystemMode systemMode) {
        this.database = new DB(systemMode);
        this.categoryMapper = new CategoryMapper(database);
        this.productMapper = new ProductMapper(database);
        this.attributeMapper = new AttributeMapper(database);
        this.distributorMapper = new DistributorMapper(database);
    }

    public DB getDatabase() {
        return database;
    }

    public ArrayList<Product> getProducts(ArrayList<Category> categoryList, ArrayList<Distributor> distributorList) {
        return productMapper.getProducts(categoryList, distributorList);
    }

    public ArrayList<Category> getCategories(ArrayList<Attribute> attributeList) {
        return categoryMapper.getCategories(attributeList);
    }

    public ArrayList<Attribute> getAttributes() {
        return attributeMapper.getAttributes();
    }
    
    public ArrayList<Distributor> getDistributors() {
        return distributorMapper.getDistributors();
    }
    
    public Category addNewCategory(String categoryName, String categoryDescription) {
        return categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    public void deleteCategory(int categoryID) {
        categoryMapper.deleteCategory(categoryID);
    }

    public void editCategory(Category category) {
        categoryMapper.editCategory(category);
    }

    public Product addNewProduct(String productName, String productDescription, String noImageFileName) {
        return productMapper.addNewProduct(productName, productDescription, noImageFileName);
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

    public void updateProductAttributeSelections(ArrayList<Product> productsUpdated) {
        for (Product product : productsUpdated) {
            attributeMapper.updateProductAttributeSelections(product);
        }
    }

    public void updateProductAttributeValues(Product product) {
        attributeMapper.updateProductAttributeValues(product);
    }
}
