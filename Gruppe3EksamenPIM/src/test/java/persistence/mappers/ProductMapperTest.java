package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
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
import org.junit.Ignore;
import persistence.DB;

/**
 *
 * @author Marcus
 */
public class ProductMapperTest {

    private final DB database = new DB(SystemMode.TEST);
    private final ProductMapper productMapper = new ProductMapper(database);
    private final ArrayList<Category> categoryList = new ArrayList();
    private static Connection testConnection;

    private final int numberOfProductsInDB = 3;
    private final int numberOfProductDistributorsForProductIDNr1InDB = 1;

    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {

                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");
                stmt.execute("drop table if exists Categories");
                stmt.execute("drop table if exists Attributes");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");

                stmt.execute("create table Product_Categories like Product_Categories_Test");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Categories select * from Product_Categories_Test");

                stmt.execute("create table Product_Attributes like Product_Attributes_Test");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Product_Attributes select * from Product_Attributes_Test");

                stmt.execute("create table Category_Attributes like Category_Attributes_Test");
                stmt.execute("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE category_attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Category_Attributes select * from Category_Attributes_Test");

            }
            categoryList.clear();

        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    @Test
    public void testSetUpOK() {
        // checking that we have a connection.
        assertNotNull(testConnection);
    }

    /**
     * Test of getProducts method, of class ProductMapper.
     */
    @Test
    public void testGetProducts() {
        //act
        ArrayList<Product> result = productMapper.getProducts(categoryList);

        //assert
        assertEquals(numberOfProductsInDB, result.size());
    }

    /**
     * Negative Test of getProducts method, from class ProductMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Product_Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetProductsNoProductsTableInDB() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Product");
        }

        //act
        ArrayList<Product> result = productMapper.getProducts(categoryList);
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

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertEquals(productDistributors, result.getDistributors());

    }

    /**
     * Negative Test of addNewProduct method, of class ProductMapper.<br>
     * Name field in DB is made to be not null, unique varchar(255).<br>
     * Names with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductNullName() {
        //arrange
        String productName = null;
        String productDescription = "First new description";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * Name field in DB is made to be not null, unique varchar(255). Therefore inserting exactly 255 characters does not crash it.
     */
    @Test
    public void testAddNewProductNameLengthAtLimit() {
        //arrange
        String productName = "";
        for (int i = 0; i < 255; i++) {
            productName += "n";
        }
        String productDescription = "First new description";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertTrue(productDistributors.equals(result.getDistributors()));
    }

