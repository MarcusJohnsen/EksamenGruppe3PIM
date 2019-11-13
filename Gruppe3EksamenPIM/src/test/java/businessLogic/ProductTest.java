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

    private static FakeProductMapper mapper;
    private static int highestProductIDInDB;

    private final static int Db1ProductID = 1;
    private final static String Db1ProductName = "Product 1";
    private final static String Db1ProductDescription = "This is the primary test 1";
    private final static String Db1ProductPicturePath = "picture.img";
    private final static ArrayList<String> Db1ProductDistributors = new ArrayList(Arrays.asList(new String[]{"distributor 1", "distributor 2"}));
    private final static int Db2ProductID = 2;
    private final static String Db2ProductName = "Test Product";
    private final static String Db2ProductDescription = "This is a test product";
    private final static String Db2ProductPicturePath = "picture.png";
    private final static ArrayList<String> Db2ProductDistributors = new ArrayList(Arrays.asList(new String[]{"distributor A"}));
    private final static int Db3ProductID = 4;
    private final static String Db3ProductName = "Product for Testing";
    private final static String Db3ProductDescription = "This product is only for testing";
    private final static String Db3ProductPicturePath = "new.img";
    private final static ArrayList<String> Db3ProductDistributors = new ArrayList(Arrays.asList(new String[]{}));

    private final static int amountOfProductInitiallyInDB = 3;

    @Before
    public void setup() {
        Product.emptyProductList();
        FakeProductMapper fakeMapper = new FakeProductMapper();

        ArrayList<Product> productList = new ArrayList();

        productList.add(new Product(Db1ProductID, Db1ProductName, Db1ProductDescription, Db1ProductPicturePath, Db1ProductDistributors));
        productList.add(new Product(Db2ProductID, Db2ProductName, Db2ProductDescription, Db2ProductPicturePath, Db2ProductDistributors));
        productList.add(new Product(Db3ProductID, Db3ProductName, Db3ProductDescription, Db3ProductPicturePath, Db3ProductDistributors));

        highestProductIDInDB = fakeMapper.setProductInformation(productList);
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
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"Tester 1", "Programmer 1"}));
        Product testProduct = Product.createNewProduct(productName, productDescription, picturePath, distributors);
        int productID = testProduct.getProductID();

        //act
        boolean result = Product.deleteProductOnID(productID);

        //assert
        assertFalse(Product.getProductList().contains(testProduct));
        assertFalse(mapper.getProductInformation().contains(testProduct));
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
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"Tester 1", "Programmer 1"}));
        Product testProduct = Product.createNewProduct(productName, productDescription, picturePath, distributors);
        int productID = testProduct.getProductID();

        //act
        boolean result = Product.deleteProductOnID(productID + 1);

        //assert
        assertTrue(Product.getProductList().contains(testProduct));
        assertTrue(mapper.getProductInformation().contains(testProduct));
        assertFalse(result);
    }

    @Test
    public void testSetupProductsFromDB() {
        //act
        Product.setupProductListFromDB();
        ArrayList<Product> result = Product.getProductList();

        //assert
        int expectedProductListSize = amountOfProductInitiallyInDB;
        assertEquals(expectedProductListSize, result.size());
        assertEquals(Db1ProductID, result.get(0).getProductID());
        assertEquals(Db2ProductID, result.get(1).getProductID());
        assertEquals(Db3ProductID, result.get(2).getProductID());
        assertTrue(Db1ProductName.equals(result.get(0).getName()));
        assertTrue(Db2ProductName.equals(result.get(1).getName()));
        assertTrue(Db3ProductName.equals(result.get(2).getName()));
        assertTrue(Db1ProductDescription.equals(result.get(0).getDescription()));
        assertTrue(Db2ProductDescription.equals(result.get(1).getDescription()));
        assertTrue(Db3ProductDescription.equals(result.get(2).getDescription()));
        assertTrue(Db1ProductPicturePath.equals(result.get(0).getPicturePath()));
        assertTrue(Db2ProductPicturePath.equals(result.get(1).getPicturePath()));
        assertTrue(Db3ProductPicturePath.equals(result.get(2).getPicturePath()));
        assertEquals(Db1ProductDistributors, result.get(0).getDistributors());
        assertEquals(Db2ProductDistributors, result.get(1).getDistributors());
        assertEquals(Db3ProductDistributors, result.get(2).getDistributors());
    }

    @Test
    public void testSetupProductsFromDBMultipleTimes() {
        //act
        Product.setupProductListFromDB();
        Product.setupProductListFromDB();
        Product.setupProductListFromDB();
        ArrayList<Product> result = Product.getProductList();

        //assert
        int expectedProductListSize = amountOfProductInitiallyInDB;
        assertEquals(expectedProductListSize, result.size());
        assertEquals(Db1ProductID, result.get(0).getProductID());
        assertEquals(Db2ProductID, result.get(1).getProductID());
        assertEquals(Db3ProductID, result.get(2).getProductID());
        assertTrue(Db1ProductName.equals(result.get(0).getName()));
        assertTrue(Db2ProductName.equals(result.get(1).getName()));
        assertTrue(Db3ProductName.equals(result.get(2).getName()));
        assertTrue(Db1ProductDescription.equals(result.get(0).getDescription()));
        assertTrue(Db2ProductDescription.equals(result.get(1).getDescription()));
        assertTrue(Db3ProductDescription.equals(result.get(2).getDescription()));
        assertTrue(Db1ProductPicturePath.equals(result.get(0).getPicturePath()));
        assertTrue(Db2ProductPicturePath.equals(result.get(1).getPicturePath()));
        assertTrue(Db3ProductPicturePath.equals(result.get(2).getPicturePath()));
        assertEquals(Db1ProductDistributors, result.get(0).getDistributors());
        assertEquals(Db2ProductDistributors, result.get(1).getDistributors());
        assertEquals(Db3ProductDistributors, result.get(2).getDistributors());
    }
    
    @Test
    public void test(){
        //arrange
        String oldPicturePath = "oldPicturePath.img";
        Product product = Product.createNewProduct("New Test Product", "This is a test", oldPicturePath, new ArrayList(Arrays.asList(new String[]{"Tester 1", "Programmer 1"})));
        int productID = product.getProductID();
        String newPicturePath = "newPicturePath.img";
        
        //act
        Product.updatePicturePath(productID, newPicturePath);
        
        //assert
        assertTrue(newPicturePath.equals(product.getPicturePath()));
    }

}
