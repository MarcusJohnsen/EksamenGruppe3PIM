package presentation;

import presentation.commands.bulkEditPIMObject.BulkSelectCommand;
import presentation.commands.bulkEditPIMObject.BulkEditCommand;
import presentation.commands.editPIMObject.EditCategoriesToProductCommand;
import presentation.commands.editPIMObject.EditDistributorCommand;
import presentation.commands.editPIMObject.EditAttributesToCategoryCommand;
import presentation.commands.editPIMObject.EditCategoryCommand;
import presentation.commands.editPIMObject.EditProductCommand;
import presentation.commands.editPIMObject.EditAttributeCommand;
import presentation.commands.editPIMObject.EditBundleCommand;
import presentation.commands.addPIMObject.AddAttributeCommand;
import presentation.commands.addPIMObject.AddBundleCommand;
import presentation.commands.addPIMObject.AddCategoryCommand;
import presentation.commands.addPIMObject.AddProductCommand;
import presentation.commands.addPIMObject.AddDistributorCommand;
import businessLogic.BusinessController;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.commands.*;

/**
 *
 * @author cahit
 */
public abstract class Command {

    private static HashMap<String, Command> commands;

    private static void initCommands() {

        commands = new HashMap<>();

        commands.put("goToJsp", new GoToJspCommand());

        commands.put("simpleSearch", new SimpleSearchCommand());
        commands.put("searchSelection", new SearchSelectionCommand());
        commands.put("advancedSearch", new AdvancedSearchCommand());

        commands.put("addProduct", new AddProductCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("addAttribute", new AddAttributeCommand());
        commands.put("addDistributor", new AddDistributorCommand());
        commands.put("addBundle", new AddBundleCommand());

        commands.put("editAttribute", new EditAttributeCommand());
        commands.put("editBundle", new EditBundleCommand());
        commands.put("editCategory", new EditCategoryCommand());
        commands.put("editDistributor", new EditDistributorCommand());
        commands.put("editProduct", new EditProductCommand());

        commands.put("deletePIMObject", new DeletePIMObjectCommand());
        commands.put("selectPIMObject", new SelectPIMObjectCommand());

        commands.put("selectAttributesForCategory", new SelectAttributesForCategoryCommand());
        commands.put("selectCategoriesForProduct", new SelectCategoriesForProductCommand());
        commands.put("editCategoriesToProduct", new EditCategoriesToProductCommand());
        commands.put("editAttributesToCategory", new EditAttributesToCategoryCommand());

        commands.put("bulkSelect", new BulkSelectCommand());
        commands.put("bulkEdit", new BulkEditCommand());

    }

    public static Command from(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand());
    }

    public static boolean doCommandNeedParts(Command command) {
        return command.getClass() == AddProductCommand.class;
    }

    public abstract String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController);
}
