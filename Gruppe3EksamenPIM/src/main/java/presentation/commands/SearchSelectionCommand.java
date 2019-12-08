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

        String PIMObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", PIMObjectType);

        ArrayList<String> filterOptions = new ArrayList();
        String filterListKey = "filterList";
        String product = "Product";
        String category = "Category";
        String distributor = "Distributor";
        String bundle = "Bundle";

        switch (PIMObjectType) {
            case "Product":
                filterOptions = new ArrayList(Arrays.asList(new String[]{category, distributor, bundle}));
                break;
            case "Category":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                break;
            case "Distributor":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                break;
            case "Bundle":
                filterOptions = new ArrayList(Arrays.asList(new String[]{product}));
                break;
            default:
                throw new IllegalArgumentException("Cannot search on object type specificed: " + PIMObjectType);
        }

        request.setAttribute(filterListKey, filterOptions);
        return nextJsp;
    }
}
