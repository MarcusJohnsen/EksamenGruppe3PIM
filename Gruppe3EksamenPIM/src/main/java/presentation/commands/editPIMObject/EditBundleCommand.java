package presentation.commands.editPIMObject;

import businessLogic.Bundle;
import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditBundleCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "viewPIMObjectList";
        request.setAttribute("PIMObjectList", businessController.getBundleList());
        int bundleID = Integer.parseInt(request.getParameter("bundleID"));
        String bundleName = request.getParameter("Bundle Name");
        String bundleDescription = request.getParameter("Bundle Description");

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        try {

            String[] productChoiceStrings = request.getParameterValues("productChoice");
            HashMap<Integer, Integer> productChoices = new HashMap();
            if (productChoiceStrings != null) {
                ArrayList<Integer> productIDList = new ArrayList();
                for (String productChoice : productChoiceStrings) {
                    productIDList.add(Integer.parseInt(productChoice));
                }

                try {
                    for (Integer productID : productIDList) {
                        productChoices.put(productID, Integer.parseInt(request.getParameter("ProductIDAmount" + productID)));
                    }
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Please write only numbers for each product choosen");
                }
            } else {
                productChoices = null;
            }

            businessController.editBundle(bundleID, bundleName, bundleDescription, productChoices);

        } catch (IllegalArgumentException ex) {
            nextJsp = "editBundle";
            request.setAttribute("error", ex.getMessage());

            TreeSet<Product> productList = businessController.getProductList();
            request.setAttribute("PIMObjectList", productList);

            Bundle bundle = businessController.getBundleFromID(bundleID);
            request.setAttribute("pimObject", bundle);
        }
        return nextJsp;
    }
}
