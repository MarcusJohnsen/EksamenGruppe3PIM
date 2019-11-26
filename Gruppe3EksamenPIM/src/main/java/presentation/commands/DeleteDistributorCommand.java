package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Distributor;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class DeleteDistributorCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String jspPage = "viewAllDistributors";
        int distributorID = Integer.parseInt(request.getParameter("distributorID"));

        businessFacade.deleteDistributor(distributorID);

        ArrayList<Distributor> distributorList = businessFacade.getDistributorList();
        request.setAttribute("distributorList", distributorList);
        return jspPage;
    }
}