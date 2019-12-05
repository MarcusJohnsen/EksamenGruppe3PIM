package presentation.commands;

import businessLogic.BusinessController;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SearchSelectionCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = "advancedSearch";

        String objectChoice = request.getParameter("BusinessObjectType");

        ArrayList<String> filterOptions = new ArrayList();
        String filterListKey = "filterList";
        String product = "Product";
        String category = "Category";
        String distributor = "Distributor";
        String bundle = "Bundle";

        switch (objectChoice) {
            case "Product":
                filterOptions = new ArrayList(Arrays.asList(new String[]{category, distributor, bundle}));
                request.setAttribute("objectChoice", product);
                break;
            case "Category":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                request.setAttribute("objectChoice", category);
                break;
            case "Distributor":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                request.setAttribute("objectChoice", distributor);
                break;
            case "Bundle":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                request.setAttribute("objectChoice", bundle);
                break;
            default:
                throw new IllegalArgumentException("Cannot search on object type specificed: " + objectChoice);
        }
        request.setAttribute(filterListKey, filterOptions);
        return nextJsp;
    }
}