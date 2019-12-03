package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Distributor;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectDistributorCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String distributorChoiceString = request.getParameter("distributorChoice");

        if (distributorChoiceString != null) {

            int distributorChoice = Integer.parseInt(distributorChoiceString);
            Distributor distributor = businessController.getDistributorFromID(distributorChoice);

            if (commandType.equals("Edit Distributor")) {
                nextJsp = "editDistributor";
            } else if (commandType.equals("Delete Distributor")) {
                nextJsp = "deleteDistributor";
            }

            request.setAttribute("distributor", distributor);
        } else {
            TreeSet<Distributor> distributorList = businessController.getDistributorList();
            request.setAttribute("distributorList", distributorList);
            nextJsp = "viewAllDistributors";
            
            request.setAttribute("error", "No Distributor Selected!");
        }
        return nextJsp;
    }
}