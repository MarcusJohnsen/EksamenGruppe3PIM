package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Category;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class SelectCategoriesForProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String nextJsp = "editCategoriesForProduct";
        int productID = Integer.parseInt(request.getParameter("productID"));
        
        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        Product product = businessController.getProductFromID(productID);
        TreeSet<Category> categoryList = businessController.getCategoryList();
        
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("product", product);
        
        return nextJsp;
    }

}
