package businessLogic;

import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.Part;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
 public class IntegrationTest {
    private static Connection testConnection;
    private final BusinessController businessController = new BusinessController(SystemMode.TEST);

    @Before
    public void setup() {
        try {
            testConnection = businessController.getStorageFacade().getSqlDatabase().getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.addBatch("drop table if exists Product_Distributor");
                stmt.addBatch("drop table if exists Product_Categories");
                stmt.addBatch("drop table if exists Product_Attributes");
                stmt.addBatch("drop table if exists Product_Bundles");
                stmt.addBatch("drop table if exists Category_Attributes");
                stmt.addBatch("drop table if exists Product");
                stmt.addBatch("drop table if exists Categories");
                stmt.addBatch("drop table if exists Attributes");
                stmt.addBatch("drop table if exists Distributor");
                stmt.addBatch("drop table if exists Bundles");

                stmt.addBatch("create table Product like Product_Test");
                stmt.addBatch("insert into Product select * from Product_Test");

                stmt.addBatch("create table Categories like Categories_Test");
                stmt.addBatch("insert into Categories select * from Categories_Test");
                
                stmt.addBatch("create table Bundles like Bundles_Test");
                stmt.addBatch("insert into Bundles select * from Bundles_Test");

                stmt.addBatch("create table Attributes like Attributes_Test");
                stmt.addBatch("insert into Attributes select * from Attributes_Test");
                
                stmt.addBatch("create table Distributor like Distributor_Test");
                stmt.addBatch("insert into Distributor select * from Distributor_Test");

                stmt.addBatch("create table Product_Distributor like Product_Distributor_Test");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.addBatch("insert into Product_Distributor select * from Product_Distributor_Test");
                
                stmt.addBatch("create table Product_Bundles like Product_Bundles_Test");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Bundle_ID) REFERENCES Bundles(Bundle_ID)");
                stmt.addBatch("ALTER TABLE Product_Bundles ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Bundles select * from Product_Bundles_Test");

                stmt.addBatch("create table Product_Categories like Product_Categories_Test");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.addBatch("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("insert into Product_Categories select * from Product_Categories_Test");

                stmt.addBatch("create table Product_Attributes like Product_Attributes_Test");
                stmt.addBatch("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.addBatch("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.addBatch("insert into Product_Attributes select * from Product_Attributes_Test");
                
                stmt.addBatch("create table Category_Attributes like Category_Attributes_Test");
                stmt.addBatch("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.addBatch("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.addBatch("insert into Category_Attributes select * from Category_Attributes_Test");
                stmt.executeBatch();
                stmt.close();
                businessController.setupListsFromDB();
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

    @Test
    public void testCreateNewCategory() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "This is a new category, for new things";
        ArrayList<String> categoryAttributeStrings = new ArrayList(Arrays.asList(new String[]{"1","2"}));
        
        //act
        Category result = businessController.createNewCategory(categoryName, categoryDescription, categoryAttributeStrings);

        //assert
        assertTrue(categoryName.equals(result.objectTitle));
        assertTrue(categoryDescription.equals(result.objectDescription));
        assertTrue(businessController.getCategoryList().contains(result));
    }

    @Test
    public void testDeleteCategory() {
        //arrange
        int categoryID = 1;

        //act
        boolean result = businessController.deleteCategory(categoryID);

        //assert
        TreeSet<Category> categoryList = businessController.getCategoryList();
        assertTrue(result);
        for (Category category : categoryList) {
            assertNotEquals(categoryID, category.objectID);
        }
    }

    @Test
    public void testCreateNewProduct() {
        //arrange
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        ArrayList<String> productDistributorStrings = new ArrayList(Arrays.asList(new String[]{"1"}));
        ArrayList<String> productCategoryStrings = new ArrayList(Arrays.asList(new String[]{"1", "2"}));
        List<Part> requestParts = new ArrayList();
        
        //act
        Product result = businessController.createNewProduct(productName, productDescription, productDistributorStrings, productCategoryStrings, requestParts);

        //assert
        assertTrue(productName.equals(result.objectTitle));
        assertTrue(productDescription.equals(result.objectDescription));
        assertTrue(businessController.getProductList().contains(result));
    }

    @Test
    public void testDeleteProduct() {
        //arrange
        int productID = 1;

        //act
        boolean result = businessController.deleteProduct(productID);

        //assert
        TreeSet<Product> productList = businessController.getProductList();
        assertTrue(result);
        for (Product product : productList) {
            assertNotEquals(productID, product.objectID);
        }
    }

    @Test
    public void testEditProduct() {
        //arrange
        int productID = 1;
        int distributorID = 1;
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{Integer.toString(distributorID)}));
        HashMap<Integer, String> productAttributeValues = new HashMap();

        //act
        businessController.editProduct(productID, productName, productDescription, productDistributors, productAttributeValues);
        Product result = businessController.getProductFromID(productID);

        //assert
        int expResultDistributorSize = 1;
        Distributor expResultDistributor = Distributor.findDistributorOnID(distributorID);
        assertTrue(result.objectTitle.equals(productName));
        assertTrue(result.objectDescription.equals(productDescription));
        assertEquals(expResultDistributorSize, result.getProductDistributors().size());
        assertTrue(result.getProductDistributors().contains(expResultDistributor));
    }

    @Test
    public void testGetCategoryFromID() {
        //arrange
        int categoryID = 5;
        String categoryName = "Landscaping";
        String categoryDescription = "For landscaping in garden";
        TreeSet<Attribute> categoryAttributes = new TreeSet();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);
        Category.addToCategoryList(category);

        //act
        Category result = businessController.getCategoryFromID(categoryID);

        //assert
        assertTrue(categoryName.equals(result.objectTitle));
        assertTrue(categoryDescription.equals(result.objectDescription));
    }

    @Test
    public void testCreateNewAttribute() {
        //arrange
        String attributeTitle1 = "tester1";
        String attributeTitle2 = "tester2";
        String attributeTitle3 = "tester3";
        int expIDResult1 = 6;
        int expIDResult2 = expIDResult1 + 1;
        int expIDResult3 = expIDResult2 + 1;
        
        //act
        Attribute result1 = businessController.createNewAttribute(attributeTitle1);
        Attribute result2 = businessController.createNewAttribute(attributeTitle2);
        Attribute result3 = businessController.createNewAttribute(attributeTitle3);

        //assert
        assertEquals(expIDResult1,result1.objectID);
        assertEquals(expIDResult2,result2.objectID);
        assertEquals(expIDResult3,result3.objectID);
        
        assertTrue(attributeTitle1.equals(result1.objectTitle));
        assertTrue(attributeTitle2.equals(result2.objectTitle));
        assertTrue(attributeTitle3.equals(result3.objectTitle));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateNewAttributeTitleNoAttributeTitle(){
        //arrange
        String attributeTitle1 = "";
        
        //act
        businessController.createNewAttribute(attributeTitle1);
    }
}