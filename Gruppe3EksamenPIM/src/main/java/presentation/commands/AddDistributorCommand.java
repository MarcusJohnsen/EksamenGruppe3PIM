package presentation.commands;

import businessLogic.BusinessFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 * 
 * @author Marcus
 */
public class AddDistributorCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";
        
        String distributorName = request.getParameter("Distributor Name");
        String distributorDescription = request.getParameter("Distributor Description");
        try {
            businessFacade.createNewDistributor(distributorName, distributorDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newDistributor";
            request.setAttribute("error", ex.getMessage());
        }
        return nextJsp;
    }
}