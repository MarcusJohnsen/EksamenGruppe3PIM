/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import businessLogic.Attribute;
import businessLogic.Category;
import businessLogic.Product;
import factory.SystemMode;
import java.sql.SQLException;
import java.util.ArrayList;
import persistence.mappers.AttributeMapper;
import persistence.mappers.CategoryMapper;
import persistence.mappers.ProductMapper;

/**
 *
 * @author Michael N. Korsgaard
 */
public class StorageFacade {

    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;
    private AttributeMapper attributeMapper;
    private DB database;

    public StorageFacade(SystemMode systemMode) {
        this.database = new DB(systemMode);
        this.categoryMapper = new CategoryMapper(database);
        this.productMapper = new ProductMapper(database);
        this.attributeMapper = new AttributeMapper(database);
    }

    public DB getDatabase() {
        return database;
    }

    public Category addNewCategory(String categoryName, String categoryDescription) {
        return categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    public ArrayList<Product> getProducts(ArrayList<Category> categoryList) {
        return productMapper.getProducts(categoryList);
    }

    public ArrayList<Category> getCategories() {
        return categoryMapper.getCategories();
    }

    public void deleteCategory(int categoryID) {
        categoryMapper.deleteCategory(categoryID);
    }
    
    public void editCategory(Category category) {
        categoryMapper.editCategory(category);
    }

    public Product addNewProduct(String productName, String productDescription, String noImageFileName, ArrayList<String> productDistributors) {
        return productMapper.addNewProduct(productName, productDescription, noImageFileName, productDistributors);
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
        productMapper.editCategoriesToProduct(product);
    }

}
