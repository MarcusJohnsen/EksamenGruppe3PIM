package businessLogic;

import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.Part;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BusinessControllerTest {

    private static Connection testConnection;
    private final BusinessController businessController = new BusinessController(SystemMode.TEST);
    private final int databaseAttributeAmount = 5;
    private final int databaseBundleAmount = 3;
    private final int databaseCategoryAmount = 3;
    private final int databaseDistributorAmount = 3;
    private final int databaseProductAmount = 3;

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
        ArrayList<String> categoryAttributeStrings = new ArrayList(Arrays.asList(new String[]{"1", "2"}));

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
        ArrayList<String> productDistributorStrings = new ArrayList(Arrays.asList(new String[]{"1", "2"}));
        ArrayList<String> productCategoryStrings = new ArrayList(Arrays.asList(new String[]{"1", "2"}));
        List<Part> requestParts = new ArrayList();

        //act
        Product result = businessController.createNewProduct(productName, productDescription, productDistributorStrings, productCategoryStrings, requestParts);
        int expProductID = databaseProductAmount+1;
        
        //assert
        assertTrue(productName.equals(result.objectTitle));
        assertTrue(productDescription.equals(result.objectDescription));
        assertTrue(businessController.getProductList().contains(result));
        assertEquals(result.getObjectID(), expProductID);
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
        int distributorID1 = 1;
        int distributorID2 = 2;
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{Integer.toString(distributorID1), Integer.toString(distributorID2)}));
        HashMap<Integer, String> productAttributeValues = new HashMap();

        //act
        businessController.editProduct(productID, productName, productDescription, productDistributors, productAttributeValues);
        Product result = businessController.getProductFromID(productID);

        //assert
        int expResultDistributorSize = 2;
        Distributor expResultDistributor1 = Distributor.findDistributorOnID(distributorID1);
        Distributor expResultDistributor2 = Distributor.findDistributorOnID(distributorID2);
        assertTrue(result.objectTitle.equals(productName));
        assertTrue(result.objectDescription.equals(productDescription));
        assertEquals(expResultDistributorSize, result.getProductDistributors().size());
        assertTrue(result.getProductDistributors().contains(expResultDistributor1));
        assertTrue(result.getProductDistributors().contains(expResultDistributor2));
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
        assertEquals(expIDResult1, result1.objectID);
        assertEquals(expIDResult2, result2.objectID);
        assertEquals(expIDResult3, result3.objectID);

        assertTrue(attributeTitle1.equals(result1.objectTitle));
        assertTrue(attributeTitle2.equals(result2.objectTitle));
        assertTrue(attributeTitle3.equals(result3.objectTitle));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateNewAttributeTitleNoAttributeTitle() {
        //arrange
        String attributeTitle1 = "";

        //act
        businessController.createNewAttribute(attributeTitle1);
    }

    @Test
    public void testEditCategory() {
        //arrange
        int categoryID = 1;
        String categoryNewTitle = "New Category Name/Title";
        String categoryNewDescription = "This is a new and better description";

        //act
        businessController.editCategory(categoryID, categoryNewTitle, categoryNewDescription);
        Category localCategoryResult = Category.findCategoryOnID(categoryID);
        TreeSet<Category> databaseCategories = businessController.getStorageFacade().getCategories(businessController.getAttributeList());
        Category databaseCategoryResult = null;
        for (Category databaseCategory : databaseCategories) {
            if (databaseCategory.getObjectID() == categoryID) {
                databaseCategoryResult = databaseCategory;
                break;
            }
        }
        if (databaseCategoryResult == null) {
            fail("Could not find the categoryObject from the database");
        }

        //assert
        assertEquals(categoryNewTitle, databaseCategoryResult.getObjectTitle());
        assertEquals(categoryNewTitle, localCategoryResult.getObjectTitle());
        assertEquals(categoryNewDescription, databaseCategoryResult.getObjectDescription());
        assertEquals(categoryNewDescription, localCategoryResult.getObjectDescription());
    }

    @Test
    public void testEditAttribute() {
        //arrange
        int attributeID = 1;
        String attributeNewTitle = "New Attribute Name/Title";

        //act
        businessController.editAttribute(attributeID, attributeNewTitle);

        //assert
        Attribute localAttributeResult = Attribute.findAttributeOnID(attributeID);
        TreeSet<Attribute> databaseAttributes = businessController.getStorageFacade().getAttributes();
        Attribute databaseAttributeResult = null;
        for (Attribute databaseAttribute : databaseAttributes) {
            if (databaseAttribute.getObjectID() == attributeID) {
                databaseAttributeResult = databaseAttribute;
                break;
            }
        }
        if (databaseAttributeResult == null) {
            fail("Could not find the attributeObject from the database");
        }

        assertEquals(attributeNewTitle, databaseAttributeResult.getObjectTitle());
        assertEquals(attributeNewTitle, localAttributeResult.getObjectTitle());
    }

    @Test
    public void testDeleteAttribute() {
        //arrange
        int attributeID = 1;

        //act
        businessController.deleteAttribute(attributeID);
        TreeSet<Attribute> databaseAttributes = businessController.getStorageFacade().getAttributes();

        //assert
        for (Attribute databaseAttribute : databaseAttributes) {
            assertTrue(databaseAttribute.getObjectID() != attributeID);
        }
        for (Attribute attribute : businessController.getAttributeList()) {
            assertTrue(attribute.getObjectID() != attributeID);
        }
        for (Category category : businessController.getCategoryList()) {
            for (Attribute attribute : category.getCategoryAttributes()) {
                assertTrue(attribute.getObjectID() != attributeID);
            }
        }
        for (Product product : businessController.getProductList()) {
            for (Attribute attribute : product.getProductAttributes()) {
                assertTrue(attribute.getObjectID() != attributeID);
            }
        }
    }

    @Test
    public void testCreateNewDistributor() {
        //arrange
        String distributorTitle = "new distributor";
        String distributorDescription = "this is the newest description";

        //act
        Distributor localDistributorResult = businessController.createNewDistributor(distributorTitle, distributorDescription);
        TreeSet<Distributor> databaseDistributors = businessController.getStorageFacade().getDistributors();
        Distributor databaseDistributorResult = null;
        for (Distributor databaseDistributor : databaseDistributors) {
            if (databaseDistributor.getObjectID() == localDistributorResult.getObjectID()) {
                databaseDistributorResult = databaseDistributor;
            }
        }
        if (databaseDistributorResult == null) {
            fail("could not find matching distributor from the database");
        }

        //assert
        int expectedID = databaseDistributorAmount + 1;
        int expectedProductListSize = 0;
        assertEquals(expectedID, localDistributorResult.getObjectID());
        assertEquals(expectedID, databaseDistributorResult.getObjectID());
        assertEquals(distributorTitle, localDistributorResult.getObjectTitle());
        assertEquals(distributorTitle, databaseDistributorResult.getObjectTitle());
        assertEquals(distributorDescription, localDistributorResult.getObjectDescription());
        assertEquals(distributorDescription, databaseDistributorResult.getObjectDescription());
        assertEquals(expectedProductListSize, databaseDistributorResult.getDistributorProducts().size());
        assertEquals(expectedProductListSize, databaseDistributorResult.getDistributorProducts().size());
    }

    @Test
    public void testDeleteDistributor() {
        //arrange
        int distributorID = 1;

        //act
        businessController.deleteDistributor(distributorID);
        TreeSet<Distributor> databaseDistributors = businessController.getStorageFacade().getDistributors();

        //assert
        for (Distributor distributor : databaseDistributors) {
            assertTrue(distributor.getObjectID() != distributorID);
        }
        for (Distributor distributor : businessController.getDistributorList()) {
            assertTrue(distributor.getObjectID() != distributorID);
        }
        for (Product product : businessController.getProductList()) {
            for (Distributor distributor : product.getProductDistributors()) {
                assertTrue(distributor.getObjectID() != distributorID);
            }
        }
    }

    @Test
    public void testEditDistributor() {
        //arrange
        int distributorID = 1;
        String distributorTitle = "New Distributor";
        String distributorDescription = "This is the newest, newest description";

        //act
        businessController.editDistributor(distributorID, distributorTitle, distributorDescription);

        //assert
        Distributor localAttributeResult = businessController.getDistributorFromID(distributorID);
        TreeSet<Distributor> databaseDistributor = businessController.getStorageFacade().getDistributors();
        Distributor databaseDistributorResult = null;
        for (Distributor distributor : databaseDistributor) {
            if (distributor.getObjectID() == distributorID) {
                databaseDistributorResult = distributor;
                break;
            }
        }
        if (databaseDistributorResult == null) {
            fail("Could not find the attributeObject from the database");
        }

        assertEquals(distributorTitle, databaseDistributorResult.getObjectTitle());
        assertEquals(distributorTitle, localAttributeResult.getObjectTitle());
        assertEquals(distributorDescription, databaseDistributorResult.getObjectDescription());
        assertEquals(distributorDescription, localAttributeResult.getObjectDescription());
    }

    @Test
    public void testEditCategoriesToProduct() {
        //arrange
        int productID = 1;
        int categoryAmount = 2;
        int categoryIDn1 = 1;
        int categoryIDn2 = 3;
        Product product = businessController.getProductFromID(productID);
        ArrayList<String> categoryChoices = new ArrayList(Arrays.asList(new String[]{Integer.toString(categoryIDn1), Integer.toString(categoryIDn2)}));

        //act
        businessController.editCategoriesToProduct(product, categoryChoices);

        //assert
        TreeSet<Category> expectedProductCategories = new TreeSet();
        Category category1 = businessController.getCategoryFromID(categoryIDn1);
        expectedProductCategories.add(category1);
        Category category2 = businessController.getCategoryFromID(categoryIDn2);
        expectedProductCategories.add(category2);

        assertTrue(product.getProductCategories().containsAll(expectedProductCategories));
        assertTrue(category1.getCategoryProducts().contains(product));
        assertTrue(category2.getCategoryProducts().contains(product));
        assertEquals(categoryAmount, product.getProductCategories().size());
        assertTrue(product.getProductAttributes().containsAll(category1.getCategoryAttributes()));
        assertTrue(product.getProductAttributes().containsAll(category2.getCategoryAttributes()));
    }

    @Test
    public void testEditAttributesToCategory() {
        //arrange
        int categoryID = 1;
        int attributeAmount = 3;
        int attributeIDn1 = 1;
        int attributeIDn2 = 3;
        int attributeIDn3 = 5;
        Category category = businessController.getCategoryFromID(categoryID);
        ArrayList<String> attributeChoices = new ArrayList(Arrays.asList(new String[]{Integer.toString(attributeIDn1),
            Integer.toString(attributeIDn2), Integer.toString(attributeIDn3)}));

        //act
        businessController.editAttributesToCategory(category, attributeChoices);

        //assert
        TreeSet<Attribute> expectedCategoryAttributes = new TreeSet();
        Attribute attribute1 = businessController.getAttributeFromID(attributeIDn1);
        expectedCategoryAttributes.add(attribute1);
        Attribute attribute2 = businessController.getAttributeFromID(attributeIDn2);
        expectedCategoryAttributes.add(attribute2);
        Attribute attribute3 = businessController.getAttributeFromID(attributeIDn3);
        expectedCategoryAttributes.add(attribute3);

        assertTrue(category.getCategoryAttributes().containsAll(expectedCategoryAttributes));
        assertEquals(attributeAmount, category.getCategoryAttributes().size());
        for (Product categoryProduct : category.getCategoryProducts()) {
            assertTrue(categoryProduct.getProductAttributes().containsAll(expectedCategoryAttributes));
        }
    }

    @Test
    public void testCreateNewBundle() {
        //arrange
        String bundleTitle = "New Bundle Name";
        String bundleDescription = "This is one of new bundles!";
        HashMap<Integer, Integer> productChoices = new HashMap();
        int productIDn1 = 1;
        Integer productIDn1Amount = 3;
        int productIDn2 = 2;
        Integer productIDn2Amount = 1;
        productChoices.put(productIDn1, productIDn1Amount);
        productChoices.put(productIDn2, productIDn2Amount);

        //act
        Bundle localBundleResult = businessController.createNewBundle(bundleTitle, bundleDescription, productChoices);
        TreeSet<Bundle> databaseBundles = businessController.getStorageFacade().getBundles(businessController.getProductList());
        Bundle databaseBundleResult = null;
        for (Bundle databaseBundle : databaseBundles) {
            if (databaseBundle.getObjectID() == localBundleResult.getObjectID()) {
                databaseBundleResult = databaseBundle;
                break;
            }
        }
        if (databaseBundleResult == null) {
            fail("Could not find matching Bundle in database to local bundle");
        }

        //assert
        int expectedBundleID = databaseBundleAmount + 1;
        Product product1 = businessController.getProductFromID(productIDn1);
        Product product2 = businessController.getProductFromID(productIDn2);
        Set<Product> expectedProductSet = new TreeSet(Arrays.asList(new Product[]{product1, product2}));
        assertEquals(expectedBundleID, localBundleResult.getObjectID());
        assertEquals(expectedBundleID, databaseBundleResult.getObjectID());
        assertEquals(bundleTitle, localBundleResult.getObjectTitle());
        assertEquals(bundleTitle, databaseBundleResult.getObjectTitle());
        assertEquals(bundleDescription, localBundleResult.getObjectDescription());
        assertEquals(bundleDescription, databaseBundleResult.getObjectDescription());
        assertTrue(localBundleResult.getBundleProducts().keySet().containsAll(expectedProductSet));
        assertTrue(databaseBundleResult.getBundleProducts().keySet().containsAll(expectedProductSet));
        assertEquals(productIDn1Amount, localBundleResult.getBundleProducts().get(product1));
        assertEquals(productIDn1Amount, databaseBundleResult.getBundleProducts().get(product1));
        assertEquals(productIDn2Amount, localBundleResult.getBundleProducts().get(product2));
        assertEquals(productIDn2Amount, databaseBundleResult.getBundleProducts().get(product2));
    }

    @Test
    public void testDeleteBundle() {
        //arrange
        int bundleID = 1;

        //act
        businessController.deleteBundle(bundleID);
        TreeSet<Bundle> databaseBundleList = businessController.getStorageFacade().getBundles(businessController.getProductList());

        //assert
        for (Bundle bundle : databaseBundleList) {
            assertTrue(bundle.getObjectID() != bundleID);
        }
        for (Bundle bundle : businessController.getBundleList()) {
            assertTrue(bundle.getObjectID() != bundleID);
        }
        for (Product product : businessController.getProductList()) {
            for (Bundle bundle : product.getProductBundle()) {
                assertTrue(bundle.getObjectID() != bundleID);
            }
        }
    }

    @Test
    public void testEditBundle() {
        //arrange
        int bundleID = 1;
        String bundleTitle = "Editted bundle title/name";
        String bundleDescription = "Bla bla bla Bla bla bla Bla bla bla";
        HashMap<Integer, Integer> productChoices = new HashMap();
        int productIDn1 = 1;
        Integer productIDn1Amount = 1;
        int productIDn2 = 3;
        Integer productIDn2Amount = 2;
        productChoices.put(productIDn1, productIDn1Amount);
        productChoices.put(productIDn2, productIDn2Amount);

        //act
        businessController.editBundle(bundleID, bundleTitle, bundleDescription, productChoices);
        Bundle localBundleResult = businessController.getBundleFromID(bundleID);
        TreeSet<Bundle> databaseBundles = businessController.getStorageFacade().getBundles(businessController.getProductList());
        Bundle databaseBundleResult = null;
        for (Bundle databaseBundle : databaseBundles) {
            if (databaseBundle.getObjectID() == bundleID) {
                databaseBundleResult = databaseBundle;
                break;
            }
        }
        if (databaseBundleResult == null) {
            fail("Could not find matching Bundle in database to local bundle");
        }

        //assert
        Product product1 = businessController.getProductFromID(productIDn1);
        Product product2 = businessController.getProductFromID(productIDn2);
        Set<Product> expectedProductSet = new TreeSet(Arrays.asList(new Product[]{product1, product2}));
        assertEquals(bundleTitle, localBundleResult.getObjectTitle());
        assertEquals(bundleTitle, databaseBundleResult.getObjectTitle());
        assertEquals(bundleDescription, localBundleResult.getObjectDescription());
        assertEquals(bundleDescription, databaseBundleResult.getObjectDescription());
        assertTrue(localBundleResult.getBundleProducts().keySet().containsAll(expectedProductSet));
        assertTrue(databaseBundleResult.getBundleProducts().keySet().containsAll(expectedProductSet));
        assertEquals(productIDn1Amount, localBundleResult.getBundleProducts().get(product1));
        assertEquals(productIDn1Amount, databaseBundleResult.getBundleProducts().get(product1));
        assertEquals(productIDn2Amount, localBundleResult.getBundleProducts().get(product2));
        assertEquals(productIDn2Amount, databaseBundleResult.getBundleProducts().get(product2));
    }

    @Test
    public void testBulkEdit() {
        //arrange
        int productIDn1 = 1;
        int productIDn2 = 3;
        int attributeIDn1 = 2;
        String attributeIDn1NewValue = "New first value for all products";
        int attributeIDn2 = 4;
        String attributeIDn2NewValue = "New values for the second attribute";
        ArrayList<Integer> productIDs = new ArrayList(Arrays.asList(new Integer[]{productIDn1, productIDn2}));
        HashMap<Integer, String> newAttributeValues = new HashMap();
        newAttributeValues.put(attributeIDn1, attributeIDn1NewValue);
        newAttributeValues.put(attributeIDn2, attributeIDn2NewValue);

        //Setting up some old values on products not included in the bulk edit selection
        Attribute attribute1 = businessController.getAttributeFromID(attributeIDn1);
        Attribute attribute2 = businessController.getAttributeFromID(attributeIDn2);
        String oldAttribute = "Old attribute value";
        for (Integer integer : attribute1.getAttributeValues().keySet()) {
            attribute1.getAttributeValues().put(integer, oldAttribute);
        }
        for (Integer integer : attribute2.getAttributeValues().keySet()) {
            attribute2.getAttributeValues().put(integer, oldAttribute);
        }

        //act
        businessController.bulkEdit(productIDs, newAttributeValues);

        //assert
        assertEquals(attributeIDn1NewValue, attribute1.getAttributeValueForID(productIDn1));
        assertEquals(attributeIDn1NewValue, attribute1.getAttributeValueForID(productIDn2));
        assertEquals(attributeIDn2NewValue, attribute2.getAttributeValueForID(productIDn1));
        assertEquals(attributeIDn2NewValue, attribute2.getAttributeValueForID(productIDn2));
        for (Integer integer : attribute1.getAttributeValues().keySet()) {
            if (integer != productIDn1 && integer != productIDn2) {
                assertNotEquals(attributeIDn1NewValue, attribute1.getAttributeValueForID(integer));
            }
        }
        for (Integer integer : attribute2.getAttributeValues().keySet()) {
            if (integer != productIDn1 && integer != productIDn2) {
                assertNotEquals(attributeIDn1NewValue, attribute2.getAttributeValueForID(integer));
            }
        }

    }

    @Test
    public void testSearchProduct() {
        //arrange
        String searchString = "a";

        //act
        TreeSet<Product> result = businessController.searchProduct(searchString);

        //assert
        for (Product product : result) {
            assertTrue(product.getObjectTitle().contains(searchString));
        }
    }

    @Test
    public void testAdvancedSearch() {
        //arrange
        String searchString = "a";
        String searchOnObject = "Product";
        String bundleFilter = null;
        String categoryFilter = "";
        String distributorFilter = null;
        String productFilter = null;

        //act
        TreeSet<PIMObject> result = businessController.advancedSearch(searchString, searchOnObject, bundleFilter, categoryFilter, distributorFilter, productFilter);

        //assert
        for (PIMObject object : result) {
            Product product = (Product) object;
            assertTrue(product.getObjectTitle().contains(searchString));
        }
    }

    @Test
    public void testfindProductsOnCategoryID() {
        //arrange
        int categoryID = 1;
        Category category = businessController.getCategoryFromID(categoryID);

        //act
        TreeSet<Product> result = businessController.findProductsOnCategoryID(categoryID);

        //assert
        for (Product product : result) {
            assertTrue(product.getProductCategories().contains(category));
        }
    }

    @Test
    public void testGetSystemStatistics() {
        //act
        HashMap<String, Object> result = businessController.getSystemStatistics();

        //assert
        assertEquals(businessController.getProductList().size(), result.get("productCount"));
        assertEquals(businessController.getBundleList().size(), result.get("bundleCount"));
        assertEquals(businessController.getCategoryList().size(), result.get("categoryCount"));
        assertEquals(businessController.getDistributorList().size(), result.get("distributorCount"));

        List<Bundle> sortedTopTenBundleList = (List<Bundle>) result.get("topTenBundles");
        List<Category> sortedTopTenCategoryList = (List<Category>) result.get("topTenCategories");
        List<Distributor> sortedTopTenDistributorList = (List<Distributor>) result.get("topTenDistributors");
        int lastProductCount;

        lastProductCount = Integer.MAX_VALUE;
        assertTrue(sortedTopTenBundleList.size() <= 10);
        for (Bundle bundle : sortedTopTenBundleList) {
            int bundleProductAmount = bundle.getBundleProducts().keySet().size();
            assertTrue(bundleProductAmount <= lastProductCount);
            lastProductCount = bundleProductAmount;
        }

        lastProductCount = Integer.MAX_VALUE;
        assertTrue(sortedTopTenCategoryList.size() <= 10);
        for (Category category : sortedTopTenCategoryList) {
            int categoryProductAmount = category.getCategoryProducts().size();
            assertTrue(categoryProductAmount <= lastProductCount);
            lastProductCount = categoryProductAmount;
        }

        lastProductCount = Integer.MAX_VALUE;
        assertTrue(sortedTopTenDistributorList.size() <= 10);
        for (Distributor distributor : sortedTopTenDistributorList) {
            int distributorProductAmount = distributor.getDistributorProducts().size();
            assertTrue(distributorProductAmount <= lastProductCount);
            lastProductCount = distributorProductAmount;
        }
    }

}
