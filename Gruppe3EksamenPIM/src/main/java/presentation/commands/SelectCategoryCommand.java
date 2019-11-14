/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author andre
 */
public class SelectCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String categoryChoiceString = request.getParameter("categoryChoice");

        if (categoryChoiceString != null) {

            int categoryChoice = Integer.parseInt(categoryChoiceString);

            if (commandType.equals("Edit Category")) {
                nextJsp = "editCategory";
            } else if (commandType.equals("Delete Category")) {
                nextJsp = "deleteCategory";
            }

            request.setAttribute("categoryID", categoryChoice);
        } else {
            nextJsp = "viewAllCategories";
            request.setAttribute("error", "No Category Selected!");
        }

        return nextJsp;
    }

}
