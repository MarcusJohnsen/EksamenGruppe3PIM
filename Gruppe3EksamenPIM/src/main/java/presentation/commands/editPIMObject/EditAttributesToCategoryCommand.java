package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Category;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 * 
 * @author Marcus
 */
public class EditAttributesToCategoryCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "editCategory";
        ArrayList<String> attributeChoices;
        
        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);
        
        if(request.getParameterValues("attributeChoices") != null){
            attributeChoices = new ArrayList(Arrays.asList(request.getParameterValues("attributeChoices")));
        } else {
            attributeChoices = new ArrayList();
        }
        Category category = businessController.getCategoryFromID(Integer.parseInt(request.getParameter("pimObjectID")));
        
        businessController.editAttributesToCategory(category, attributeChoices);
        
        request.setAttribute("pimObject", category);
        
        return nextJsp;
    }
}