/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands.bulkEditPIMObject;

import businessLogic.Attribute;
import businessLogic.BusinessController;
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
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "index";

        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        Category category = businessController.getCategoryFromID(categoryID);

        try {
            String[] productChoices = (String[]) request.getSession().getAttribute("productChoices");
            ArrayList<Integer> productIDs = new ArrayList();
            for (String productChoice : productChoices) {
                productIDs.add(Integer.parseInt(productChoice));
            }

            HashMap<Integer, String> newAttributeValues = new HashMap();

            for (Attribute categoryAttribute : category.getCategoryAttributes()) {
                int attributeValue = categoryAttribute.getObjectID();
                String newAttributeValue = request.getParameter("AttributeID" + attributeValue);
                if (!newAttributeValue.equals("")) {
                    if (newAttributeValue.equals("DELETE") || newAttributeValue.equals("'DELETE'")) {
                        newAttributeValues.put(attributeValue, "");
                    } else {
                        newAttributeValues.put(attributeValue, newAttributeValue);
                    }
                }
            }

            businessController.bulkEdit(productIDs, newAttributeValues);

        } catch (IllegalArgumentException ex) {
            nextJsp = "bulkEdit";
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("category", category);
        }

        return nextJsp;
    }

}
