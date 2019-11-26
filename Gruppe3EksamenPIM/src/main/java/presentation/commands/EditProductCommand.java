package presentation.commands;

import businessLogic.Attribute;
import businessLogic.BusinessFacade;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        //ArrayList<Distributor> distributors = new ArrayList(Arrays.asList(request.getParameterValues("Product Distributors")));
        
        HashMap<Integer, String> productAttributeValues = new HashMap();
        ArrayList<Distributor> distributors = new ArrayList();
        Product product = businessFacade.getProductFromID(productID);
        for (Attribute productAttribute : product.getProductAttributes()) {
            int attributeValue = productAttribute.getAttributeID();
            productAttributeValues.put(attributeValue, request.getParameter("AttributeID" + attributeValue));
        }
        
        try {
            businessFacade.editProduct(productID, productName, productDescription, distributors, productAttributeValues);
            request.setAttribute("productList", businessFacade.getProductList());
        } catch (IllegalArgumentException ex) {
            nextJsp = "editProduct";
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("product", product);
        }
        return nextJsp;
    }
}
