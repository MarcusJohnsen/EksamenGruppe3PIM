package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class SimpleSearchCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = "viewAllProducts";
        
        String searchString = request.getParameter("search");

        TreeSet<Product> searchedProductsList = businessFacade.searchProduct(searchString);
        
        request.setAttribute("productList", searchedProductsList);

        return nextJsp;
    }
}