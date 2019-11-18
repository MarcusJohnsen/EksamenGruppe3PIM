/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Attribute {
    
    private int attributeID;
    private String attributeTitle;
    private HashMap<Integer, String> attributeValues;
    private static ArrayList<Attribute> attributeList = new ArrayList();

    public Attribute(int attributeID, String attributeTitle, HashMap<Integer, String> attributeValues) {
        this.attributeID = attributeID;
        this.attributeTitle = attributeTitle;
        this.attributeValues = attributeValues;
    }
    
    public static Attribute findAttributeOnID(int attributeID){
        for (Attribute attribute : attributeList) {
            if(attribute.getAttributeID() == attributeID){
                return attribute;
            }
        }
        return null;
    }
    
    public static boolean validateNewAttributeTitle(String attributeTitle){
        if(attributeTitle.isEmpty()){
            throw new IllegalArgumentException("New Attribute need a title");
        }
        for (Attribute attribute : attributeList) {
            if(attribute.getAttributeTitle().equals(attributeTitle)){
                throw new IllegalArgumentException("Dublicate attribute already exist");
            }
        }
        return true;
    }
    
    public static boolean validateNewAttributeValue(String attributeValue){
        if(attributeValue.isEmpty()){
            throw new IllegalArgumentException("Can't insert empty value");
        }
        return true;
    }
    
    public static boolean addToAttributeList(Attribute attribute){
        return attributeList.add(attribute);
    }

    public static ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }
    
    public boolean insertValueIntoAttribute (String value, int productID){
        boolean hadPreviousValue = attributeValues.put(productID, value) != null;
        return hadPreviousValue;
    }
    
    public String getAttributeValueForID(int productID) {
        return attributeValues.get(productID);
    }

    public int getAttributeID() {
        return attributeID;
    }

    public String getAttributeTitle() {
        return attributeTitle;
    }
    
}
