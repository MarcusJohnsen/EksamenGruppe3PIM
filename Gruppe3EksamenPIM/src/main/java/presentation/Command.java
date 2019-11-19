package presentation;

import businessLogic.BusinessFacade;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.commands.AddAttributeCommand;
import presentation.commands.AddCategoryCommand;
import presentation.commands.AddProductCommand;
import presentation.commands.DeleteCategoryCommand;
import presentation.commands.DeleteProductCommand;
import presentation.commands.EditCategoryCommand;
import presentation.commands.EditProductCommand;
import presentation.commands.SelectProductCommand;
import presentation.commands.GoToJspCommand;
import presentation.commands.SelectCategoryCommand;
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
        commands.put("goToJsp", new GoToJspCommand());
        commands.put("selectProduct", new SelectProductCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("deleteProduct", new DeleteProductCommand());
        commands.put("selectCategory", new SelectCategoryCommand());
        commands.put("deleteCategory", new DeleteCategoryCommand());
        commands.put("editCategory", new EditCategoryCommand());
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