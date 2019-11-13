package presentation.commands;

import businessLogic.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 * 
 * @author Marcus
 */
public class AddCategoryCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String nextJsp = "index";
        
        String categoryName = request.getParameter("Category Name");
        String categoryDescription = request.getParameter("Category Description");
        
        Category newCategory = Category.createNewCategory(categoryName, categoryDescription);
        request.getSession().setAttribute("categoryID", newCategory.getCategoryID());
        
        return nextJsp;
    }
}