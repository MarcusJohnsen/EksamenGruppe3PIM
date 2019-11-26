package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Distributor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 * 
 * @author Marcus
 */
public class EditDistributorCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "viewAllDistributors";
        request.setAttribute("distributorList", businessFacade.getDistributorList());
        int distributorID = Integer.parseInt(request.getParameter("distributorID"));
        String distributorName = request.getParameter("Distributor Name");
        String distributorDescription = request.getParameter("Distributor Description");

        try {
            businessFacade.editDistributor(distributorID, distributorName, distributorDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editDistributor";
            request.setAttribute("error", ex.getMessage());
            
            Distributor distributor = businessFacade.getDistributorFromID(distributorID);
            request.setAttribute("distributor", distributor);
        }
        return nextJsp;
    }
}