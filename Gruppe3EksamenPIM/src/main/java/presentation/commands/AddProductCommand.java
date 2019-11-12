package presentation.commands;

import businessLogic.Product;
import java.util.ArrayList;
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
        
        //FrontController.uploadFile(request, response);
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        String[] distributers = request.getParameterValues("Product Distributors");
        ArrayList<String> productDistributors = new ArrayList();
        Collections.addAll(productDistributors, distributers);
        
        Product newProduct = Product.createNewProduct(productName, productDescription, "", productDistributors);
        request.getSession().setAttribute("productID", newProduct.getProductID());

        return nextJsp;
    }

}
