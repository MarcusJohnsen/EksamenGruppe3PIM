/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Product;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael N. Korsgaard
 */
public class EditCategoriesToProductCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "editProduct";
        ArrayList<String> categoryChoices = new ArrayList(Arrays.asList(request.getParameterValues("categoryChoices")));
        Product product = businessFacade.getProductFromID(Integer.parseInt(request.getParameter("productID")));
        
        businessFacade.editCategoriesToProduct(product, categoryChoices);
        
        request.setAttribute("product", product);
        return nextJsp;
    }
    
}
