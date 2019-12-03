package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Distributor;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class DeleteDistributorCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String jspPage = "viewAllDistributors";
        int distributorID = Integer.parseInt(request.getParameter("distributorID"));

        businessController.deleteDistributor(distributorID);

        TreeSet<Distributor> distributorList = businessController.getDistributorList();
        request.setAttribute("distributorList", distributorList);
        return jspPage;
    }
}