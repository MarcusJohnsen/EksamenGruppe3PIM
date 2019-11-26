/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import businessLogic.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class EditBundleCommand extends Command{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "viewAllBundle";
        request.setAttribute("bundleList", businessFacade.getBundleList());
        int bundleID = Integer.parseInt(request.getParameter("bundleID"));
        String bundleName = request.getParameter("Bundle Name");
        String bundleDescription = request.getParameter("Bundle Description");

        try {
            businessFacade.editBundle(bundleID, bundleName, bundleDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editBundle";
            request.setAttribute("error", ex.getMessage());
            
            Bundle bundle = businessFacade.getBundleFromID(bundleID);
            request.setAttribute("bundle", bundle);
        }
        return nextJsp;
    }
}
