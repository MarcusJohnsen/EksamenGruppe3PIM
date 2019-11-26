/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import businessLogic.Category;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class DeleteBundleCommand extends Command{
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String jspPage = "viewAllBundles";
        int bundleID = Integer.parseInt(request.getParameter("bundleID"));

        businessFacade.deleteBundle(bundleID);

        ArrayList<Bundle> bundleList = businessFacade.getBundleList();
        request.setAttribute("bundleList", bundleList);
        return jspPage;

    }
}
