package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class EditProductCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String nextJsp = "editProduct";
        
        String productChoice = request.getParameter("productchoice");
        
        return nextJsp;
    }
}