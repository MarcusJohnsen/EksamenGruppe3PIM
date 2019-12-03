package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessController;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class DeleteAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String jspPage = "viewAllAttributes";
        int attributeID = Integer.parseInt(request.getParameter("attributeID"));

        businessController.deleteAttribute(attributeID);

        TreeSet<Attribute> attributeList = businessController.getAttributeList();
        request.setAttribute("attributeList", attributeList);
        return jspPage;
    }
    

}