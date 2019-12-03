/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class SimpleSearchCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = "viewAllProducts";
//        String commandType = request.getParameter("submitButton");
        String searchString = request.getParameter("search");

        TreeSet<Product> searchedProductsList = businessFacade.searchProduct(searchString);
        
        request.setAttribute("productList", searchedProductsList);

        return nextJsp;
    }

}
