package presentation.commands.addPIMObject;

import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class AddBundleCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "index";

        String bundleName = request.getParameter("Bundle Name");
        String bundleDescription = request.getParameter("Bundle Description");

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

            businessController.createNewBundle(bundleName, bundleDescription, productChoices);

        } catch (IllegalArgumentException ex) {
            nextJsp = "newBundle";
            TreeSet<Product> productList = businessController.getProductList();
            request.setAttribute("productList", productList);
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
