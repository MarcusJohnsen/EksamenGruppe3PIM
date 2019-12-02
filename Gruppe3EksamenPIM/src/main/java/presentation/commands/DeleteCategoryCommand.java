package presentation.commands;

import businessLogic.BusinessFacade;
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
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String jspPage = "viewAllCategories";
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        businessFacade.deleteCategory(categoryID);

        TreeSet<Category> categoryList = businessFacade.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        return jspPage;

    }

}
