package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class SelectAttributesForCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "selectAttributesForCategory";
        
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        Category category = businessFacade.getCategoryFromID(categoryID);
        ArrayList<Category> categoryList = businessFacade.getCategoryList();
        
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("category", category);
        
        return nextJsp;
    }

}
