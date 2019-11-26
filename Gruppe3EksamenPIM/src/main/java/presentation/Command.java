package presentation;

import businessLogic.BusinessFacade;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.commands.AddAttributeCommand;
import presentation.commands.AddCategoryCommand;
import presentation.commands.AddDistributorCommand;
import presentation.commands.EditCategoriesToProductCommand;
import presentation.commands.AddProductCommand;
import presentation.commands.DeleteCategoryCommand;
import presentation.commands.DeleteDistributorCommand;
import presentation.commands.DeleteProductCommand;
import presentation.commands.EditAttributesToCategoryCommand;
import presentation.commands.EditCategoryCommand;
import presentation.commands.EditDistributorCommand;
import presentation.commands.EditProductCommand;
import presentation.commands.SelectProductCommand;
import presentation.commands.GoToJspCommand;
import presentation.commands.SelectAttributesForCategoryCommand;
import presentation.commands.SelectCategoriesForProductCommand;
import presentation.commands.SelectCategoryCommand;
import presentation.commands.SelectDistributorCommand;
import presentation.commands.UnknownCommand;

/**
 *
 * @author cahit
 */
public abstract class Command {
    
    private static HashMap<String, Command> commands;
   
  private static void initCommands() {
        
        commands = new HashMap<>();
        commands.put("addProduct", new AddProductCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("addAttribute", new AddAttributeCommand());
        commands.put("addDistributor", new AddDistributorCommand());
        commands.put("goToJsp", new GoToJspCommand());
        commands.put("selectProduct", new SelectProductCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("deleteProduct", new DeleteProductCommand());
        commands.put("selectCategory", new SelectCategoryCommand());
        commands.put("deleteCategory", new DeleteCategoryCommand());
        commands.put("selectCategoriesForProduct", new SelectCategoriesForProductCommand());
        commands.put("selectAttributesForCategory", new SelectAttributesForCategoryCommand());
        commands.put("editCategory", new EditCategoryCommand());
        commands.put("editCategoriesToProduct", new EditCategoriesToProductCommand());
        commands.put("editAttributesToCategory", new EditAttributesToCategoryCommand());
        commands.put("selectDistributor", new SelectDistributorCommand());
        commands.put("editDistributor", new EditDistributorCommand());
        commands.put("deleteDistributor", new DeleteDistributorCommand());
    }

  public static Command from(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand());
        //return null;
    }
    public abstract String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade);
}  