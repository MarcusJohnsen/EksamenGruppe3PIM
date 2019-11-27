package presentation.commands;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class GoToJspCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String jspPage = request.getParameter("goToJsp");

        ArrayList<Product> productList;
        ArrayList<Attribute> attributeList;
        ArrayList<Category> categoryList;
        ArrayList<Distributor> distributorList;
        ArrayList<Bundle> bundleList;

        switch (jspPage) {
            case "viewAllProducts":
                productList = businessFacade.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "viewAllCategories":
                categoryList = businessFacade.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                break;
            case "viewAllDistributors":
                distributorList = businessFacade.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "viewAllBundles":
                bundleList = businessFacade.getBundleList();
                request.setAttribute("bundleList", bundleList);
                break;
            case "newBundle":
                productList = businessFacade.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "editBundle":
                productList = businessFacade.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "newProduct":
                distributorList = businessFacade.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            default:
                break;

        }

        return jspPage;
    }

}
