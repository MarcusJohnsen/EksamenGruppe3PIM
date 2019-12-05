package presentation;

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
        commands.put("addProduct", new AddProductCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("addAttribute", new AddAttributeCommand());
        commands.put("addDistributor", new AddDistributorCommand());
        commands.put("addBundle", new AddBundleCommand());

        commands.put("goToJsp", new GoToJspCommand());
        commands.put("simpleSearch", new SimpleSearchCommand());
        commands.put("advancedSearch", new AdvancedSearchCommand());
        commands.put("searchSelection", new SearchSelectionCommand());

        commands.put("selectProduct", new SelectProductCommand());
        commands.put("editProduct", new EditProductCommand());
        commands.put("deleteProduct", new DeleteProductCommand());

        commands.put("selectCategory", new SelectCategoryCommand());
        commands.put("editCategory", new EditCategoryCommand());
        commands.put("deleteCategory", new DeleteCategoryCommand());
        commands.put("bulkSelect", new BulkSelectCommand());
        commands.put("bulkEdit", new BulkEditCommand());

        commands.put("selectAttribute", new SelectAttributeCommand());
        commands.put("editAttribute", new EditAttributeCommand());
        commands.put("deleteAttribute", new DeleteAttributeCommand());

        commands.put("selectCategoriesForProduct", new SelectCategoriesForProductCommand());
        commands.put("selectAttributesForCategory", new SelectAttributesForCategoryCommand());
        commands.put("editCategoriesToProduct", new EditCategoriesToProductCommand());
        commands.put("editAttributesToCategory", new EditAttributesToCategoryCommand());

        commands.put("selectDistributor", new SelectDistributorCommand());
        commands.put("editDistributor", new EditDistributorCommand());
        commands.put("deleteDistributor", new DeleteDistributorCommand());

        commands.put("selectBundle", new SelectBundleCommand());
        commands.put("editBundle", new EditBundleCommand());
        commands.put("deleteBundle", new DeleteBundleCommand());
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
