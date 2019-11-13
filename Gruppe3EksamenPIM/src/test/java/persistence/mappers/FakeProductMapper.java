package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */

public class FakeProductMapper implements ProductMapperInterface {

    private ArrayList<Product> productInformation;
    private int newHighestProductID;

    public FakeProductMapper() {
        productInformation = new ArrayList();
        newHighestProductID = 1;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return productInformation;
    }

    @Override
    public int addNewProduct(Product product) {
        int newID = newHighestProductID++;
        productInformation.add(product);
        return newID;
    }

    public ArrayList<Product> getProductInformation() {
        return productInformation;
    }

    public int setProductInformation(ArrayList<Product> productInformation) {
        this.productInformation = productInformation;
        newHighestProductID = findHighestID();
        return newHighestProductID - 1;
    }

    private int findHighestID() {
        int newHighest = 0;
        for (Product product : productInformation) {
            if (product.getProductID() > newHighest) {
                newHighest = product.getProductID();
            }
        }
        newHighest++;
        return newHighest;
    }

    @Override
    public void updatePicturePath(int productID, String picturePath) {
        //This DB-change should not be nessery, as the change is already made in the Product class.
    }
    @Override
    public ArrayList<String> getProductDistributors(int product_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteProduct(int productID) {
        ArrayList<Product> productsToBeRemoved = new ArrayList();
        for (Product product : productInformation) {
            if(product.getProductID() == productID){
                productsToBeRemoved.add(product);
            }
        }
        for (Product product : productsToBeRemoved) {
            productInformation.remove(product);
        }
    }

    @Override
    public void editProduct(int productID, String name, String description, ArrayList<String> distributors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}