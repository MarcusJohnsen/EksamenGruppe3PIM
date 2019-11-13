
package presentation;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.commands.AddProductCommand;
import presentation.commands.DeleteProductCommand;
import presentation.commands.EditProductCommand;
import presentation.commands.SelectProductCommand;
import presentation.commands.GoToJspCommand;
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
        commands.put("goToJsp", new GoToJspCommand());
        commands.put("selectProduct", new SelectProductCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("deleteProduct", new DeleteProductCommand());
    }
 
  
  
  public static Command from(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand());
        //return null;
    }

    public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}  

