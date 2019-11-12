

package presentation.commands;

import businessLogic.Product;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class ViewProductsCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        String jspPage = "viewAllProducts";
    
        Product.setupProductsFromDB();
        
        ArrayList<Product> fullList = Product.getProductList();
        
        request.setAttribute("fullList", fullList);
     
        return jspPage;

    }

    

}
