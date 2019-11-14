
package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class DeleteCategoryCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

       String jspPage = "viewAllCategories";
       int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        
        Category.deleteCategory(categoryID);
        
        return jspPage;

    }
    


}
