package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */

public class FakeProductMapper implements ProductMapperInterface {

    ArrayList<HashMap<String, Object>> productInformation;

    public FakeProductMapper() {
        productInformation = new ArrayList();
    }

    @Override
    public ArrayList<HashMap<String, Object>> getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addNewProduct(Product product) {
        HashMap<String, Object> productMap = new HashMap();
        productMap.put("name", product.getName());
        productMap.put("description", product.getName());
        productMap.put("picturePath", product.getPicturePath());
        productMap.put("distributors", product.getDistributors());
        int newID = findHighestID();
        productMap.put("ID", newID);
        productInformation.add(productMap);

        return newID;
    }

    public ArrayList<HashMap<String, Object>> getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(ArrayList<HashMap<String, Object>> productInformation) {
        this.productInformation = productInformation;
    }

    private int findHighestID() {
        int newHighest = 0;
        for (HashMap<String, Object> hashMap : productInformation) {
            int thisInt = (int) hashMap.get("ID");
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

    @Override
    public void deleteProduct(int productID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}