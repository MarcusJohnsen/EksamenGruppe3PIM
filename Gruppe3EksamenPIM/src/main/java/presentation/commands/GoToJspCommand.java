package presentation.commands;

import businessLogic.Attribute;
import businessLogic.Bundle;
import businessLogic.BusinessController;
import businessLogic.Category;
import businessLogic.Distributor;
import businessLogic.PIMObject;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

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
            case "viewPIMObjectList":
                setupPIMObjectList(request, businessController);
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

    private void setupPIMObjectList(HttpServletRequest request, BusinessController businessController) {

        String pimObjectType = request.getParameter("PIMObjectType");
        TreeSet<PIMObject> pimObjectList = new TreeSet();

        switch (pimObjectType) {
            case "Product":
                pimObjectList.addAll(businessController.getProductList());
                break;
            case "Category":
                pimObjectList.addAll(businessController.getCategoryList());
                break;
            case "Distributor":
                pimObjectList.addAll(businessController.getDistributorList());
                break;
            case "Bundle":
                pimObjectList.addAll(businessController.getBundleList());
                break;
            case "Attribute":
                pimObjectList.addAll(businessController.getAttributeList());
                break;
            default:
                throw new IllegalArgumentException("PIM object type is not recognised");
        }

        request.setAttribute("PIMObjectList", pimObjectList);
        request.setAttribute("PIMObjectType", pimObjectType);
    }

}
