package presentation.commands;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.Product;
import java.util.TreeSet;
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

        TreeSet<Product> productList;
        TreeSet<Attribute> attributeList;
        TreeSet<Category> categoryList;
        TreeSet<Distributor> distributorList;
        TreeSet<Bundle> bundleList;

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
            case "viewAllAttributes":
                attributeList = businessFacade.getAttributeList();
                request.setAttribute("attributeList", attributeList);
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
                categoryList = businessFacade.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                distributorList = businessFacade.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "editProduct":
                distributorList = businessFacade.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "newCategory":
                attributeList = businessFacade.getAttributeList();
                request.setAttribute("attributeList", attributeList);
                break;    
            default:
                break;
        }
        return jspPage;
    }
}