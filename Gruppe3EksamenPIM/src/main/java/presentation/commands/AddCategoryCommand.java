package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Marcus
 */
public class AddCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";

        String categoryName = request.getParameter("Category Name");
        String categoryDescription = request.getParameter("Category Description");
        ArrayList<String> attributeChoices;
        
         if(request.getParameterValues("attributeChoice") != null){
            attributeChoices = new ArrayList(Arrays.asList(request.getParameterValues("attributeChoice")));
        } else {
            attributeChoices = new ArrayList();
        }
        
        try {
            businessFacade.createNewCategory(categoryName, categoryDescription, attributeChoices);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newCategory";
            TreeSet<Attribute> attributeList = businessFacade.getAttributeList();
            request.setAttribute("attributeList", attributeList);
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
