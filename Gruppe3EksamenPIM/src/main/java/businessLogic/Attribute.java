package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Attribute {

    private int attributeID;
    private String attributeName;
    private HashMap<Integer, String> attributeValues;
    private static ArrayList<Attribute> attributeList = new ArrayList();

    public Attribute(int attributeID, String attributeTitle, HashMap<Integer, String> attributeValues) {
        this.attributeID = attributeID;
        this.attributeName = attributeTitle;
        this.attributeValues = attributeValues;
    }

    /**
     * 
     * @param AttributeListFromDB
     * Gets the list of Attribute-objects from the DataBase and stores them in an ArrayList. 
     */
    
    public static void setupAttributeListFromDB(ArrayList<Attribute> AttributeListFromDB) {
        attributeList = AttributeListFromDB;
    }

    /**
     * 
     * @param attributeID Traverses through the attributelist in search 
     * of a specific attribute based on its ID 
     * @return The attribute if it is found. If not found, a null value is returned.
     */
    
    public static Attribute findAttributeOnID(int attributeID) {
        for (Attribute attribute : attributeList) {
            if (attribute.getAttributeID() == attributeID) {
                return attribute;
            }
        }
        return null;
    }

    
    /**
     * 
     * @param attributeTitle Validates the attributeName. The given title must not be empty or already existing.
     * @return Boolean true if given title is not empty/already existing
     * @throw Exception if boolean is not true.
     */
    
    public static boolean validateNewAttributeTitle(String attributeTitle) {
        if (attributeTitle.isEmpty()) {
            throw new IllegalArgumentException("New Attribute need a title");
        }
        for (Attribute attribute : attributeList) {
            if (attribute.getAttributeName().equals(attributeTitle)) {
                throw new IllegalArgumentException("Dublicate attribute already exist");
            }
        }
        return true;
    }

    /**
     * 
     * @param attribute Adds a new attribute-object to the attributeList.
     * @return Boolean true if attribute is added to the list. 
     */
    
    public static boolean addToAttributeList(Attribute attribute) {
        return attributeList.add(attribute);
    }
    
    void editAttribute(String attributeName) {
        this.attributeName = attributeName;
    }
    
    public static boolean deleteAttribute(int attributeID) {
        return attributeList.remove(findAttributeOnID(attributeID));
    }

    /**
     * 
     * @param attributeChoices Traverses the attributeList in search of matching attributeID's.
     * If matching occur, the attribute object is added to the result-List.
     * @return The result-List. 
     */
    
    public static ArrayList<Attribute> getMatchingAttributesOnIDs(ArrayList<String> attributeChoices) {
        ArrayList<Attribute> result = new ArrayList();
        for (Attribute attribute : attributeList) {
            if (attributeChoices.contains(Integer.toString(attribute.getAttributeID()))) {
                result.add(attribute);
            }
        }
        return result;
    }

    /**
     * 
     * @return The attributeList 
     */
    public static ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }

    /**
     * 
     * @param value 
     * @param productID
     * Inserts new value into HashMap. Takes two parameters and uses them as a key/value pair.
     * @return boolean true if the new inserted value is not null.
     */
    
    public boolean insertValueIntoAttribute(String value, int productID) {
        String previousValue = this.attributeValues.put(productID, value);
        boolean hadPreviousValue = previousValue != null;
        return hadPreviousValue;
    }

    
    /**
     * 
     * @param productID
     * @return the corresponding value of (HashMap) productID-key. 
     */
    
    public String getAttributeValueForID(int productID) {
        return attributeValues.get(productID);
    }

    
    /**
     * 
     * @return attributeID
     */
    
    
    public int getAttributeID() {
        return attributeID;
    }

    
    /**
     * 
     * @return attributeName
     */
    
    public String getAttributeName() {
        return attributeName;
    }

    
    /**
     * 
     * @return HashMap of attributeValues
     */
    
    public HashMap<Integer, String> getAttributeValues() {
        return attributeValues;
    }
}