/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.TreeSet;

/**
 *
 * @author Andreas
 */
public class SearchEngine {

    public static TreeSet<Product> simpleSearch(String searchString, TreeSet<Product> fullList) {
        TreeSet<Product> result = new TreeSet();
        searchString = searchString.toLowerCase();
        for (Product product : fullList) {
            String productName = product.getName().toLowerCase();
            String productID = Integer.toString(product.getProductID());
            if (productName.contains(searchString) || productID.contains(searchString)) {
                result.add(product);
            }
        }
        return result;
    }
    
}
