/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import static businessLogic.Product.findProductOnID;
import static businessLogic.Product.validateProductInput;
import factory.SystemMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        Product.setupProductListFromDB(storageFacade.getProducts());
        Category.setupCategoryListFromDB(storageFacade.getCategories());
    }

    public Category createNewCategory(String categoryName, String categoryDescription) throws IllegalArgumentException {
        Category.validateCategoryInput(categoryName, categoryDescription);
        Category newCategory = storageFacade.addNewCategory(categoryName, categoryDescription);
        Category.addToCategoryList(newCategory);
        return newCategory;
    }

    public boolean deleteCategory(int categoryID) {
        storageFacade.deleteCategory(categoryID);
        return Category.deleteCategory(categoryID);
    }

    public Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributors) {
        //Remove all empty fields from distributors
        productDistributors.removeAll(Arrays.asList("", null));

        validateProductInput(productName, productDescription, productDistributors);
        Product newProduct = storageFacade.addNewProduct(productName, productDescription, noImageFileName, productDistributors);
        Product.addToProductList(newProduct);
        return newProduct;
    }

    public boolean deleteProduct(int productID) {
        storageFacade.deleteProduct(productID);
        return Product.deleteProductOnID(productID);
    }

    public void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributors) {
        //Remove all empty fields from distributors
        productDistributors.removeAll(Arrays.asList("", null));

        Product product = findProductOnID(productID);
        product.editProduct(productName, productDescription, productDistributors);
        storageFacade.editProduct(product);
    }

    public void updatePicturePath(int productID, String picturePath) {
        Product product = Product.findProductOnID(productID);
        product.setPicturePath(picturePath);
        storageFacade.updatePicturePath(productID, picturePath);
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
}
