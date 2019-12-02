package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
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

        // get parameters for choosen categories
        ArrayList<String> categoryChoices;
        if(request.getParameterValues("categoryChoices") != null){
            categoryChoices = new ArrayList(Arrays.asList(request.getParameterValues("categoryChoices")));
        } else {
            categoryChoices = new ArrayList();
        }

        try {
            // get parameters for choosen distributors, and throw error if there is none
            ArrayList<String> distributorChoices;
            if (request.getParameterValues("distributorChoices") != null) {
                distributorChoices = new ArrayList(Arrays.asList(request.getParameterValues("distributorChoices")));
            } else {
                throw new IllegalArgumentException("Need at least 1 distributor");
            }
            
            Product newProduct = businessFacade.createNewProduct(productName, productDescription, distributorChoices, categoryChoices);
            request.getSession().setAttribute("productID", newProduct.getProductID());
            
        } catch (IllegalArgumentException ex) {
            TreeSet<Category> categoryList = businessFacade.getCategoryList();
            request.setAttribute("categoryList", categoryList);
            nextJsp = "newProduct";
            TreeSet<Distributor> distributorList = businessFacade.getDistributorList();
            request.setAttribute("distributorList", distributorList);
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }

}
