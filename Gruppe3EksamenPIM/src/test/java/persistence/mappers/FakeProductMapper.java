/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;
import persistence.mappers.ProductMapperInterface;

/**
 *
 * @author Michael N. Korsgaard
 */
public class FakeProductMapper implements ProductMapperInterface {

    ArrayList<HashMap<String, String>> productInformation;
    ArrayList<HashMap<String, String>> distributersInformation;

    public FakeProductMapper() {
        productInformation = new ArrayList();
        distributersInformation = new ArrayList();
    }

    @Override
    public ArrayList<HashMap<String, String>> getProducts() {
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
        
        for (String distributer : product.getDistributers()) {
            HashMap<String, String> distributerMap = new HashMap();
            distributerMap.put("productID", Integer.toString(product.getProductID()));
            distributerMap.put("distributer", distributer);
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

}
