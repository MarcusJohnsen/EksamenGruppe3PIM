package presentation.commands.editPIMObject;

import businessLogic.Attribute;
import businessLogic.BusinessController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewPIMObjectList";
        
        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        int attributeID = Integer.parseInt(request.getParameter("attributeID"));
        String attributeName = request.getParameter("Attribute Name");

        try {
            businessController.editAttribute(attributeID, attributeName);
            request.setAttribute("PIMObjectList", businessController.getAttributeList());
        } catch (IllegalArgumentException ex) {
            nextJsp = "editAttribute";
            request.setAttribute("error", ex.getMessage());

            Attribute attribute = businessController.getAttributeFromID(attributeID);
            request.setAttribute("pimObject", attribute);
        }
        return nextJsp;
    }

}
