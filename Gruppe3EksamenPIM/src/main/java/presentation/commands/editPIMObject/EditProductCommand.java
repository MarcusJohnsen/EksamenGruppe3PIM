package presentation.commands.editPIMObject;

import businessLogic.Attribute;
import businessLogic.BusinessController;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael N. Korsgaard
 */
public class EditProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewPIMObjectList";

        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        
        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        HashMap<Integer, String> productAttributeValues = new HashMap();
        Product product = businessController.getProductFromID(productID);
        for (Attribute productAttribute : product.getProductAttributes()) {
            int attributeValue = productAttribute.getObjectID();
            productAttributeValues.put(attributeValue, request.getParameter("AttributeID" + attributeValue));
        }
        
        try {
            ArrayList<String> distributorChoices;
            if (request.getParameterValues("distributorChoices") != null) {
                distributorChoices = new ArrayList(Arrays.asList(request.getParameterValues("distributorChoices")));
            } else {
                throw new IllegalArgumentException("Need at least 1 distributor");
            }
            businessController.editProduct(productID, productName, productDescription, distributorChoices, productAttributeValues);
            request.setAttribute("PIMObjectList", businessController.getProductList());
        } catch (IllegalArgumentException ex) {
            nextJsp = "editProduct";
            TreeSet<Distributor> distributorList = businessController.getDistributorList();
            request.setAttribute("PIMObjectList", distributorList);
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("pimObject", product);
        }
        return nextJsp;
    }
}