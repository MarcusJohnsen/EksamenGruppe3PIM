package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Distributor;
import businessLogic.PIMObject;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class DeletePIMObjectCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String jspPage = "viewPIMObjectList";

        String pimObjectType = request.getParameter("PIMObjectType");
        int PIMObjectID = Integer.parseInt(request.getParameter("PIMObjectID"));

        deletePIMObject(businessController, PIMObjectID, pimObjectType);
        setupPIMObjectList(request, businessController, pimObjectType);

        return jspPage;
    }

    private void deletePIMObject(BusinessController businessController, int PIMObjectID, String pimObjectType) {
        switch (pimObjectType) {
            case "Product":
                businessController.deleteProduct(PIMObjectID);
                break;
            case "Category":
                businessController.deleteCategory(PIMObjectID);
                break;
            case "Distributor":
                businessController.deleteDistributor(PIMObjectID);
                break;
            case "Bundle":
                businessController.deleteBundle(PIMObjectID);
                break;
            case "Attribute":
                businessController.deleteAttribute(PIMObjectID);
                break;
            default:
                throw new IllegalArgumentException("PIM object type is not recognised");
        }
    }

    private void setupPIMObjectList(HttpServletRequest request, BusinessController businessController, String pimObjectType) {

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
