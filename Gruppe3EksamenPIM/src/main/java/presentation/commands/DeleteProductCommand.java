/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessController;
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
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String jspPage = "viewAllProducts";
        int productID = Integer.parseInt(request.getParameter("productID"));

        businessController.deleteProduct(productID);

        TreeSet<Product> productList = businessController.getProductList();
        request.setAttribute("productList", productList);
        return jspPage;
    }

}
