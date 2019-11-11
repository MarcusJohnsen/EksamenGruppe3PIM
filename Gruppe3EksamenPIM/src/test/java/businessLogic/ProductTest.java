/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistence.mappers.FakeProductMapper;
import persistence.mappers.ProductMapperInterface;

/**
 *
 * @author Michael N. Korsgaard
 */
public class ProductTest {
    
    private static ProductMapperInterface mapper;
    
    @Before
    public void setup(){
        FakeProductMapper fakeMapper = new FakeProductMapper();
        mapper = fakeMapper;
        Product.setProductMapper(mapper);
    }

    @Test
    public void testCreateNewProduct() {
        //arrange
        String name = "ProductTesterName1";
        String descrition = "Product Test Description Number 1";
        String picturePath = "Picture/Path/Here";
        ArrayList<String> distributers = new ArrayList();
        String distributer = "Distributer name";
        distributers.add(distributer);
        
        //act
        Product result = Product.createNewProduct(name, descrition, picturePath, distributers);
        
        //assert
        int expectedID = 1;
        assertTrue(name.equals(result.getName()));
        assertTrue(descrition.equals(result.getDescription()));
        assertTrue(picturePath.equals(result.getPicturePath()));
        assertEquals(expectedID, result.getProductID());
    }
    
    
    @Test
    public void testCreate3NewProduct() {
        //arrange
        String name1 = "ProductTesterName1";
        String descrition1 = "Product Test Description Number 1";
        String picturePath1 = "Picture/Path/Here";
        String name2 = "ProductTesterNavn2";
        String descrition2 = "Product Description Test Number 2";
        String picturePath2 = "Picture\\Path\\Here";
        String name3 = "ProductTesterNaame3";
        String descrition3 = "Product Test Description TEST Number 3";
        String picturePath3 = "Picture is here";
        ArrayList<String> distributers = new ArrayList();
        String distributer = "Distributer name";
        distributers.add(distributer);
        
        //act
        Product result1 = Product.createNewProduct(name1, descrition1, picturePath1, distributers);
        Product result2 = Product.createNewProduct(name2, descrition2, picturePath2, distributers);
        Product result3 = Product.createNewProduct(name3, descrition3, picturePath3, distributers);
        
        //assert
        int expectedIDn1 = 1;
        int expectedIDn2 = 2;
        int expectedIDn3 = 3;
        assertTrue(name1.equals(result1.getName()));
        assertTrue(descrition1.equals(result1.getDescription()));
        assertTrue(picturePath1.equals(result1.getPicturePath()));
        assertEquals(expectedIDn1, result1.getProductID());
        assertTrue(name2.equals(result2.getName()));
        assertTrue(descrition2.equals(result2.getDescription()));
        assertTrue(picturePath2.equals(result2.getPicturePath()));
        assertEquals(expectedIDn2, result2.getProductID());
        assertTrue(name3.equals(result3.getName()));
        assertTrue(descrition3.equals(result3.getDescription()));
        assertTrue(picturePath3.equals(result3.getPicturePath()));
        assertEquals(expectedIDn3, result3.getProductID());
    }
    
}
