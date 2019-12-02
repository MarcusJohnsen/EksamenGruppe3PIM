package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessFacade;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectAttributeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String attributeChoiceString = request.getParameter("attributeChoice");
        if (attributeChoiceString != null) {

            int attributeChoice = Integer.parseInt(attributeChoiceString);
            Attribute attribute = businessFacade.getAttributeFromID(attributeChoice);

            if (commandType.equals("Edit Attribute")) {
                nextJsp = "editAttribute";
            } else if (commandType.equals("Delete Attribute")) {
                nextJsp = "deleteAttribute";
            }

            request.setAttribute("attribute", attribute);

        } else {
            TreeSet<Attribute> attributeList = businessFacade.getAttributeList();
            request.setAttribute("attributeList", attributeList);
            nextJsp = "viewAllAttributes";

            request.setAttribute("error", "No Attribute Selected!");
        }
        return nextJsp;
    }
}