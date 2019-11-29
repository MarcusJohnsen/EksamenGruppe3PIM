package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String productChoiceString = request.getParameter("productChoice");
        if (productChoiceString != null) {

            int productChoice = Integer.parseInt(productChoiceString);
            Product product = businessFacade.getProductFromID(productChoice);

            if (commandType.equals("Edit Product")) {
                nextJsp = "editProduct";
            } else if (commandType.equals("Delete Product")) {
                nextJsp = "deleteProduct";
            } else if (commandType.equals("View all product details")) {
                nextJsp = "viewAllProductDetails";
            }

            request.setAttribute("product", product);

        } else {
            ArrayList<Product> productList = businessFacade.getProductList();
            request.setAttribute("productList", productList);
            nextJsp = "viewAllProducts";

            request.setAttribute("error", "No Product Selected!");
        }

        return nextJsp;
    }
}
