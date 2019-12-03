/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businessLogic.BusinessController;
import factory.SystemMode;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael N. Korsgaard
 */
@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
@MultipartConfig(fileSizeThreshold = 500000, // this equals around 0,5 MB
        maxFileSize = 1048576L, // this equals 1 MB
        maxRequestSize = 5242880 // this equals 5 MB
)
public class FrontController extends HttpServlet {

    private static final SystemMode systemMode = SystemMode.PRODUCTION;
    private static BusinessController businessController;
    private static boolean needSetup = true;

    public static void setup() {
        if (needSetup) {
            businessController = new BusinessController(systemMode);
            businessController.setupListsFromDB();
            needSetup = false;
        }
    }

    public static BusinessController getBusinessController() {
        return businessController;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        try {
            setup();
            
            Command cmd = Command.from(request);
            if(Command.doCommandNeedParts(cmd)){
                request.setAttribute("partList", request.getParts());
            }
            String view = cmd.execute(request, response, businessController);
            if (view.equals("index")) {
                request.getRequestDispatcher(view + ".jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
            }
//        } catch (Exception ex) {
//            request.setAttribute("error", ex.getMessage());
//            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
