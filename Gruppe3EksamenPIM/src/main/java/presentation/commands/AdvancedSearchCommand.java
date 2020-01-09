package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.PIMObject;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class AdvancedSearchCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = "viewPIMObjectList";

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        String searchResult = request.getParameter("searchKey");

        String bundleFilter = request.getParameter("BundleChoice");
        String categoryFilter = request.getParameter("CategoryChoice");
        String distributorFilter = request.getParameter("DistributorChoice");
        String productFilter = request.getParameter("ProductChoice");

        TreeSet<PIMObject> searchedObjectsList = businessFacade.advancedSearch(searchResult, pimObjectType, bundleFilter, categoryFilter, distributorFilter, productFilter);
        request.setAttribute("PIMObjectList", searchedObjectsList);

        return nextJsp;
    }
}
