/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

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
public class EditProductCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String nextJsp = "viewAllProducts";
        
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("Product Name");
        String productDescription = request.getParameter("Product Description");
        ArrayList<String> distributors = new ArrayList(Arrays.asList(request.getParameterValues("Product Distributors")));
        
        Product.editProduct(productID, productName, productDescription, distributors);
        
        return nextJsp;
    }
    
}
