/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class AddBundleCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";

        String bundleName = request.getParameter("Bundle Name");
        String bundleDescription = request.getParameter("Bundle Description");
        try {
            businessFacade.createNewBundle(bundleName, bundleDescription, null);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newBundle";
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
