package presentation.commands;

import businessLogic.BusinessController;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class AdvancedSearchCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessFacade) {
        String nextJsp = "";
        
        TreeSet<Object> searchedProductsList = businessFacade.advancedSearch(nextJsp);
    
        
        
        return nextJsp;
    }
}