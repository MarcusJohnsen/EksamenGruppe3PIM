

package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class JspViewProductsCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        String jspPage = request.getParameter("viewProducts");
    
        
        
        return jspPage;

    }

    

}
