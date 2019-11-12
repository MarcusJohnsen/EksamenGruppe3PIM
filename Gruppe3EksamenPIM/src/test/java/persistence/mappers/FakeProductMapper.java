package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */

public class FakeProductMapper implements ProductMapperInterface {

    ArrayList<HashMap<String, String>> productInformation;
    ArrayList<HashMap<String, String>> distributorsInformation;

    public FakeProductMapper() {
        productInformation = new ArrayList();
        distributorsInformation = new ArrayList();
    }

    @Override
    public ArrayList<HashMap<String, Object>> getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addNewProduct(Product product) {
        HashMap<String, String> productMap = new HashMap();
        productMap.put("name", product.getName());
        productMap.put("description", product.getName());
        productMap.put("picturePath", product.getPicturePath());
        int newID = findHighestID();
        productMap.put("ID", Integer.toString(newID));
        productInformation.add(productMap);
        
        for (String distributor : product.getDistributors()) {
            HashMap<String, String> distributorMap = new HashMap();
            distributorMap.put("productID", Integer.toString(product.getProductID()));
            distributorMap.put("distributor", distributor);
        }

        return newID;
    }

    public ArrayList<HashMap<String, String>> getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(ArrayList<HashMap<String, String>> productInformation) {
        this.productInformation = productInformation;
    }

    private int findHighestID() {
        int newHighest = 0;
        for (HashMap<String, String> hashMap : productInformation) {
            int thisInt = Integer.parseInt(hashMap.get("ID"));
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