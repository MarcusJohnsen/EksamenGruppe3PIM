package presentation.commands;

import businessLogic.BusinessController;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class AdvancedSearchCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = null;
        
        // sorry det ikke virker endnu Michael, jeg fejrer min fødselsdag, og kan ikke finde ud af programmering alligevel xD
        
        String searchResult = request.getParameter("searchComplete");
        String objectType = request.getParameter("objectChoice");
        
        String bundleFilter = request.getParameter("BundleChoice");
        String categoryFilter = request.getParameter("CategoryChoice");
        String distributorFilter = request.getParameter("DistributorChoice");
        String productFilter = request.getParameter("ProductChoice");

        TreeSet<Object> searchedObjectsList = businessFacade.advancedSearch(searchResult, objectType,bundleFilter, categoryFilter, distributorFilter, productFilter);
        
        switch (objectType) {
            case "Product":
                break;
            case "Category":
                break;
            case "Distributor":
                break;
            case "Bundle":
                break;
            default:
                throw new IllegalArgumentException("Cannot search on object type specificed: " + objectType);
        }
        return nextJsp;
    }
}