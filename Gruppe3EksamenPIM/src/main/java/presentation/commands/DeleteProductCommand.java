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
 * @author Michael N. Korsgaard
 */
public class DeleteProductCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String jspPage = "viewAllProducts";
        int productID = Integer.parseInt(request.getParameter("productID"));

        businessFacade.deleteProduct(productID);

        TreeSet<Product> productList = businessFacade.getProductList();
        request.setAttribute("productList", productList);
        return jspPage;
    }

}
