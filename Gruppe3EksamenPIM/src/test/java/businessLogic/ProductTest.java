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

        int productId = 2;
        String productName = "spiderman";
        String productDescription = "the hero spiderman";
        String picturePath = "testimage.png";
        ArrayList<String> distributors = new ArrayList<>();
        distributors.add("marvel");

        Product product = new Product(productId, productName, productDescription, picturePath, distributors);    //new Product constructor

        assertTrue(productName.equals(product.getName()));
        assertTrue(productDescription.equals("the hero spiderman"));
        assertTrue(picturePath.equals("testimage.png"));
        assertEquals(productId, 2);
        assertTrue(distributors.get(0).equals("marvel"));
        //Product findProductOnID(int productID)
        //boolean validateProductInput(String productName, String productDescription, ArrayList<String> productDistributors) throws IllegalArgumentException
    }

    @Test
    public void testAddToProductList() {

        int productId = 1;
        String productName = "spiderman";
        String productDescription = "the hero spiderman";
        String picturePath = "testimageSpiderman.png";
        ArrayList<String> distributors = new ArrayList<>();
        distributors.add("marvel");

        Product product = new Product(productId, productName, productDescription, picturePath, distributors);
        Product.addToProductList(product);

        int productId2 = 3;
        String productName2 = "superman";
        String productDescription2 = "the hero superman";
        String picturePath2 = "testimageSuperman.png";
        ArrayList<String> distributors2 = new ArrayList<>();
        distributors2.add("marvel");

        Product product2 = new Product(productId2, productName2, productDescription2, picturePath2, distributors2);
        Product.addToProductList(product2);

        int productId3 = 5;
        String productName3 = "batman";
        String productDescription3 = "the hero batman";
        String picturePath3 = "testimageBatman.png";
        ArrayList<String> distributors3 = new ArrayList<>();
        distributors2.add("marvel");

        Product product3 = new Product(productId3, productName3, productDescription3, picturePath3, distributors3);
        Product.addToProductList(product3);

        assertEquals(Product.getProductList().size(), 3);
        assertTrue(Product.getProductList().get(1).getDescription().equals("the hero superman"));
    }

    @Test

    public void testDeleteProductOnID() {

        int productId = 6;
        String productName = "ironman";
        String productDescription = "the hero ironman";
        String picturePath = "testimageIronman.png";
        ArrayList<String> distributors = new ArrayList<>();
        distributors.add("marvel");

        Product product = new Product(productId, productName, productDescription, picturePath, distributors);
        Product.addToProductList(product);

        int productId2 = 7;
        String productName2 = "hulk";
        String productDescription2 = "the hero hulk";
        String picturePath2 = "testimagHulk.png";
        ArrayList<String> distributors2 = new ArrayList<>();
        distributors2.add("marvel");

        Product product2 = new Product(productId2, productName2, productDescription2, picturePath2, distributors2);
        Product.addToProductList(product2);

        int productId3 = 8;
        String productName3 = "wolverine";
        String productDescription3 = "the hero wolverine";
        String picturePath3 = "testimageWolverine.png";
        ArrayList<String> distributors3 = new ArrayList<>();
        distributors2.add("marvel");

        Product product3 = new Product(productId3, productName3, productDescription3, picturePath3, distributors3);
        Product.addToProductList(product3);

        Product.deleteProductOnID(7);
        Product.deleteProductOnID(8);

        assertEquals(Product.getProductList().size(), 1);

    }

    @Test

    public void testFindproductOnId() {

        int productId = 9;
        String productName = "magneto";
        String productDescription = "the hero magneto";
        String picturePath = "testimageMagneto.png";
        ArrayList<String> distributors = new ArrayList<>();
        distributors.add("marvel");

        Product product = new Product(productId, productName, productDescription, picturePath, distributors);
        Product.addToProductList(product);

        int productId2 = 10;
        String productName2 = "joker";
        String productDescription2 = "the hero joker";
        String picturePath2 = "testimageJoker.png";
        ArrayList<String> distributors2 = new ArrayList<>();
        distributors2.add("marvel");

        Product product2 = new Product(productId2, productName2, productDescription2, picturePath2, distributors2);
        Product.addToProductList(product2);

        int productId3 = 11;
        String productName3 = "captainA";
        String productDescription3 = "the hero captainA";
        String picturePath3 = "testimagecaptainA.png";
        ArrayList<String> distributors3 = new ArrayList<>();
        distributors3.add("marvel");

        Product product3 = new Product(productId3, productName3, productDescription3, picturePath3, distributors3);
        Product.addToProductList(product3);

        assertTrue(Product.findProductOnID(11).getDescription().equals("the hero captainA"));
        assertTrue(Product.findProductOnID(10).getPicturePath().equals("testimageJoker.png"));

    }

    @Test
    public void testNegativeFindproductOnIdNoMatchNotEmptyList() {

        int productID = 20;
        String productName = "joker";
        String productDescription = "the insane clown";
        String picturePath = "testimageJoker.png";
        ArrayList<String> distributors = new ArrayList(Arrays.asList(new String[]{"marvel"}));

        Product product = new Product(productID, productName, productDescription, picturePath, distributors);
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
