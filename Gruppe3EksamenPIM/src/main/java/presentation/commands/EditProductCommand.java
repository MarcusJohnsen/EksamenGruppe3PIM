package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael N. Korsgaard
 */
public class EditProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "viewAllProducts";
        request.setAttribute("productList", businessFacade.getProductList());
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        ArrayList<String> distributors = new ArrayList(Arrays.asList(request.getParameterValues("Product Distributors")));
        ArrayList<String> attributes = new ArrayList(Arrays.asList(request.getParameterValues("Product Attributes")));

        try {
            businessFacade.editProduct(productID, productName, productDescription, distributors, attributes);
        } catch (IllegalArgumentException ex) {
            nextJsp = "editProduct";
            request.setAttribute("error", ex.getMessage());
            
            Product product = businessFacade.getProductFromID(productID);
            request.setAttribute("product", product);
        }
        return nextJsp;
    }
}
