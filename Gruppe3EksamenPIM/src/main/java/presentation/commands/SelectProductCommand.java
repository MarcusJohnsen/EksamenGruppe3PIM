package presentation.commands;

import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String productChoiceString = request.getParameter("productChoice");
        if (productChoiceString != null) {

            int productChoice = Integer.parseInt(productChoiceString);
            
            if (commandType.equals("Edit Product")) {
                nextJsp = "editProduct";
            } else if (commandType.equals("Delete Product")) {
                nextJsp = "deleteProduct";
            }
            
            request.setAttribute("productID", productChoice);

        } else {
            nextJsp = "viewAllProducts";
            request.setAttribute("error", "No Product Selected!");
        }

        return nextJsp;
    }
}
