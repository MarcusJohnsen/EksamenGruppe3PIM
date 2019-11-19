package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Attribute;
import businessLogic.Category;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Marcus
 */
public class SelectAttributesForCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        
        String nextJsp = "editAttributeToCategory";
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        
        Category category = businessFacade.getCategoryFromID(categoryID);
        ArrayList<Attribute> attributeList = businessFacade.getAttributeList();
        
        request.setAttribute("attributeList", attributeList);
        request.setAttribute("category", category);
        
        return nextJsp;
    }
}