/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
public class ProductTest {

    @Test
    public void testCreateNewProduct() {
        //arrange
        String name = "ProductTesterName1";
        String descrition = "Product Test Description Number 1";
        
        //act
        Product result = Product.createNewProduct(name, descrition);
        
        //assert
        int expectedID = 1;
        assertTrue(name.equals(result.getName()));
        assertTrue(descrition.equals(result.getDescription()));
        assertEquals(expectedID, result.getProductID());
    }
    
    
    @Test
    public void testCreate3NewProduct() {
        //arrange
        String name = "ProductTesterName1";
        String descrition = "Product Test Description Number 1";
        
        //act
        Product result1 = Product.createNewProduct(name, descrition);
        Product result2 = Product.createNewProduct(name, descrition);
        Product result3 = Product.createNewProduct(name, descrition);
        
        //assert
        int expectedID = 1;
        assertTrue(name.equals(result1.getName()));
        assertTrue(descrition.equals(result1.getDescription()));
        assertEquals(expectedID, result1.getProductID());
        assertTrue(name.equals(result2.getName()));
        assertTrue(descrition.equals(result2.getDescription()));
        assertEquals(expectedID, result2.getProductID());
        assertTrue(name.equals(result3.getName()));
        assertTrue(descrition.equals(result3.getDescription()));
        assertEquals(expectedID, result3.getProductID());
    }
    
}
