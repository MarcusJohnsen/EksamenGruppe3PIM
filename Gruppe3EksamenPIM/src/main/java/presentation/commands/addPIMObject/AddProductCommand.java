package presentation.commands.addPIMObject;

import businessLogic.BusinessController;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class AddProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewAllProductDetails";

        // get parameters from request
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");

        // get parameters for choosen categories
        ArrayList<String> categoryChoices;
        if (request.getParameterValues("categoryChoices") != null) {
            categoryChoices = new ArrayList(Arrays.asList(request.getParameterValues("categoryChoices")));
        } else {
            categoryChoices = new ArrayList();
        }

        try {
            List<Part> parts = (List<Part>) request.getParts();
            // get parameters for choosen distributors, and throw error if th+ere is none
            ArrayList<String> distributorChoices;
            if (request.getParameterValues("distributorChoices") != null) {
                distributorChoices = new ArrayList(Arrays.asList(request.getParameterValues("distributorChoices")));
            } else {
                throw new IllegalArgumentException("Need at least 1 distributor");
            }
            Product newProduct = businessController.createNewProduct(productName, productDescription, distributorChoices, categoryChoices, parts);
            request.setAttribute("pimObject", newProduct);
        } catch (IllegalArgumentException ex) {
            TreeSet<Category> categoryList = businessController.getCategoryList();
            request.setAttribute("categoryList", categoryList);
            nextJsp = "newProduct";
            TreeSet<Distributor> distributorList = businessController.getDistributorList();
            request.setAttribute("distributorList", distributorList);
            request.setAttribute("error", ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextJsp;
    }
}
