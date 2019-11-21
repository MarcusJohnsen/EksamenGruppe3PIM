/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import static businessLogic.Category.findCategoryOnID;
import static businessLogic.Category.validateCategoryInput;
import static businessLogic.Product.findProductOnID;
import static businessLogic.Product.validateProductInput;
import factory.SystemMode;
import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<Product> productList = storageFacade.getProducts(categoryList);
        Category.setupCategoryListFromDB(categoryList);
        Product.setupProductListFromDB(productList);
        Attribute.setupAttributeListFromDB(attributeList);
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

    public Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributors) {
        validateProductInput(productName, productDescription, productDistributors);
        Product newProduct = storageFacade.addNewProduct(productName, productDescription, noImageFileName, productDistributors);
        Product.addToProductList(newProduct);
        return newProduct;
    }

    public boolean deleteProduct(int productID) {
        storageFacade.deleteProduct(productID);
        return Product.deleteProductOnID(productID);
    }

    public void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributors, HashMap<Integer, String> productAttributeValues) throws IllegalArgumentException {
        validateProductInput(productName, productDescription, productDistributors);
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

    public void editCategoriesToProduct(Product product, ArrayList<String> categoryChoices) {
        ArrayList<Category> categoryList = Category.getMatchingCategoriesOnIDs(categoryChoices);
        product.editProductCategories(categoryList);
        storageFacade.editCategoriesToProduct(product);
        storageFacade.updateProductAttributeSelections(product);
    }

    public void editAttributesToCategory(Category category, ArrayList<String> attributeChoices) {
        ArrayList<Attribute> attributeList = Attribute.getMatchingAttributesOnIDs(attributeChoices);
        category.setCategoryAttributes(attributeList);
        ArrayList<Product> productsUpdated = Product.updateCategoryAttributes(category.getCategoryID());
        storageFacade.updateProductAttributeSelections(productsUpdated);
        storageFacade.editAttributeToCategory(category);
    }
}
