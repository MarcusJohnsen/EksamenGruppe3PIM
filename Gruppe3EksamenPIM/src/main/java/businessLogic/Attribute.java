package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Attribute extends PIMObject {

    private HashMap<Integer, String> attributeValues;
    private static TreeSet<Attribute> attributeList = new TreeSet();

    public Attribute(int attributeID, String attributeTitle, HashMap<Integer, String> attributeValues) {
        super(attributeID, attributeTitle, null);
        this.attributeValues = attributeValues;
    }

    public static void setupAttributeListFromDB(TreeSet<Attribute> AttributeListFromDB) {
        attributeList = AttributeListFromDB;
    }

    /**
     * Searches thought the static attributeList from the Attribute class, searching for an attribute with a ID matching the search parameter.
     *
     * //TODO: Move this method into SearchEngine class.
     *
     * @param attributeID an int searchKey used to find an attribute with a matching ID.
     * @return The attribute if it is found. If not found, a null value is returned.
     */
    public static Attribute findAttributeOnID(int attributeID) {
        for (Attribute attribute : attributeList) {
            if (attribute.objectID == attributeID) {
                return attribute;
            }
        }
        return null;
    }

    /**
     * Edit multiple attribute values on multiple different products.
     *
     * @param productIDs List of IDs for products that should have their corresponding attribute values changed.
     * @param newAttributeValues a hashMap with ints matching attribute IDs as keys to their new String values.
     * @throws IllegalArgumentException, if newAttributeValues is empty.
     */
    public static void bulkEditProducts(ArrayList<Integer> productIDs, HashMap<Integer, String> newAttributeValues) {
        if (newAttributeValues.isEmpty()) {
            throw new IllegalArgumentException("Nothing to edit");
        }
        TreeSet<Attribute> attributeNeedingEdit = Attribute.getMatchingAttributesOnIDs(newAttributeValues.keySet());
        for (Attribute attribute : attributeNeedingEdit) {
            for (Integer productID : productIDs) {
                attribute.attributeValues.put(productID, newAttributeValues.get(attribute.objectID));
            }
        }
    }

    /**
     * Check if user input is valid for businessLogic parameters for the attributeTitle.
     *
     * @param attributeTitle user input for attribute title.
     * @return Boolean true if given title is not empty/already existing.
     * @throw Exception if user input is not valid, with message explaining what businesslogic was violated.
     */
    public static boolean validateNewAttributeTitle(String attributeTitle) {
        if (attributeTitle.isEmpty()) {
            throw new IllegalArgumentException("New Attribute need a title");
        }
        for (Attribute attribute : attributeList) {
            if (attribute.objectTitle.equals(attributeTitle)) {
                throw new IllegalArgumentException("Dublicate attribute already exist");
            }
        }
        return true;
    }

    public static boolean addToAttributeList(Attribute attribute) {
        return attributeList.add(attribute);
    }

    public void editAttribute(String attributeTitle) {
        this.objectTitle = attributeTitle;
    }

    public static boolean deleteAttribute(int attributeID) {
        return attributeList.remove(findAttributeOnID(attributeID));
    }

    /**
     * get TreeSet of attributes for all attributes with ID matching one of the search-Strings in attributeChoices
     *
     * //TODO: Move this method into SearchEngine class.
     *
     * @param attributeChoices String list of attributeIDs
     * @return TreeSet with all attributes with matching IDs to search.
     */
    public static TreeSet<Attribute> getMatchingAttributesOnStringIDs(ArrayList<String> attributeChoices) {
        TreeSet<Attribute> result = new TreeSet();
        for (Attribute attribute : attributeList) {
            if (attributeChoices.contains(Integer.toString(attribute.objectID))) {
                result.add(attribute);
            }
        }
        return result;
    }

    /**
     * get TreeSet of attributes for all attributes with ID matching one of the search-ints in attributeChoices
     *
     * //TODO: Move this method into SearchEngine class.
     *
     * @param attributeChoices int set of attributeIDs
     * @return TreeSet with all attributes with matching IDs to search.
     */
    public static TreeSet<Attribute> getMatchingAttributesOnIDs(Set<Integer> attributeChoices) {
        TreeSet<Attribute> result = new TreeSet();
        for (Attribute attribute : attributeList) {
            if (attributeChoices.contains(attribute.objectID)) {
                result.add(attribute);
            }
        }
        return result;
    }

    public static TreeSet<Attribute> getAttributeList() {
        return attributeList;
    }

    /**
     * Insert a value for a product to the corresponding attribute
     *
     * @param value String value for product
     * @param productID id for the product the value should be held at.
     * @return boolean true if the attribute had a previous value for the product.
     */
    public boolean insertValueIntoAttribute(String value, int productID) {
        String previousValue = this.attributeValues.put(productID, value);
        boolean hadPreviousValue = previousValue != null;
        return hadPreviousValue;
    }

    public void removeValueFromAttribute(int productID) {
        this.attributeValues.remove(productID);
    }

    public String getAttributeValueForID(int productID) {
        return attributeValues.get(productID);
    }

    public HashMap<Integer, String> getAttributeValues() {
        return attributeValues;
    }
}
