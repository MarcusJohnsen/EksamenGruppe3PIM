package presentation.commands;

import businessLogic.BusinessFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael
 */
public class AddAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";

        String attributeTitle = request.getParameter("Attribute Name");
        try {
//            businessFacade.createNewAttribute(attributeTitle);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newAttribute";
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
