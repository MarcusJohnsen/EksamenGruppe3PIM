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
        String description = "Product Test Description Number 1";
        String picturePath = "Picture/Path/Here";
        ArrayList<String> distributors = new ArrayList();
        String distributor = "Distributor name";
        distributors.add(distributor);
        
        //act
        Product result = Product.createNewProduct(name, description, picturePath, distributors);
        
        //assert
        int expectedID = 1;
        assertTrue(name.equals(result.getName()));
        assertTrue(description.equals(result.getDescription()));
        assertTrue(picturePath.equals(result.getPicturePath()));
        assertEquals(expectedID, result.getProductID());
    }
    
    
    @Test
    public void testCreate3NewProduct() {
        //arrange
        String name1 = "ProductTesterName1";
        String description1 = "Product Test Description Number 1";
        String picturePath1 = "Picture/Path/Here";
        String name2 = "ProductTesterNavn2";
        String description2 = "Product Description Test Number 2";
        String picturePath2 = "Picture\\Path\\Here";
        String name3 = "ProductTesterNaame3";
        String description3 = "Product Test Description TEST Number 3";
        String picturePath3 = "Picture is here";
        ArrayList<String> distributors = new ArrayList();
        String distributor = "Distributor name";
        distributors.add(distributor);
        
        //act
        Product result1 = Product.createNewProduct(name1, description1, picturePath1, distributors);
        Product result2 = Product.createNewProduct(name2, description2, picturePath2, distributors);
        Product result3 = Product.createNewProduct(name3, description3, picturePath3, distributors);
        
        //assert
        int expectedIDn1 = 1;
        int expectedIDn2 = 2;
        int expectedIDn3 = 3;
        assertTrue(name1.equals(result1.getName()));
        assertTrue(description1.equals(result1.getDescription()));
        assertTrue(picturePath1.equals(result1.getPicturePath()));
        assertEquals(expectedIDn1, result1.getProductID());
        assertTrue(name2.equals(result2.getName()));
        assertTrue(description2.equals(result2.getDescription()));
        assertTrue(picturePath2.equals(result2.getPicturePath()));
        assertEquals(expectedIDn2, result2.getProductID());
        assertTrue(name3.equals(result3.getName()));
        assertTrue(description3.equals(result3.getDescription()));
        assertTrue(picturePath3.equals(result3.getPicturePath()));
        assertEquals(expectedIDn3, result3.getProductID());
    }
    
    @Test
    public void testDeleteProductOnID(){
        //arrange
        String productName = "Test Product";
        String productDescription = "This is a product for testing, not for using";
        String picturePath = "Picture.file";
        ArrayList<String> distributors = new ArrayList();
        distributors.add("Tester 1");
        distributors.add("Programmer 1");
        Product testProduct = Product.createNewProduct(productName, productDescription, picturePath, distributors);
        int productID = testProduct.getProductID();
        
        //act
        Product.deleteProductOnID(productID);
        
        //assert
        assertFalse(Product.getProductList().contains(testProduct));
    }
    
}
