/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Product;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class ProductMapperTest {
    
    private static Connection testConnection;
    private final DB database = new DB(SystemMode.TEST);
    private final int numberOfProductsInDB = 3;
    private final int numberOfProductDistributorsInDB = 6;
    private final int numberOfProductDistributorsForProductIDNr1InDB = 1;
    private final int numberOfProductDistributorsForProductIDNr2InDB = 2;
    private final int numberOfProductDistributorsForProductIDNr3InDB = 3;
    
    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Product");
                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");
            }
            
        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }
    
    @Test
    public void testSetUpOK() {
        // Just check that we have a connection.
        assertNotNull(testConnection);
    }

    /**
     * Test of getProducts method, of class ProductMapper.
     */
    @Test
    public void testGetProducts() {
        //arrange
        ProductMapper instance = new ProductMapper(database);

        //act
        ArrayList<Product> result = instance.getProducts();

        //assert
        assertEquals(numberOfProductsInDB, result.size());
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.
     */
    @Test
    public void testAddNewProduct() {
        //arrange
        String productName = "New Product";
        String productDescription = "This is a new product";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));
        ProductMapper instance = new ProductMapper(database);

        //act
        Product result = instance.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertEquals(productDistributors, result.getDistributors());
        
    }

    /**
     * Test of updatePicturePath method, of class ProductMapper.
     */
    @Test
    public void testUpdatePicturePath() {
        //arrange
        int productID = 1;
        String picturePath = "";
        ProductMapper instance = new ProductMapper(database);

        //act
        int result = instance.updatePicturePath(productID, picturePath);

        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteProduct method, of class ProductMapper.
     */
    @Test
    public void testDeleteProduct() {
        //arrange
        int productID = 1;
        ProductMapper instance = new ProductMapper(database);

        //act
        int result = instance.deleteProduct(productID);

        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Test of editProduct method, of class ProductMapper.
     */
    @Test
    public void testEditProduct() {
        //arrange
        Product product = new Product(1, "newTitle", "newDescription", "newPic.img", new ArrayList(Arrays.asList(new String[]{"dist. nr. 1", "dist. nr. 2", "dist. nr. 3"})));
        ProductMapper instance = new ProductMapper(database);

        //act
        int result = instance.editProduct(product);

        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Test of getProductDistributors method, of class ProductMapper.
     */
    @Test
    public void testGetProductDistributors() {
        //arrange
        int product_ID = 1;
        ProductMapper instance = new ProductMapper(database);

        //act
        ArrayList<String> result = instance.getProductDistributors(product_ID);

        //assert
        assertEquals(numberOfProductDistributorsForProductIDNr1InDB, result.size());
    }

    /**
     * Test of addProductDistributors method, of class ProductMapper.
     */
    @Test
    public void testAddProductDistributors() {
        //arrange
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"new Dist. 1", "new Dist. 2", "new Dist. 3"}));
        int productID = 1;
        ProductMapper instance = new ProductMapper(database);
        
        //act
        int result = instance.addProductDistributors(productDistributors, productID);
        
        //assert
        int expResult = productDistributors.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteProductDistributors method, of class ProductMapper.
     */
    @Test
    public void testDeleteProductDistributors() {
        //arrange
        int productID = 1;
        ProductMapper instance = new ProductMapper(database);
        
        //act
        int result = instance.deleteProductDistributors(productID);
        
        //assert
        assertEquals(numberOfProductDistributorsForProductIDNr1InDB, result);
    }
    
}
