package businessLogic;

import factory.PIMObjectType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchEngineTest {

    private static final SearchEngine search = new SearchEngine();
    private static final TreeSet<Product> productList = new TreeSet();
    private static final TreeSet<Category> categoryList = new TreeSet();
    private static final TreeSet<Distributor> distributorList = new TreeSet();
    private static final TreeSet<Bundle> bundleList = new TreeSet();
    private static final HashMap<PIMObjectType, String> filterValues = new HashMap();
    private static Distributor distributor1, distributor2, distributor3;
    private static Category category1, category2, category3, category4;
    private static Product product1, product2, product3, product4, product5;
    private static Bundle bundle1, bundle2, bundle3;

    @BeforeClass
    public static void setupClass() {
        // Descriptions for business objects are not used for search, and as such, we can add a simple description to all business objects.
        String description = "This is a description";

        // Make Attributes
        // There are no search use for attributes, and as such, this list can be empty
        TreeSet<Attribute> attributeList = new TreeSet();

        // Make Distributors
        distributor1 = new Distributor(1, "Marvel", description);
        distributorList.add(distributor1);
        distributor2 = new Distributor(2, "DC", description);
        distributorList.add(distributor2);
        distributor3 = new Distributor(3, "Disney", description);
        distributorList.add(distributor3);
        search.setDistributorList(distributorList);

        // Make Categories
        category1 = new Category(1, "Superhero", description, attributeList);
        categoryList.add(category1);
        category2 = new Category(2, "Villain", description, attributeList);
        categoryList.add(category2);
        category3 = new Category(3, "Anti Hero", description, attributeList);
        categoryList.add(category3);
        category4 = new Category(4, "Side Character", description, attributeList);
        categoryList.add(category4);
        search.setCategoryList(categoryList);

        // Make Products
        TreeSet<Distributor> productDistributors;
        TreeSet<Category> productCategories;
        // There is not search on pictures for products, and as such, we can add a simple picturePath String to all business products.
        String picturePath = "image.png";

        productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor1, distributor3}));
        productCategories = new TreeSet(Arrays.asList(new Category[]{category1}));
        product1 = new Product(1, "Ironman", description, picturePath, productDistributors, productCategories);
        productList.add(product1);

        productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor1}));
        productCategories = new TreeSet(Arrays.asList(new Category[]{category1, category3}));
        product2 = new Product(2, "Deadpool", description, picturePath, productDistributors, productCategories);
        productList.add(product2);

        productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor2}));
        productCategories = new TreeSet(Arrays.asList(new Category[]{category1}));
        product3 = new Product(3, "Superman", description, picturePath, productDistributors, productCategories);
        productList.add(product3);

        productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor2}));
        productCategories = new TreeSet(Arrays.asList(new Category[]{category2}));
        product4 = new Product(4, "Joker", description, picturePath, productDistributors, productCategories);
        productList.add(product4);

        productDistributors = new TreeSet(Arrays.asList(new Distributor[]{distributor2}));
        productCategories = new TreeSet(Arrays.asList(new Category[]{category2, category3, category4}));
        product5 = new Product(5, "Harley Quinn", description, picturePath, productDistributors, productCategories);
        productList.add(product5);
        search.setProductList(productList);

        // Make Bundles
        HashMap<Product, Integer> bundleProducts;

        bundleProducts = new HashMap();
        bundleProducts.put(product4, 1);
        bundleProducts.put(product5, 1);
        bundle1 = new Bundle(1, "Team Joker", description, bundleProducts);
        bundleList.add(bundle1);

        bundleProducts = new HashMap();
        bundleProducts.put(product2, 1);
        bundleProducts.put(product5, 1);
        bundle2 = new Bundle(2, "Dream Team", description, bundleProducts);
        bundleList.add(bundle2);

        bundleProducts = new HashMap();
        bundleProducts.put(product2, 1);
        bundleProducts.put(product4, 1);
        bundleProducts.put(product5, 1);
        bundle3 = new Bundle(3, "R-rated", description, bundleProducts);
        bundleList.add(bundle3);
        search.setBundleList(bundleList);
    }

    @Before
    public void setup() {

        // Connect Lists in SearchEngine
        search.setupSearchEngine(productList, categoryList, distributorList, bundleList);

        // Empty filterValues from HashMap
        filterValues.clear();
    }

    @Test
    public void testSearchCorrect() {
        String searchString = "man";

        TreeSet<Product> result = search.simpleProductSearch(searchString);

        assertTrue(result.contains(product1));
        assertFalse(result.contains(product2));
        assertTrue(result.contains(product3));
        assertFalse(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testSearchEmptyString() {
        String searchString = "";

        TreeSet<Product> result = search.simpleProductSearch(searchString);

        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertTrue(result.contains(product3));
        assertTrue(result.contains(product4));
        assertTrue(result.contains(product5));
    }

    @Test
    public void testSearchOnID() {
        String searchString = "2";

        TreeSet<Product> result = search.simpleProductSearch(searchString);

        assertFalse(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        assertFalse(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testAdvancedSearchProductNoFilters() {
        String searchString = "o";
        String searchType = "Product";
        String bundleFilter = "    ";
        String categoryFilter = " ";
        String distributorFilter = "";
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        assertTrue(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testAdvancedSearchProductWithBundleFilters() {
        String searchString = "o";
        String searchType = "Product";
        String bundleFilter = "3";
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertFalse(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        assertTrue(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testAdvancedSearchProductWithCategoryFilters() {
        String searchString = "o";
        String searchType = "Product";
        String bundleFilter = "";
        String categoryFilter = "1";
        String distributorFilter = null;
        String productFilter = "     ";

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        assertFalse(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testAdvancedSearchProductWithDistributorFilters() {
        String searchString = "o";
        String searchType = "Product";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = "2";
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertFalse(result.contains(product1));
        assertFalse(result.contains(product2));
        assertFalse(result.contains(product3));
        assertTrue(result.contains(product4));
        assertFalse(result.contains(product5));
    }

    @Test
    public void testAdvancedSearchDistributorNoFilters() {
        String searchString = "d";
        String searchType = "Distributor";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertFalse(result.contains(distributor1));
        assertTrue(result.contains(distributor2));
        assertTrue(result.contains(distributor3));
    }

    @Test
    public void testAdvancedSearchDistributorWithProductFilter() {
        String searchString = "d";
        String searchType = "Distributor";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = "IrOnMaN";

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertFalse(result.contains(distributor1));
        assertFalse(result.contains(distributor2));
        assertTrue(result.contains(distributor3));
    }

    @Test
    public void testAdvancedSearchCategoryNoFilters() {
        String searchString = "H";
        String searchType = "Category";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(category1));
        assertFalse(result.contains(category2));
        assertTrue(result.contains(category3));
        assertTrue(result.contains(category4));
    }

    @Test
    public void testAdvancedSearchCategoryWithProductFilter() {
        String searchString = "H";
        String searchType = "Category";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = "o";

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(category1));
        assertFalse(result.contains(category2));
        assertTrue(result.contains(category3));
        assertFalse(result.contains(category4));
    }

    @Test
    public void testAdvancedSearchBundleNoFilters() {
        String searchString = "M";
        String searchType = "Bundle";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = null;

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(bundle1));
        assertTrue(result.contains(bundle2));
        assertFalse(result.contains(bundle3));

    }

    @Test
    public void testAdvancedSearchBundleWithProductFilter() {
        String searchString = "M";
        String searchType = "Bundle";
        String bundleFilter = null;
        String categoryFilter = null;
        String distributorFilter = null;
        String productFilter = "JOkER";

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertTrue(result.contains(bundle1));
        assertFalse(result.contains(bundle2));
        assertFalse(result.contains(bundle3));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAdvancedSearchWrongType() {
        String searchString = "";
        String searchType = "Bundle" + "e";
        filterValues.putAll(SearchEngine.makeFilterMap(null, "   ", "hello", ""));

        String bundleFilter = null;
        String categoryFilter = "   ";
        String distributorFilter = "hello";
        String productFilter = "";

        TreeSet<PIMObject> result = search.advancedSearch(searchString, searchType, bundleFilter, categoryFilter, distributorFilter, productFilter);
    }

    @Test
    public void testMakeFilterMap() {
        String bundleFilter = "b as banana";
        String categoryFilter = "c as call";
        String distributorFilter = "d as death";
        String productFilter = "p as person";

        HashMap<PIMObjectType, String> result = SearchEngine.makeFilterMap(bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertEquals(bundleFilter, result.get(PIMObjectType.BUNDLE));
        assertEquals(categoryFilter, result.get(PIMObjectType.CATEGORY));
        assertEquals(distributorFilter, result.get(PIMObjectType.DISTRIBUTOR));
        assertEquals(productFilter, result.get(PIMObjectType.PRODUCT));
    }

    @Test
    public void testMakeFilterMapWithNullValues() {
        String bundleFilter = "";
        String categoryFilter = "   ";
        String distributorFilter = null;
        String productFilter = null;

        HashMap<PIMObjectType, String> result = SearchEngine.makeFilterMap(bundleFilter, categoryFilter, distributorFilter, productFilter);

        assertEquals(bundleFilter, result.get(PIMObjectType.BUNDLE));
        assertEquals(categoryFilter, result.get(PIMObjectType.CATEGORY));
        assertEquals(distributorFilter, result.get(PIMObjectType.DISTRIBUTOR));
        assertEquals(productFilter, result.get(PIMObjectType.PRODUCT));
    }

}
