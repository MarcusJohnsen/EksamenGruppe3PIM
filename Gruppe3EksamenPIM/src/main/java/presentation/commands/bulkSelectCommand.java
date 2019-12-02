/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class bulkSelectCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "bulkEdit";
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        Category category = businessFacade.getCategoryFromID(categoryID);

        try {
            String[] productChoices = request.getParameterValues("productChoice");
            if (productChoices == null) {
                throw new IllegalArgumentException("Need at least 1 product to perform bulk edit");
            }
            request.getSession().setAttribute("productChoices", productChoices);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            TreeSet<Product> productList = businessFacade.findProductsOnCategoryID(categoryID);
            request.setAttribute("productList", productList);
            nextJsp = "bulkSelect";
        }

        request.setAttribute("category", category);
        return nextJsp;
    }

}