    /**
     * Negative Test of addNewProduct method, of class ProductMapper.<br>
     * Name field in DB is made to be not null, unique varchar(255). Names exceeding the 255 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductNameLengthExceedLimit() {
        //arrange
        String productName = "";
        for (int i = 0; i < 256; i++) {
            productName += "n";
        }
        String productDescription = "First new description";
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Negative Test of addNewProduct method, of class ProductMapper.<br>
     * Description field in DB is made to be not null, unique varchar(255).<br>
     * Descriptions with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductNullDescription() {
        //arrange
        String productName = "new product";
        String productDescription = null;
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * Description field in DB is made to be not null, varchar(2550). Therefore adding exactly 2550 characters should not crash the program.
     */
    @Test
    public void testAddNewProductDescriptionLengthAtLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "";
        for (int i = 0; i < 2550; i++) {
            productDescription += "n";
        }
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertTrue(productDistributors.equals(result.getDistributors()));
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Descriptions exceeding the 2550 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewProductDescriptionLengthExceedLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "";
        for (int i = 0; i < 2551; i++) {
            productDescription += "n";
        }
        String productPicturePath = "newProduct.img";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * picturePath field in DB is made to be not null, varchar(100). Therefore adding exactly 100 characters should not crash the program.
     */
    @Test
    public void testAddNewProductPicturePathLengthAtLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "First new description";
        String productPicturePath = "";
        for (int i = 0; i < 100; i++) {
            productPicturePath += "n";
        }
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertTrue(productDistributors.equals(result.getDistributors()));
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * picturePath field in DB is made to be not null, varchar(100).<br>
     * picturePaths exceeding the 100 varchar limit should cause an exception to be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewProductPicturePathLengthExceedLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "First new description";
        String productPicturePath = "";
        for (int i = 0; i < 101; i++) {
            productPicturePath += "n";
        }
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"ProductTester", "ProductBuilder"}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * DistributorName field in DB is made to be not null, varchar(255). Therefore adding exactly 255 characters should not crash the program.
     */
    @Test
    public void testAddNewProductDistributorsAtLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "First new description";
        String productPicturePath = "newProduct.img";
        String productDistributor = "";
        for (int i = 0; i < 255; i++) {
            productDistributor += "n";
        }
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{productDistributor}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getProductID());
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(productPicturePath.equals(result.getPicturePath()));
        assertTrue(productDistributors.equals(result.getDistributors()));
    }

    /**
     * Test of addNewProduct method, of class ProductMapper.<br>
     * DistributorName field in DB is made to be not null, varchar(255).<br>
     * DistributorName exceeding the 255 varchar limit should cause an exception to be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewProductDistributorsExceedLimit() {
        //arrange
        String productName = "new product";
        String productDescription = "First new description";
        String productPicturePath = "newProduct.img";
        String productDistributor = "";
        for (int i = 0; i < 256; i++) {
            productDistributor += "n";
        }
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{productDistributor}));

        //act
        Product result = productMapper.addNewProduct(productName, productDescription, productPicturePath, productDistributors);
    }

    /**
     * Test of updatePicturePath method, of class ProductMapper.
     */
    @Test
    public void testUpdatePicturePath() {
        //arrange
        int productID = 1;
        String picturePath = "";

        //act
        int result = productMapper.updatePicturePath(productID, picturePath);

        //assert
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Negative test of updatePicturePath method, of class ProductMapper. Picturepath field in DB is made to be not null, varchar(100).<br>
     * Picturepath exceeding the 100 varchar limit should cause an exception to be thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestUpdatePicturePath() {
        //arrange
        int productID = 1;
        String picturePath = "";
        for (int i = 0; i < 101; i++) {
            picturePath += "n";
        }
        ProductMapper instance = new ProductMapper(database);

        //act
        int result = instance.updatePicturePath(productID, picturePath);
    }

    /**
     * Test of deleteProduct method, of class ProductMapper.
     */
    @Test
    public void testDeleteProduct() {
        //arrange
        int productID = 1;

        //act
        int result = productMapper.deleteProduct(productID);

        //assert
        int expResult = 8;
        assertEquals(expResult, result);
    }

    /**
     * Negative test of deleteProduct method, from class ProductMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Product_Categories table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestDeleteProduct() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Product_Categories");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Product");
        }
        int productID = 1;

        //act
        int result = productMapper.deleteProduct(productID);
    }

    /**
     * Test of editProduct method, of class ProductMapper.
     */
    @Test
    public void testEditProduct() {
        //arrange
        Product product = new Product(1, "newTitle", "newDescription", "newPic.img", new ArrayList(Arrays.asList(new String[]{"dist. nr. 1", "dist. nr. 2", "dist. nr. 3"})), categoryList);

        //act
        int result = productMapper.editProduct(product);

        //assert
        int expResult = 5;
        assertEquals(expResult, result);
    }

    /**
     * Negative test of editProduct method, of class ProductMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Description exceeding the 2550 varchar limit should cause an exception to be thrown.<br>
     * It's also worth noting that the edit could've also crashed by exceeding the character limit in "Name", "picturePath", "Distributors" or by an ID that doesn't exist.<br>
     * Showing every single option would've been an overkill and description was therefore chosen.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestEditProduct() {
        //arrange
        String productDescription = "";
        for (int i = 0; i < 2551; i++) {
            productDescription += "n";
        }
        Product product = new Product(1, "newTitle", productDescription, "newPic.img", new ArrayList(Arrays.asList(new String[]{"dist. nr. 1", "dist. nr. 2", "dist. nr. 3"})), categoryList);

        //act
        int result = productMapper.editProduct(product);
    }

    /**
     * Test of editProductCategories method, of class ProductMapper.
     */
    @Test
    public void testEditProductCategories() {
        //arrange
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{}));
        int[] categoryID = new int[]{1, 2};
        Category category1 = new Category(categoryID[0], "Tests", "These are tests", new ArrayList<Attribute>());
        Category category2 = new Category(categoryID[1], "Tests", "These are tests", new ArrayList<Attribute>());
        categoryList.add(category1);
        categoryList.add(category2);
        Product product = new Product(1, "Test Product", "This product is for testing", "test.jpg", distributors, categoryList);
        AttributeMapper attributeMapper = new AttributeMapper(database);
        CategoryMapper categoryMapper = new CategoryMapper(database);

        //act
        productMapper.editProductCategories(product);

        //assert
        ArrayList<Attribute> attributeListFromDB = attributeMapper.getAttributes();
        ArrayList<Category> categoryListFromDB = categoryMapper.getCategories(attributeListFromDB);
        ArrayList<Product> productListFromDB = productMapper.getProducts(categoryListFromDB);
        Product productFromDB = productListFromDB.get(0);
        assertEquals(categoryList.size(), productFromDB.getProductCategories().size());
        assertEquals(category1.getCategoryID(), productFromDB.getProductCategories().get(0).getCategoryID());
        assertEquals(category2.getCategoryID(), productFromDB.getProductCategories().get(1).getCategoryID());
    }
}
