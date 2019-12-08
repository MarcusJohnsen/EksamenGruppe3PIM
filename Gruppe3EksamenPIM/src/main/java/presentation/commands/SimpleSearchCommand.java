package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.PIMObject;
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
        String nextJsp = "viewPIMObjectList";
        String pimObjectType = "Product";
        String searchString = request.getParameter("search");

        TreeSet<PIMObject> searchedProductsList = new TreeSet(businessFacade.searchProduct(searchString));
        
        request.setAttribute("PIMObjectList", searchedProductsList);
        request.setAttribute("PIMObjectType", pimObjectType);

        return nextJsp;
    }
}