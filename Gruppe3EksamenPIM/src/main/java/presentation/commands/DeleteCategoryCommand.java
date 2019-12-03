package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Category;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class DeleteCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String jspPage = "viewAllCategories";
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        businessController.deleteCategory(categoryID);

        TreeSet<Category> categoryList = businessController.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        return jspPage;

    }

}
