package businessLogic;

import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Marcus
 */

public class AttributeTest {

    @Before
    public void setup() {
        Attribute.getAttributeList().clear();
    }
    
    @Test
    public void testCreateAttribute() {
        //arrange
        int attributeID = 1;
        String attributeTitle = "weight";
        HashMap<Integer, String> attributeValues = new HashMap<>();
        attributeValues.put(1, "attribute number 1");
        
        //act
        Attribute attribute = new Attribute(attributeID, attributeTitle, attributeValues);
        Attribute.addToAttributeList(attribute);
        
        //assert
        assertEquals(attributeID, attribute.getAttributeID());
        assertTrue(attributeTitle.equals(attribute.getAttributeTitle()));
        assertTrue(attributeValues.equals(attribute.getAttributeValues()));
    }
    
    @Test
    public void testFindAttributeOnID() {
        //arrange
        int attributeID1 = 5;
        String attributeTitle1 = "weight";
        HashMap<Integer, String> attributeValues1 = new HashMap<>();
        attributeValues1.put(1, "attribute number 1");
        int attributeID2 = 6;
        String attributeTitle2 = "height";
        HashMap<Integer, String> attributeValues2 = new HashMap<>();
        attributeValues2.put(2, "attribute number 2");
        
        //act
        Attribute attribute1 = new Attribute(attributeID1, attributeTitle1, attributeValues1);
        Attribute.addToAttributeList(attribute1);
        Attribute attribute2 = new Attribute(attributeID2, attributeTitle2, attributeValues2);
        Attribute.addToAttributeList(attribute2);
        
        //assert
        assertTrue(Attribute.findAttributeOnID(5).getAttributeTitle().equals("weight"));
    }
    
    @Test
    public void negativeTestFindAttributeOnIDNull() {
        //arrange
        int attributeID = 1;
        String attributeTitle = "weight";
        HashMap<Integer, String> attributeValues = new HashMap<>();
        attributeValues.put(1, "attribute number 1");
        
        Attribute attribute = new Attribute (attributeID, attributeTitle, attributeValues);
        Attribute.addToAttributeList(attribute);
        
        //act
        Attribute result = Attribute.findAttributeOnID(10);
        
        assertNull(result);
    }
    
}