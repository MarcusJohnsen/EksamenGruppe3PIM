package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Product;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class SelectCategoriesForProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = "editCategoriesForProduct";
        int productID = Integer.parseInt(request.getParameter("productID"));

        Product product = businessFacade.getProductFromID(productID);
        ArrayList<Category> categoryList = businessFacade.getCategoryList();
        
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("product", product);
        
        return nextJsp;
    }

}
