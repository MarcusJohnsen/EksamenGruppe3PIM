package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class AddProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = "uploadImage";

        // get parameters from request
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        ArrayList<String> distributors = new ArrayList(Arrays.asList(request.getParameterValues("Product Distributors")));

        try {
            Product newProduct = businessFacade.createNewProduct(productName, productDescription);
            request.getSession().setAttribute("productID", newProduct.getProductID());
        } catch (IllegalArgumentException ex) {
            nextJsp = "newProduct";
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }

}
