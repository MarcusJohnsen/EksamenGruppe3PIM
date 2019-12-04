package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Attribute implements Comparable<Attribute> {

    private int attributeID;
    private String attributeName;
    private HashMap<Integer, String> attributeValues;
    private static TreeSet<Attribute> attributeList = new TreeSet();

    public Attribute(int attributeID, String attributeTitle, HashMap<Integer, String> attributeValues) {
        this.attributeID = attributeID;
        this.attributeName = attributeTitle;
        this.attributeValues = attributeValues;
    }

    /**
     *
     * @param AttributeListFromDB Gets the list of Attribute-objects from the DataBase and stores them in a TreeSet.
     */
    public static void setupAttributeListFromDB(TreeSet<Attribute> AttributeListFromDB) {
        attributeList = AttributeListFromDB;
    }

    /**
     *
     * @param attributeID Traverses through the attributelist in search of a specific attribute based on its ID
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

    public static void bulkEditProducts(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues) {
        if (newAttributeValues.isEmpty()) {
            throw new IllegalArgumentException("Nothing to edit");
        }
        TreeSet<Attribute> attributeNeedingEdit = Attribute.getMatchingAttributesOnIDs(newAttributeValues.keySet());
        for (Attribute attribute : attributeNeedingEdit) {
            for (Integer productID : productIDs) {
                attribute.attributeValues.put(productID, newAttributeValues.get(attribute.attributeID));
            }
        }
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
     * @param attributeChoices Traverses the attributeList in search of matching attributeID's. If matching occur, the attribute object is added to the result-List.
     * @return The result-List.
     */
    public static TreeSet<Attribute> getMatchingAttributesOnStringIDs(ArrayList<String> attributeChoices) {
        TreeSet<Attribute> result = new TreeSet();
        for (Attribute attribute : attributeList) {
            if (attributeChoices.contains(Integer.toString(attribute.getAttributeID()))) {
                result.add(attribute);
            }
        }
        return result;
    }

    public static TreeSet<Attribute> getMatchingAttributesOnIDs(Set<Integer> attributeChoices) {
        TreeSet<Attribute> result = new TreeSet();
        for (Attribute attribute : attributeList) {
            if (attributeChoices.contains(attribute.getAttributeID())) {
                result.add(attribute);
            }
        }
        return result;
    }

    /**
     *
     * @return The attributeList
     */
    public static TreeSet<Attribute> getAttributeList() {
        return attributeList;
    }

    /**
     *
     * @param value
     * @param productID Inserts new value into HashMap. Takes two parameters and uses them as a key/value pair.
     * @return boolean true if the new inserted value is not null.
     */
    public boolean insertValueIntoAttribute(String value, int productID) {
        String previousValue = this.attributeValues.put(productID, value);
        boolean hadPreviousValue = previousValue != null;
        return hadPreviousValue;
    }
    
    public void removeValueFromAttribute(int productID){
        this.attributeValues.remove(productID);
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

    @Override
    public int compareTo(Attribute otherAttribute) {

        int thisID = this.attributeID;
        int otherID = otherAttribute.attributeID;
        return thisID - otherID;

    }
}
