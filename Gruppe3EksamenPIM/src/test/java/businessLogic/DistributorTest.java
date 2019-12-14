package businessLogic;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class DistributorTest {

    @Before
    public void setup() {

        Distributor.getDistributorList().clear();
    }

    @Test
    public void testDistributorConstructor() {

        //arrange
        int distributorID = 1;
        String distributorName = "Haribo";
        String distributorDescription = "Sweet for sweeties";

        //act
        Distributor result = new Distributor(distributorID, distributorName, distributorDescription);
        Distributor.addToDistributorList(result);

        //assert
        assertTrue(distributorName.equals(result.objectTitle));
        assertTrue(distributorDescription.equals(result.objectDescription));
        assertEquals(distributorID, result.objectID);
        assertTrue(Distributor.getDistributorList().contains(result));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateDistributorInputWhereDistributorNameIsEmpty() {

        //arrange   
        String distributorName = "";
        String distributorDescription = "Sweet for sweeties";
        //act

        boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);
        //assert

        assertTrue(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateDistributorInputWhereDistributorDescriptionIsEmpty() {

        //arrange  
        String distributorName = "Haribo";
        String distributorDescription = "";

        //act
        boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);

        //assert
        assertTrue(result);
    }

    @Test
    public void testValidateDistributorInput() {

        //arrange      
        String distributorName = "Haribo";
        String distributorDescription = "Sweet for sweeties";

        //act
        boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);

        //assert
        assertTrue(result);
    }

    @Test
    public void testEditDistributor() {

        //arrange   
        int distributorID = 1;
        String distributorName = "Haribo";
        String distributorDescription = "Sweet for sweeties";
        String name = "Changed-Name from Haribo";
        String description = "Changed-Description from Sweet for sweeties";

        //act
        Distributor result = new Distributor(distributorID, distributorName, distributorDescription);
        result.editDistributor(name, description);

        //assert
        assertTrue(result.objectTitle.equals(name));
        assertTrue(result.objectDescription.equals(description));

    }

    @Test
    public void testNegativeFindDistributorOnID() {
        int distributorIDNotInUse = 1000;

        Distributor distributor = Distributor.findDistributorOnID(distributorIDNotInUse);

        assertNull(distributor);
    }

}
