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
public class FakeProductMapper implements ProductMapperInterface{

    @Override
    public ArrayList<HashMap<String, String>> getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addNewProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
