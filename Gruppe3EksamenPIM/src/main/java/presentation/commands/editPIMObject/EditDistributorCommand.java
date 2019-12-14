package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Distributor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditDistributorCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewPIMObjectList";
        request.setAttribute("PIMObjectList", businessController.getDistributorList());
        int distributorID = Integer.parseInt(request.getParameter("distributorID"));
        String distributorName = request.getParameter("Distributor Name");
        String distributorDescription = request.getParameter("Distributor Description");

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        try {
            businessController.editDistributor(distributorID, distributorName, distributorDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editDistributor";
            request.setAttribute("error", ex.getMessage());

            Distributor distributor = businessController.getDistributorFromID(distributorID);
            request.setAttribute("pimObject", distributor);
        }
        return nextJsp;
    }
}
