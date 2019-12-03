package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewAllAttributes";
        request.setAttribute("attributeList", businessController.getAttributeList());
        int attributeID = Integer.parseInt(request.getParameter("attributeID"));
        String attributeName = request.getParameter("Attribute Name");

        try {
            businessController.editAttribute(attributeID, attributeName);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editAttribute";
            request.setAttribute("error", ex.getMessage());
            
            Attribute attribute = businessController.getAttributeFromID(attributeID);
            request.setAttribute("attribute", attribute);
        }
        return nextJsp;
    }

}