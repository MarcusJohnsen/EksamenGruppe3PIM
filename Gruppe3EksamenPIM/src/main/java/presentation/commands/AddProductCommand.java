package presentation.commands;

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
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String nextJsp = "uploadImage";
        
        // get parameters from request
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        ArrayList<String> distributors = new ArrayList(Arrays.asList(request.getParameterValues("Product Distributors")));
        
        Product newProduct = Product.createNewProduct(productName, productDescription, "", distributors);
        request.getSession().setAttribute("productID", newProduct.getProductID());

        return nextJsp;
    }

}
