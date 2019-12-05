package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class AdvancedSearchCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = "viewAllProducts";
    
        String searchChoiceProduct = request.getParameter("productSearch");
        String searchChoiceCategory = request.getParameter("categoryChoice");
        String searchChoiceDistributor = request.getParameter("distributorChoice");
        String searchChoiceBundle = request.getParameter("bundleChoice");
        
        TreeSet<Product> searchedProductsList = businessFacade.advancedSearch(searchChoiceProduct);
        
        request.setAttribute("productList", searchedProductsList);
        
        return nextJsp;
    }
}