package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */

public class FakeProductMapper implements ProductMapperInterface {

    private ArrayList<HashMap<String, Object>> productInformation;
    private int newHighestProductID;

    public FakeProductMapper() {
        productInformation = new ArrayList();
        newHighestProductID = 1;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getProducts() {
        return productInformation;
    }

    @Override
    public int addNewProduct(Product product) {
        HashMap<String, Object> productMap = new HashMap();
        productMap.put("product_Name", product.getName());
        productMap.put("product_Description", product.getDescription());
        productMap.put("picturePath", product.getPicturePath());
        productMap.put("distributor", product.getDistributors());
        int newID = newHighestProductID++;
        productMap.put("product_ID", newID);
        productInformation.add(productMap);

        return newID;
    }

    public ArrayList<HashMap<String, Object>> getProductInformation() {
        return productInformation;
    }

    public int setProductInformation(ArrayList<HashMap<String, Object>> productInformation) {
        this.productInformation = productInformation;
        newHighestProductID = findHighestID();
        return newHighestProductID - 1;
    }

    private int findHighestID() {
        int newHighest = 0;
        for (HashMap<String, Object> hashMap : productInformation) {
            int thisInt = (int) hashMap.get("product_ID");
            if (thisInt > newHighest) {
                newHighest = thisInt;
            }
        }
        newHighest++;
        return newHighest;
    }

    @Override
    public void addImage(int productID, String picturePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public ArrayList<String> getProductDistributors(int product_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}