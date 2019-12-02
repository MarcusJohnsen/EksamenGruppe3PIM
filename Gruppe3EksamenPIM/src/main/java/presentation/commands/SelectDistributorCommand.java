package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Distributor;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectDistributorCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String distributorChoiceString = request.getParameter("distributorChoice");

        if (distributorChoiceString != null) {

            int distributorChoice = Integer.parseInt(distributorChoiceString);
            Distributor distributor = businessFacade.getDistributorFromID(distributorChoice);

            if (commandType.equals("Edit Distributor")) {
                nextJsp = "editDistributor";
            } else if (commandType.equals("Delete Distributor")) {
                nextJsp = "deleteDistributor";
            }

            request.setAttribute("distributor", distributor);
        } else {
            TreeSet<Distributor> distributorList = businessFacade.getDistributorList();
            request.setAttribute("distributorList", distributorList);
            nextJsp = "viewAllDistributors";
            
            request.setAttribute("error", "No Distributor Selected!");
        }
        return nextJsp;
    }
}