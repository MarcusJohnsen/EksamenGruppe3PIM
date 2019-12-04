/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andreas
 */
public class SearchEngineTest {
    
    @Test
    public void testSearchCorrect(){
        SearchEngine search = new SearchEngine(); //Is not "used", only for test cover
        String searchString = "Co";
        Product product1 = new Product(1, "Coca Cola Light", "", "", new TreeSet(), new TreeSet());
        Product product2 = new Product(2, "Coca Cola Max", "", "", new TreeSet(), new TreeSet());
        Product product3 = new Product(3, "Porsche", "", "", new TreeSet(), new TreeSet());
        TreeSet<Product> fullList = new TreeSet();
        fullList.add(product1);
        fullList.add(product2);
        fullList.add(product3);
        
        TreeSet<Product> result = SearchEngine.simpleSearch(searchString, fullList);
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        
    }
    
    @Test
    public void testSearchEmptyString(){
        String searchString = "";
        Product product1 = new Product(1, "Coca Cola Light", "", "", new TreeSet(), new TreeSet());
        Product product2 = new Product(2, "Coca Cola Max", "", "", new TreeSet(), new TreeSet());
        Product product3 = new Product(3, "Porsche", "", "", new TreeSet(), new TreeSet());
        TreeSet<Product> fullList = new TreeSet();
        fullList.add(product1);
        fullList.add(product2);
        fullList.add(product3);
        
        TreeSet<Product> result = SearchEngine.simpleSearch(searchString, fullList);
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertTrue(result.contains(product3));
        
    }
    
    @Test
    public void testSearchOnID(){
        String searchString = "2";
        Product product1 = new Product(1, "Coca Cola Light", "", "", new TreeSet(), new TreeSet());
        Product product2 = new Product(2, "Coca Cola Max", "", "", new TreeSet(), new TreeSet());
        Product product3 = new Product(3, "Porsche", "", "", new TreeSet(), new TreeSet());
        TreeSet<Product> fullList = new TreeSet();
        fullList.add(product1);
        fullList.add(product2);
        fullList.add(product3);
        
        TreeSet<Product> result = SearchEngine.simpleSearch(searchString, fullList);
        assertFalse(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        
    }
    
}
