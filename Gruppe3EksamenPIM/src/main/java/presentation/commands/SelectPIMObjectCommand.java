package presentation.commands;

import businessLogic.BusinessController;
import businessLogic.Distributor;
import businessLogic.PIMObject;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectPIMObjectCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String nextJsp;
        String commandType = request.getParameter("submitButton");
        String pimObjectChoiceString = request.getParameter("PIMObjectChoice");
        
        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        if (pimObjectChoiceString != null) {
            int pimObjectChoice = Integer.parseInt(pimObjectChoiceString);

            setPIMObject(request, pimObjectType, businessController, pimObjectChoice);
            nextJsp = setNextJsp(request, businessController, commandType, pimObjectType, pimObjectChoice);

        } else {
            setPIMObjectList(request, pimObjectType, businessController);

            nextJsp = "viewPIMObjectList";

            request.setAttribute("error", "No " + pimObjectType + " Selected!");
        }
        return nextJsp;
    }

    private String setNextJsp(HttpServletRequest request, BusinessController businessController, String commandType, String pimObjectType, int pimObjectChoice) throws IllegalArgumentException {
        String nextJsp;
        switch (commandType) {
            case "Edit":
                nextJsp = setNextEditJsp(request, businessController, pimObjectType);
                break;
            case "Delete":
                nextJsp = "deletePIMObject";
                break;
            case "Select":
                nextJsp = "viewAllProductDetails";
                break;
            case "Bulk Edit":
                nextJsp = "bulkSelect";
                break;
            default:
                throw new IllegalArgumentException("Sub-command for Select PIM Object: " + commandType + " is not recognised");
        }
        return nextJsp;
    }

    private String setNextEditJsp(HttpServletRequest request, BusinessController businessController, String pimObjectType) throws IllegalArgumentException {
        String nextJsp;
        switch (pimObjectType) {
            case ("Attribute"):
                nextJsp = "editAttribute";
                break;
            case ("Bundle"):
                setPIMObjectListForBundleEdit(request, businessController);
                nextJsp = "editBundle";
                break;
            case ("Category"):
                nextJsp = "editCategory";
                break;
            case ("Distributor"):
                nextJsp = "editDistributor";
                break;
            case ("Product"):
                nextJsp = "editProduct";
                break;
            default:
                throw new IllegalArgumentException("PIM object type: " + pimObjectType + " is not recognised");
        }
        return nextJsp;
    }

    private void setPIMObject(HttpServletRequest request, String pimObjectType, BusinessController businessController, int pimObjectChoice) {
        PIMObject pimObject;
        switch (pimObjectType) {
            case ("Attribute"):
                pimObject = businessController.getAttributeFromID(pimObjectChoice);
                break;
            case ("Bundle"):
                pimObject = businessController.getBundleFromID(pimObjectChoice);
                break;
            case ("Category"):
                pimObject = businessController.getCategoryFromID(pimObjectChoice);
                break;
            case ("Distributor"):
                pimObject = businessController.getDistributorFromID(pimObjectChoice);
                break;
            case ("Product"):
                pimObject = businessController.getProductFromID(pimObjectChoice);
                break;
            default:
                throw new IllegalArgumentException("PIM object type: " + pimObjectType + " is not recognised");
        }
        request.setAttribute("pimObject", pimObject);
    }

    private void setPIMObjectList(HttpServletRequest request, String pimObjectType, BusinessController businessController) {
        TreeSet<PIMObject> pimObjectList;
        switch (pimObjectType) {
            case ("Attribute"):
                pimObjectList = new TreeSet(businessController.getAttributeList());
                break;
            case ("Bundle"):
                pimObjectList = new TreeSet(businessController.getBundleList());
                break;
            case ("Category"):
                pimObjectList = new TreeSet(businessController.getCategoryList());
                break;
            case ("Distributor"):
                pimObjectList = new TreeSet(businessController.getDistributorList());
                break;
            case ("Product"):
                pimObjectList = new TreeSet(businessController.getProductList());
                break;
            default:
                throw new IllegalArgumentException("PIM object type: " + pimObjectType + " is not recognised");
        }
        request.setAttribute("PIMObjectList", pimObjectList);
    }

    private void setPIMObjectListForBulkEdit(HttpServletRequest request, int pimObjectChoice, BusinessController businessController) {
        TreeSet<PIMObject> pimObjectList = new TreeSet(businessController.findProductsOnCategoryID(pimObjectChoice));
        request.setAttribute("PIMObjectList", pimObjectList);
    }
    
    private void setPIMObjectListForBundleEdit(HttpServletRequest request, BusinessController businessController){
        TreeSet<PIMObject> pimObjectList;
        pimObjectList = new TreeSet(businessController.getProductList());
        request.setAttribute("PIMObjectList", pimObjectList);
    }
}
