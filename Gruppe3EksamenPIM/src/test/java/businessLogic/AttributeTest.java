package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
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
        assertTrue(attributeTitle.equals(attribute.getAttributeName()));
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
        assertTrue(Attribute.findAttributeOnID(5).getAttributeName().equals("weight"));
        assertTrue(Attribute.findAttributeOnID(6).getAttributeName().equals("height"));
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
    
    @Test (expected = IllegalArgumentException.class)
    public void negativValidationAttributeTitle() {
        //arrange
        String attributeTitle = "";
        
        //act
        Attribute.validateNewAttributeTitle(attributeTitle);
    }
    
    @Test   (expected = IllegalArgumentException.class)
    public void testValidateAttributeTitle() {
        //arrange
        int attributeID = 1;
        String attributeTitle = "weight";
        HashMap<Integer, String> attributeValues = new HashMap<>();
        attributeValues.put(1, "attribute number 1");
        Attribute attribute = new Attribute (attributeID, attributeTitle, attributeValues);
        Attribute.addToAttributeList(attribute);
        
        //act
        Attribute.validateNewAttributeTitle(attributeTitle);
    }
    
    @Test
    public void testGetMatchingAttributesOnIDs() {
        //arrange
        int attributeID1 = 11;
        int attributeID2 = 12;
        int attributeID3 = 13;
        Attribute attribute1 = new Attribute(attributeID1, "Have", new HashMap());
        Attribute attribute2 = new Attribute(attributeID2, "Electronic", new HashMap());
        Attribute attribute3 = new Attribute(attributeID3, "Mad", new HashMap());
        Attribute.addToAttributeList(attribute1);
        Attribute.addToAttributeList(attribute2);
        Attribute.addToAttributeList(attribute3);
        ArrayList<String> searchAttributeIDs = new ArrayList(Arrays.asList(new String[]{Integer.toString(attributeID1), Integer.toString(attributeID2)}));
        
        //act
        TreeSet<Attribute> result = Attribute.getMatchingAttributesOnStringIDs(searchAttributeIDs);
        
        assertTrue(result.contains(attribute1));
        assertTrue(result.contains(attribute2));
        assertFalse(result.contains(attribute3));
        
    }
    
    
    
}