package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String productChoiceString = request.getParameter("productChoice");
        if (productChoiceString != null) {

            int productChoice = Integer.parseInt(productChoiceString);
            Product product = businessController.getProductFromID(productChoice);

            if (commandType.equals("Edit Product")) {
                nextJsp = "editProduct";
            } else if (commandType.equals("Delete Product")) {
                nextJsp = "deleteProduct";
            } else if (commandType.equals("View all product details")) {
                nextJsp = "viewAllProductDetails";
            }

            request.setAttribute("product", product);

        } else {
            TreeSet<Product> productList = businessController.getProductList();
            request.setAttribute("productList", productList);
            nextJsp = "viewAllProducts";

            request.setAttribute("error", "No Product Selected!");
        }

        return nextJsp;
    }
}
