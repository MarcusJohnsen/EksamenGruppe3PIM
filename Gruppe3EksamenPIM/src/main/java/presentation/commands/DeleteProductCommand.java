/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Michael N. Korsgaard
 */
public class DeleteProductCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String jspPage = "viewAllProducts";
        int productID = Integer.parseInt(request.getParameter("productID"));
        
        Product.deleteProductOnID(productID);

        return jspPage;
    }
    
}
