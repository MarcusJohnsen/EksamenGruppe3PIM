/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class EditCategoryCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "viewAllCategory";

        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("Category Name");
        String categoryDescription = request.getParameter("Category Description");

        try {
            businessFacade.editCategory(categoryID, categoryName, categoryDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editCategory";
            request.setAttribute("error", ex.getMessage());
            
            Category category = businessFacade.getCategoryFromID(categoryID);
            request.setAttribute("category", category);
        }
        return nextJsp;
    }
}
