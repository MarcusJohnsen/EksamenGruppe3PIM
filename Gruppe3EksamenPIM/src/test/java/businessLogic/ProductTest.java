package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
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
        int productId = 2;
        String productName = "spiderman";
        String productDescription = "the hero spiderman";
        String picturePath = "testimage.png";
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));
        ArrayList<Category> productCategories = new ArrayList();

        Product result = new Product(productId, productName, productDescription, picturePath, distributors, productCategories);

        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertTrue(picturePath.equals(result.getPicturePath()));
        assertEquals(productId, result.getProductID());
        assertEquals(distributors, result.getDistributors());
        assertEquals(productCategories, result.getProductCategories());
    }

    @Test
    public void testAddToProductList() {

        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        ArrayList<String>[] distributors = new ArrayList[]{new ArrayList(Arrays.asList(new String[]{"Marvel"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"}))};
        ArrayList<Category>[] productCategories = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};

        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], distributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], distributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], distributors[2], productCategories[2]);

        //act
        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);

        //assert
        assertEquals(Product.getProductList().size(), productID.length);
        assertEquals(product1, Product.getProductList().get(0));
        assertEquals(product2, Product.getProductList().get(1));
        assertEquals(product3, Product.getProductList().get(2));
    }

    @Test
    public void testDeleteProductOnID() {
        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        ArrayList<String>[] distributors = new ArrayList[]{new ArrayList(Arrays.asList(new String[]{"Marvel"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"}))};
        ArrayList<Category>[] productCategories = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};
        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], distributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], distributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], distributors[2], productCategories[2]);
        
        Product.addToProductList(product1);
        Product.addToProductList(product2);
        Product.addToProductList(product3);
        
        //act
        Product.deleteProductOnID(3);
        Product.deleteProductOnID(5);

        //assert
        assertEquals(Product.getProductList().size(), 1);
        assertEquals(product1, Product.getProductList().get(0));
    }

    @Test
    public void testFindproductOnId() {

        //arrange
        int[] productID = new int[]{1, 3, 5};
        String[] productName = new String[]{"spiderman", "superman", "batman"};
        String[] productDescription = new String[]{"the hero spiderman", "the hero superman", "the hero batman"};
        String[] picturePath = new String[]{"testimageSpiderman.png", "testimageSuperman.png", "testimageBatman.png"};
        ArrayList<String>[] distributors = new ArrayList[]{new ArrayList(Arrays.asList(new String[]{"Marvel"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"})), new ArrayList(Arrays.asList(new String[]{"DC Comic"}))};
        ArrayList<Category>[] productCategories = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};
        Product product1 = new Product(productID[0], productName[0], productDescription[0], picturePath[0], distributors[0], productCategories[0]);
        Product product2 = new Product(productID[1], productName[1], productDescription[1], picturePath[1], distributors[1], productCategories[1]);
        Product product3 = new Product(productID[2], productName[2], productDescription[2], picturePath[2], distributors[2], productCategories[2]);
        
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
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));
        ArrayList<Category> productCategories = new ArrayList();

        Product product = new Product(productID, productName, productDescription, picturePath, distributors, productCategories);
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
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));

        boolean result = Product.validateProductInput(productName, productDescription, distributors);

        assertTrue(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateProductInputNoProductDescriptionInput() {

        String productName = "joker";
        String productDescription = "";
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));

        boolean result = Product.validateProductInput(productName, productDescription, distributors);

        assertTrue(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateProductInputNoDistributorInput() {

        String productName = "joker";
        String productDescription = "the hero joker";
        ArrayList<String> distributors = new ArrayList();
        distributors.add(null);

        boolean result = Product.validateProductInput(productName, productDescription, distributors);

        assertTrue(result);

    }

    @Test
    public void testValidateProductInputEmptyDistributorsButNotTotalEmpty() {
        //arrange
        String productName = "Batman";
        String productDescription = "This is the 'best' superhero ever";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"", "batman", null}));

        //act
        Product.validateProductInput(productName, productDescription, productDistributors);

        //assert
        int expSize = 1;
        assertEquals(expSize, productDistributors.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateProductInputEmptyDistributorsEmptyValues() {
        //arrange
        String productName = "Batman";
        String productDescription = "This is the 'best' superhero ever";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{"", "", null}));

        //act
        Product.validateProductInput(productName, productDescription, productDistributors);
    }

}
