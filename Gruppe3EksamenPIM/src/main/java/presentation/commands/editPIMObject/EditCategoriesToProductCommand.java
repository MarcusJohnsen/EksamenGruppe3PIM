package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditCategoriesToProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "editProduct";
        ArrayList<String> categoryChoices;

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        if (request.getParameterValues("categoryChoices") != null) {
            categoryChoices = new ArrayList(Arrays.asList(request.getParameterValues("categoryChoices")));
        } else {
            categoryChoices = new ArrayList();
        }

        Product product = businessController.getProductFromID(Integer.parseInt(request.getParameter("pimObjectID")));
        businessController.editCategoriesToProduct(product, categoryChoices);

        request.setAttribute("pimObject", product);
        return nextJsp;
    }
}
