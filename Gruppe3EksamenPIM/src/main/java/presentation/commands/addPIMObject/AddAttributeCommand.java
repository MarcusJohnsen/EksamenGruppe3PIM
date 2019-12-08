package presentation.commands.addPIMObject;

import businessLogic.BusinessController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael
 */
public class AddAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "index";

        String attributeTitle = request.getParameter("attributeName");
        try {
            businessController.createNewAttribute(attributeTitle);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newAttribute";
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
