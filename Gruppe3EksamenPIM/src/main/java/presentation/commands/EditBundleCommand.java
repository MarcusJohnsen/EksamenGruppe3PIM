/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class EditBundleCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "viewAllBundles";
        request.setAttribute("bundleList", businessFacade.getBundleList());
        int bundleID = Integer.parseInt(request.getParameter("bundleID"));
        String bundleName = request.getParameter("Bundle Name");
        String bundleDescription = request.getParameter("Bundle Description");

        try {

            String[] productChoiceStrings = request.getParameterValues("productChoice");
            HashMap<Integer, Integer> productChoices = new HashMap();
            if (productChoiceStrings != null) {
                ArrayList<Integer> productIDList = new ArrayList();
                for (String productChoice : productChoiceStrings) {
                    productIDList.add(Integer.parseInt(productChoice));
                }

                try {
                    for (Integer productID : productIDList) {
                        productChoices.put(productID, Integer.parseInt(request.getParameter("ProductIDAmount" + productID)));
                    }
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Please write only numbers for each product choosen");
                }
            } else {
                productChoices = null;
            }

            businessFacade.editBundle(bundleID, bundleName, bundleDescription, productChoices);

        } catch (IllegalArgumentException ex) {
            nextJsp = "editBundle";
            request.setAttribute("error", ex.getMessage());

            TreeSet<Product> productList = businessFacade.getProductList();
            request.setAttribute("productList", productList);

            Bundle bundle = businessFacade.getBundleFromID(bundleID);
            request.setAttribute("bundle", bundle);
        }
        return nextJsp;
    }
}
