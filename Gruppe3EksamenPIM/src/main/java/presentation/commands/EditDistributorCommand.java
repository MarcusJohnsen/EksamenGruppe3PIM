package presentation.commands;

import businessLogic.BusinessController;
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
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewAllDistributors";
        request.setAttribute("distributorList", businessController.getDistributorList());
        int distributorID = Integer.parseInt(request.getParameter("distributorID"));
        String distributorName = request.getParameter("Distributor Name");
        String distributorDescription = request.getParameter("Distributor Description");

        try {
            businessController.editDistributor(distributorID, distributorName, distributorDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editDistributor";
            request.setAttribute("error", ex.getMessage());
            
            Distributor distributor = businessController.getDistributorFromID(distributorID);
            request.setAttribute("distributor", distributor);
        }
        return nextJsp;
    }
}