package businessLogic;

import java.util.ArrayList;
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
        assertTrue(Attribute.findAttributeOnID(6).getAttributeTitle().equals("height"));
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
//        //arrange
//        int attributeID = 1;
//        String attributeTitle = "Have";
//        Attribute attribute = new Attribute(int, String, HashMap<>);
//        int attributeID2 = 2;
//        String attributeTitle2 = "Electronic";
//        HashMap<Integer, String> attributeValues = new HashMap<>();
//        ArrayList<String> attributeChoices = new ArrayList();
//        attributeChoices.add(attributeTitle);
//        attributeChoices.add(attributeTitle2);
//        
//        Attribute.getMatchingAttributesOnIDs(attributeChoices);
        
        
    }
}