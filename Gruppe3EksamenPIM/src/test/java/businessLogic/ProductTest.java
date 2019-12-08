package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistence.mappers.ProductMapper;

/**
 *
 * @author Michael N. Korsgaard
 */
public class ProductTest {

    @Before
    public void setup() {
        Product.getProductList().clear();
    }

    @Test
    public void testProductConstructor() {
        //act
        int productID = 2;
        String productName = "spiderman";
        String productDescription = "the hero spiderman";
        String picturePath = "testimage.png";
        TreeSet<Distributor> productDistributors = new TreeSet();
        TreeSet<Category> productCategories = new TreeSet();

        Product result = new Product(productID, productName, productDescription, picturePath, productDistributors, productCategories);

        assertTrue(productName.equals(result.objectTitle));
        assertTrue(productDescription.equals(result.objectDescription));
        assertTrue(picturePath.equals(result.getPicturePath()));
        assertEquals(productID, result.objectID);
        assertEquals(productDistributors, result.getProductDistributors());
        assertEquals(productCategories, result.getProductCategories());
    }

    @Test
    public void testAddToProductList() {

        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        Distributor distributor1 = new Distributor(1, "Marvel", "The comic book company");
        Distributor distributor2 = new Distributor(2, "DC", "Another comic book company");
        TreeSet<Distributor>[] productDistributors = new TreeSet[]{
            new TreeSet(Arrays.asList(new Distributor[]{distributor1})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor2})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}))};
        TreeSet<Category>[] productCategories = new TreeSet[]{new TreeSet(), new TreeSet(), new TreeSet()};

        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], productDistributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], productDistributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], productDistributors[2], productCategories[2]);

        //act
        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        //assert
        assertEquals(Product.getProductList().size(), productID.length);
        assertTrue(Product.getProductList().contains(product1));
        assertTrue(Product.getProductList().contains(product2));
        assertTrue(Product.getProductList().contains(product3));
    }

    @Test
    public void testDeleteProductOnID() {
        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        Distributor distributor1 = new Distributor(1, "Marvel", "The comic book company");
        Distributor distributor2 = new Distributor(2, "DC", "Another comic book company");
        TreeSet<Distributor>[] productDistributors = new TreeSet[]{
            new TreeSet(Arrays.asList(new Distributor[]{distributor1})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor2})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}))};
        TreeSet<Category>[] productCategories = new TreeSet[]{new TreeSet(), new TreeSet(), new TreeSet()};
        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], productDistributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], productDistributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], productDistributors[2], productCategories[2]);

        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        //act
        Product.deleteProductOnID(3);
        Product.deleteProductOnID(5);

        //assert
        assertEquals(Product.getProductList().size(), 1);
        assertTrue(Product.getProductList().contains(product1));
    }

    @Test
    public void testFindproductOnId() {

        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        Distributor distributor1 = new Distributor(1, "Marvel", "The comic book company");
        Distributor distributor2 = new Distributor(2, "DC", "Another comic book company");
        TreeSet<Distributor>[] productDistributors = new TreeSet[]{
            new TreeSet(Arrays.asList(new Distributor[]{distributor1})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor2})),
            new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}))};
        TreeSet<Category>[] productCategories = new TreeSet[]{new TreeSet(), new TreeSet(), new TreeSet()};
        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], productDistributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], productDistributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], productDistributors[2], productCategories[2]);

        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        //act
        Product result1 = Product.findProductOnID(5);
        Product result2 = Product.findProductOnID(1);

        assertEquals(result1, product3);
        assertEquals(result2, product1);

    }

    @Test
    public void testNegativeFindproductOnIdNoMatchNotEmptyList() {

        int productID = 20;
        String productName = "joker";
        String productDescription = "the insane clown";
        String picturePath = "testimageJoker.png";
        Distributor distributor = new Distributor(1, "Name", "Description");
        TreeSet<Distributor> productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor}));
        TreeSet<Category> productCategories = new TreeSet();

        Product product = new Product(productID, productName, productDescription, picturePath, productDistributors, productCategories);
        Product.addToProductList(product);

        Product result = Product.findProductOnID(productID + 1);

        assertNull(result);

    }

    @Test
    public void testNegativeFindproductOnIdNoMatchEmptyList() {

        //Nothing in the list
        Product result = Product.findProductOnID(1);

        assertNull(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateProductInputNoNameInput() {

        String productName = "";
        String productDescription = "the hero joker";
        //ArrayList<Distributor>[] productDistributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));

        boolean result = Product.validateProductInput(productName, productDescription);

        assertTrue(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateProductInputNoProductDescriptionInput() {

        String productName = "joker";
        String productDescription = "";
        ArrayList<Distributor> productDistributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));

        boolean result = Product.validateProductInput(productName, productDescription);

        assertTrue(result);

    }

    /* @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateProductInputNoDistributorInput() {
        
        String productName = "joker";
        String productDescription = "the hero joker";
        ArrayList<Distributor>[] productDistributors = new ArrayList();
        distributors.add(null);
        
        boolean result = Product.validateProductInput(productName, productDescription, distributors);
        
        assertTrue(result);
        
    } */
 /* @Test
    public void testValidateProductInputEmptyDistributorsButNotTotalEmpty() {
        //arrange
        String productName = "Batman";
        String productDescription = "This is the 'best' superhero ever";
        ArrayList<Distributor>[] productDistributors = new ArrayList(Arrays.asList(new String[]{"", "batman", null}));

        //act
        Product.validateProductInput(productName, productDescription);

        //assert
        int expSize = 1;
        assertEquals(expSize, productDistributors.size());
    } */
 /* @Test(expected = IllegalArgumentException.class)
    public void testValidateProductInputEmptyDistributorsEmptyValues() {
        //arrange
        String productName = "Batman";
        String productDescription = "This is the 'best' superhero ever";
        ArrayList<Distributor> productDistributors = new ArrayList(Arrays.asList(new String[]{"", "", null}));

        //act
        Product.validateProductInput(productName, productDescription);
    } */
    @Test
    public void testFindProductOnProductID() {

        // arrange   
        int categoryID1 = 100;
        String categoryName1 = "newsletters";
        String categoryDescription1 = "papermade";
        TreeSet<Attribute> categoryAttributes1 = new TreeSet();

        Category category1 = new Category(categoryID1, categoryName1, categoryDescription1, categoryAttributes1);
        Distributor distributor1 = new Distributor(1, "Marvel", "The comic book company");
        Distributor distributor2 = new Distributor(2, "DC", "Another comic book company");

        int productId1 = 1;
        String productName1 = "burner";
        String description1 = "fire";
        String picPath1 = "thisIsAnImagePath1.png";
        TreeSet<Distributor> distributors1 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories1 = new TreeSet();
        productCategories1.add(category1);

        int productId2 = 2;
        String productName2 = "freezer";
        String description2 = "ice";
        String picPath2 = "thisIsAnImagePath2.png";
        TreeSet<Distributor> distributors2 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories2 = new TreeSet();
        productCategories2.add(category1);

        int productId3 = 3;
        String productName3 = "vaporizer";
        String description3 = "water";
        String picPath3 = "thisIsAnImagePath3.png";
        TreeSet<Distributor> distributors3 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories3 = new TreeSet();
        productCategories3.add(category1);

        Product product1 = new Product(productId1, productName1, description1, picPath1, distributors1, productCategories1);
        Product product2 = new Product(productId2, productName2, description2, picPath2, distributors2, productCategories2);
        Product product3 = new Product(productId3, productName3, description3, picPath3, distributors3, productCategories3);

        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        // act
        TreeSet<Product> result = new TreeSet();
        result = Product.findProductsOnCategoryID(100);

        // assert
        int expResultSize = 3;
        assertEquals(expResultSize, result.size());

    }

    @Test
    public void testUpdateCategoryAttributes() {

        int attributeID = 1;
        Attribute attribute = new Attribute(attributeID, "Alternative Name", new HashMap<Integer, String>());

        int categoryID = 1;
        Category category = new Category(categoryID, "Tests", "Category for tests", new TreeSet<Attribute>(Arrays.asList(new Attribute[]{attribute})));
        Distributor distributor1 = new Distributor(1, "Marvel", "The comic book company");
        Distributor distributor2 = new Distributor(2, "DC", "Another comic book company");

        int productId1 = 1;
        String productName1 = "burner";
        String description1 = "fire";
        String picPath1 = "thisIsAnImagePath1.png";
        TreeSet<Distributor> distributors1 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories1 = new TreeSet();
        productCategories1.add(category);

        int productId2 = 2;
        String productName2 = "freezer";
        String description2 = "ice";
        String picPath2 = "thisIsAnImagePath2.png";
        TreeSet<Distributor> distributors2 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories2 = new TreeSet();
        productCategories2.add(category);

        int productId3 = 3;
        String productName3 = "vaporizer";
        String description3 = "water";
        String picPath3 = "thisIsAnImagePath3.png";
        TreeSet<Distributor> distributors3 = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor2}));
        TreeSet<Category> productCategories3 = new TreeSet();
        Product product1 = new Product(productId1, productName1, description1, picPath1, distributors1, productCategories1);
        Product product2 = new Product(productId2, productName2, description2, picPath2, distributors2, productCategories2);
        Product product3 = new Product(productId3, productName3, description3, picPath3, distributors3, productCategories3);

        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        TreeSet<Product> result = Product.updateCategoryAttributes(categoryID);

        //assert
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        assertTrue(product1.getProductAttributes().contains(attribute));
        assertTrue(product2.getProductAttributes().contains(attribute));
        assertFalse(product3.getProductAttributes().contains(attribute));
    }

    @Test
    public void testProductConstructorWhereProductCategoriesAreNull() {

        int productID = 10;
        String name = "burner";
        String description = "fire";
        String picturePath = "this is an imageTest.png";
        Distributor distributor = new Distributor(1, "Name", "Description");
        TreeSet<Distributor> distributors = new TreeSet(Arrays.asList(new Distributor[]{distributor}));

        Product result = new Product(productID, name, description, picturePath, distributors, null);

        assertTrue(result.getProductCategories() instanceof TreeSet);
        assertTrue(result.getProductAttributes() instanceof TreeSet);
        assertTrue(result.getProductCategories().isEmpty());
        assertTrue(result.getProductAttributes().isEmpty());
    }

    @Test
    public void testEditProductCategories() {

        //Attribute:int attributeID, String attributeTitle, HashMap<Integer, String> attributeValues
        int attributeID = 1;
        String attributeTitle = "Size";
        HashMap<Integer, String> attributeValues = new HashMap<>();
        attributeValues.put(attributeID, attributeTitle);

        Attribute attribute = new Attribute(attributeID, attributeTitle, attributeValues);

        int categoryID = 100;
        String name = "burner";
        String description = "fire";
        TreeSet<Attribute> categoryAttributes = new TreeSet(Arrays.asList(new String[]{"firestarter", "firecharger"}));
        Category category = new Category(categoryID, name, description, categoryAttributes);

    }

}
