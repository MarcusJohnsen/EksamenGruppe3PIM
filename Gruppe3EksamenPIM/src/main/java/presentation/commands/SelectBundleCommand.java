/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class SelectBundleCommand extends Command {
   
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String bundleChoiceString = request.getParameter("bundleChoice");
        if (bundleChoiceString != null) {

            int bundleChoice = Integer.parseInt(bundleChoiceString);
            Bundle bundle = businessFacade.getBundleFromID(bundleChoice);

            if (commandType.equals("Edit Bundle")) {
                nextJsp = "editBundle";
            } else if (commandType.equals("Delete Bundle")) {
                nextJsp = "deleteBundle";
            }

            request.setAttribute("bundle", bundle);

        } else {
            ArrayList<Bundle> bundleList = businessFacade.getBundleList();
            request.setAttribute("bundleList", bundleList);
            nextJsp = "viewAllBundles";

            request.setAttribute("error", "No Bundle Selected!");
        }

        return nextJsp;
    }
}
