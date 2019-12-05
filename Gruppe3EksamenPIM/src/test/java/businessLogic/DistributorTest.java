
package businessLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author cahit
 */
public class DistributorTest {
   
    @Before
    public void setup() {
    
        Distributor.getDistributorList().clear();
    }
    
    @Test
    public void testDistributorConstructor(){
    
     //arrange
     int distributorID = 1;
     String distributorName = "Haribo";
     String distributorDescription = "Sweet for sweeties";
       
     //act
     Distributor result = new Distributor(distributorID, distributorName, distributorDescription);
     Distributor.addToDistributorList(result);
     
     //assert
     assertTrue(distributorName.equals(result.getDistributorName()));
     assertTrue(distributorDescription.equals(result.getDistributorDescription()));
     assertEquals(distributorID,result.getDistributorID());
     assertTrue(Distributor.getDistributorList().contains(result));   
    
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateDistributorInputWhereDistributorNameIsEmpty(){
     
     //arrange   
     
     String distributorName = "";
     String distributorDescription = "Sweet for sweeties";
     //act
     
     boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);
     //assert
     
     assertTrue(result);
    
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValidateDistributorInputWhereDistributorDescriptionIsEmpty(){
      
      //arrange  
     String distributorName = "Haribo";
     String distributorDescription = "";
       
     //act
     boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);
     
     //assert
     assertTrue(result);
    }
    
    @Test
    public void testValidateDistributorInput(){
      
     //arrange      
     String distributorName = "Haribo";
     String distributorDescription = "Sweet for sweeties";
     
     //act
     boolean result = Distributor.validateDistributorInput(distributorName, distributorDescription);
     
     //assert
     assertTrue(result);
    }
    
    @Test
    public void testEditDistributor(){
    
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
     assertTrue(result.getDistributorName().equals(name));
     assertTrue(result.getDistributorDescription().equals(description));
     
    }
    
    @Test
    public void testTopTenDistributors(){
        
        
        
    }
    
    
}
