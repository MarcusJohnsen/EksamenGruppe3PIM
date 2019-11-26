package businessLogic;

import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
 public class IntegrationTest {
    private static Connection testConnection;
    private final BusinessFacade businessFacade = new BusinessFacade(SystemMode.TEST);

    @Before
    public void setup() {
        try {
            testConnection = businessFacade.getStorageFacade().getDatabase().getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Product_Bundles");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");
                stmt.execute("drop table if exists Categories");
                stmt.execute("drop table if exists Attributes");
                stmt.execute("drop table if exists Distributor");
                stmt.execute("drop table if exists Bundles");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");
                
                stmt.execute("create table Bundles like Bundles_Test");
                stmt.execute("insert into Bundles select * from Bundles_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");
                
                stmt.execute("create table Distributor like Distributor_Test");
                stmt.execute("insert into Distributor select * from Distributor_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");
                
                stmt.execute("create table Product_Bundles like Product_Bundles_Test");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.execute("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Bundles select * from Product_Bundles_Test");

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
                businessFacade.setupListsFromDB();
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

    //Category createNewCategory(String categoryName, String categoryDescription) throws IllegalArgumentException
    @Test
    public void testCreateNewCategory() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "This is a new category, for new things";

        //act
        Category result = businessFacade.createNewCategory(categoryName, categoryDescription);

        //assert
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
        assertTrue(businessFacade.getCategoryList().contains(result));
    }

    //boolean deleteCategory(int categoryID)
    @Test
    public void testDeleteCategory() {
        //arrange
        int categoryID = 1;

        //act
        boolean result = businessFacade.deleteCategory(categoryID);

        //assert
        ArrayList<Category> categoryList = businessFacade.getCategoryList();
        assertTrue(result);
        for (Category category : categoryList) {
            assertNotEquals(categoryID, category.getCategoryID());
        }

    }

    //Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributors)
    @Test
    public void testCreateNewProduct() {
        //arrange
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        //String firstDistributor = "1st Distributor";
        //String secondDistributor = "2nd Distributor";
        //ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{firstDistributor, secondDistributor}));

        //act
        Product result = businessFacade.createNewProduct(productName, productDescription);

        //assert
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(businessFacade.getProductList().contains(result));
    }

    //boolean deleteProduct(int productID)
    @Test
    public void testDeleteProduct() {
        //arrange
        int productID = 1;

        //act
        boolean result = businessFacade.deleteProduct(productID);

        //assert
        ArrayList<Product> productList = businessFacade.getProductList();
        assertTrue(result);
        for (Product product : productList) {
            assertNotEquals(productID, product.getProductID());
        }
    }

    //void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributors)
    @Test
    public void testEditProduct() {
        //arrange
        int productID = 1;
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        String firstDistributor = "1st Distributor";
        String secondDistributor = "2nd Distributor";
        String firstDistributor2 = "1st Distributor";
        String secondDistributor2 = "2nd Distributor";
        ArrayList<Distributor> productDistributors = new ArrayList(Arrays.asList(new String[]{firstDistributor, secondDistributor}));
        HashMap<Integer, String> productAttributeValues = new HashMap();

        //act
        businessFacade.editProduct(productID, productName, productDescription, productDistributors, productAttributeValues);
        Product result = businessFacade.getProductFromID(productID);

        //assert
        assertTrue(result.getName().equals(productName));
        assertTrue(result.getDescription().equals(productDescription));

    }

    //void updatePicturePath(int productID, String picturePath)
    @Test
    public void testUpdatePicturePath() {
        //arrange
        int productID = 1;
        String picturePath = "newPic.img";

        //act
        businessFacade.updatePicturePath(productID, picturePath);
        Product result = businessFacade.getProductFromID(productID);

        //assert
        assertTrue(picturePath.equals(result.getPicturePath()));

    }

    @Test
    public void testGetCategoryFromID() {
        //arrange
        int categoryID = 5;
        String categoryName = "Landscaping";
        String categoryDist = "For landscaping in garden";
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        Category category = new Category(categoryID, categoryName, categoryDist, categoryAttributes);
        Category.addToCategoryList(category);

        //act
        Category result = businessFacade.getCategoryFromID(categoryID);

        //assert
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDist.equals(result.getDescription()));
    }

    @Test

    public void testCreateNewAttribute() {

        String attributeTitle1 = "tester1";
        String attributeTitle2 = "tester2";
        String attributeTitle3 = "tester3";

        Attribute result1 = businessFacade.createNewAttribute(attributeTitle1);
        Attribute result2 = businessFacade.createNewAttribute(attributeTitle2);
        Attribute result3 = businessFacade.createNewAttribute(attributeTitle3);

        int expIDResult1 = 6;
        int expIDResult2 = expIDResult1 + 1;
        int expIDResult3 = expIDResult2 + 1;
        
        assertEquals(expIDResult1,result1.getAttributeID());
        assertEquals(expIDResult2,result2.getAttributeID());
        assertEquals(expIDResult3,result3.getAttributeID());
        
        assertTrue(attributeTitle1.equals(result1.getAttributeTitle()));
        assertTrue(attributeTitle2.equals(result2.getAttributeTitle()));
        assertTrue(attributeTitle3.equals(result3.getAttributeTitle()));


    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateNewAttributeTitleNoAttributeTitle(){
        
        String attributeTitle1 = "";
        Attribute result1 = businessFacade.createNewAttribute(attributeTitle1);
        
        
        
    }
    
    
    }
