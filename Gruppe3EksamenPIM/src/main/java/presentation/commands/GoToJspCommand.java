package presentation.commands;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.BusinessController;
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
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String jspPage = request.getParameter("goToJsp");

        TreeSet<Product> productList;
        TreeSet<Attribute> attributeList;
        TreeSet<Category> categoryList;
        TreeSet<Distributor> distributorList;
        TreeSet<Bundle> bundleList;

        switch (jspPage) {
            case "viewAllProducts":
                productList = businessController.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "viewAllCategories":
                categoryList = businessController.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                break;
            case "viewAllDistributors":
                distributorList = businessController.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "viewAllBundles":
                bundleList = businessController.getBundleList();
                request.setAttribute("bundleList", bundleList);
                break;
            case "viewAllAttributes":
                attributeList = businessController.getAttributeList();
                request.setAttribute("attributeList", attributeList);
                break;
            case "newBundle":
                productList = businessController.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "editBundle":
                productList = businessController.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "newProduct":
                categoryList = businessController.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                distributorList = businessController.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "editProduct":
                distributorList = businessController.getDistributorList();
                request.setAttribute("distributorList", distributorList);
                break;
            case "newCategory":
                attributeList = businessController.getAttributeList();
                request.setAttribute("attributeList", attributeList);
                break;    
            default:
                break;
        }
        return jspPage;
    }
}