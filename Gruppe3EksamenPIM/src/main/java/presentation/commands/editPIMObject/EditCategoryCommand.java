package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewPIMObjectList";
        request.setAttribute("PIMObjectList", businessController.getCategoryList());
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("Category Name");
        String categoryDescription = request.getParameter("Category Description");

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        try {
            businessController.editCategory(categoryID, categoryName, categoryDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editCategory";
            request.setAttribute("error", ex.getMessage());

            Category category = businessController.getCategoryFromID(categoryID);
            request.setAttribute("pimObject", category);
        }
        return nextJsp;
    }
}
