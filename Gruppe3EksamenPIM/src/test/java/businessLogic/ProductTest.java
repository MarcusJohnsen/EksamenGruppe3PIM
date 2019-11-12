package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private static int highestProductIDInDB;

    @Before
    public void setup() {
        Product.emptyProductList();
        FakeProductMapper fakeMapper = new FakeProductMapper();

        ArrayList<HashMap<String, Object>> productInfo = new ArrayList();
        HashMap<String, Object> productMap = new HashMap();
        Object[][] products = {
            // String name, String description, String picturePath, ArrayList<String> distributors, int productID
            {"Product 1", "This is the primary test 1", "picture.img", new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"})), 1},
            {"Test Product", "This is a test product", "picture.png", new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"})), 2},
            {"Product for Testing", "This product is only for testing", "new.img", new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"})), 3},
            {"New Test 3000", "Newest, best, testproduct, EVER!!!", "something.img", new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"})), 5},
            {"Cahit's Test", "I made this", "mine.img", new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"})), 7}
        };

        for (Object[] object : products) {
            productMap = new HashMap();
            productMap.put("product_Name", (String) object[0]);
            productMap.put("product_Description", (String) object[1]);
            productMap.put("picturePath", (String) object[2]);
            productMap.put("distributor", (ArrayList) object[3]);
            productMap.put("product_ID", (int) object[4]);
            productInfo.add(productMap);
        }

        highestProductIDInDB = fakeMapper.setProductInformation(productInfo);
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
        int expectedID = highestProductIDInDB + 1;
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
        int expectedIDn1 = highestProductIDInDB + 1;
        int expectedIDn2 = highestProductIDInDB + 2;
        int expectedIDn3 = highestProductIDInDB + 3;
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
    public void testDeleteProductOnID() {
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
        boolean result = Product.deleteProductOnID(productID);

        //assert
        assertFalse(Product.getProductList().contains(testProduct));
        assertTrue(result);
    }

    @Test
    public void testDeleteProductOnIDNotFoundEmptyList() {

        //act
        boolean result = Product.deleteProductOnID(1);

        //assert
        assertFalse(result);
    }

    @Test
    public void testDeleteProductOnIDNotFoundNotEmptyList() {
        //arrange
        String productName = "Test Product";
        String productDescription = "This is a product for testing, not for using";
        String picturePath = "Picture.file";
        ArrayList<String> distributors = new ArrayList();
        distributors.add("Tester 1");
        distributors.add("Programmer 1");
        Product testProduct = Product.createNewProduct(productName, productDescription, picturePath, distributors);
        int productID = testProduct.getProductID();
        productID++;

        //act
        boolean result = Product.deleteProductOnID(productID);

        //assert
        assertTrue(Product.getProductList().contains(testProduct));
        assertFalse(result);
    }

    @Test
    public void testSetupProductsFromDB() {
        
        int expectedProductListSize = 0;
        assertEquals(expectedProductListSize, Product.getProductList().size());
        
        //act
        Product.setupProductsFromDB();
        ArrayList<Product> result = Product.getProductList();
        
        //assert
        expectedProductListSize = 5;
        assertEquals(expectedProductListSize, result.size());

    }

}
