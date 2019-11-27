package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessFacade;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class DeleteAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String jspPage = "viewAllAttributes";
        int attributeID = Integer.parseInt(request.getParameter("attributeID"));

        businessFacade.deleteAttribute(attributeID);

        ArrayList<Attribute> attributeList = businessFacade.getAttributeList();
        request.setAttribute("attributeList", attributeList);
        return jspPage;
    }
    

}