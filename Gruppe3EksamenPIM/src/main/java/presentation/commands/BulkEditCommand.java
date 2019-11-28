/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael N. Korsgaard
 */
public class BulkEditCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";

        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        Category category = businessFacade.getCategoryFromID(categoryID);

        try {
            ArrayList<Integer> productIDs = new ArrayList();
            ArrayList<Product> productList = businessFacade.findProductsOnCategoryID(categoryID);
            for (Product product : productList) {
                productIDs.add(product.getProductID());
            }

            HashMap<Integer, String> newAttributeValues = new HashMap();

            for (Attribute categoryAttribute : category.getCategoryAttributes()) {
                int attributeValue = categoryAttribute.getAttributeID();
                String newAttributeValue = request.getParameter("AttributeID" + attributeValue);
                if (!newAttributeValue.toLowerCase().equals("*placeholder*")) {
                    newAttributeValues.put(attributeValue, newAttributeValue);
                }
            }

            businessFacade.bulkEdit(productIDs, newAttributeValues);

        } catch (IllegalArgumentException ex) {
            nextJsp = "bulkEdit";
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("category", category);
        }

        return nextJsp;
    }

}
