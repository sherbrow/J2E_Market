/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sherbrow
 */
public class FrontServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("text/html;charset=UTF-8");
        
        Map<String,String> router = new HashMap<String, String>();
        
        String pathInfo = request.getPathInfo();
        if("/".equals(pathInfo)) {
            forwardJsf("index", request, response); // Dev
            return;
        }
        
        List<String> parameterNamesList = new ArrayList<String>();
        
        Pattern urlPattern = Pattern.compile("([a-zA-Z]+)");
        parameterNamesList.add("controller");
        parameterNamesList.add("action");
        
        Matcher urlMatcher = urlPattern.matcher(pathInfo);
        urlMatcher.matches();
        if(urlMatcher.find()) {
            int supportedParametersCount =
                urlMatcher.groupCount() < parameterNamesList.size()?
                    urlMatcher.groupCount()
                    :parameterNamesList.size();

            for(int index = 0;index < supportedParametersCount; ++index) {
                String parameterValue = urlMatcher.group(index);
                String parameterName = parameterNamesList.get(index);
                router.put(parameterName, parameterValue);
            }
        }
        
        MvcController controller;
        String controllerName = router.get("controller");
        controllerName = controllerName.toUpperCase().charAt(0) + controllerName.toLowerCase().substring(1);
        controllerName = "web.controller."+controllerName+"Controller";
        String actionName = router.get("action");
        if(actionName == null || actionName.isEmpty()) actionName = "index";
        // TODO Set defaults if not found
        ActionResult actionResult = null;
        try {
                controller = (MvcController)(Class.forName(controllerName).newInstance());
                actionResult = controller.call(actionName, request, response);
        } catch (ClassNotFoundException e) {
                actionResult = new ActionResult("404");
        } catch (Exception e) {
                e.printStackTrace();
                actionResult = new ActionResult(e.getMessage());
        }
        
        if((actionResult.getType() & ActionResult.MASK_HTTPERROR) != 0) {
            switch(actionResult.getType() & ActionResult.MASK_HTTPERROR) {
                case ActionResult.HTTP_401:
                    response.setStatus(401);
                    break;
                case ActionResult.HTTP_403:
                    response.setStatus(403);
                    break;
                case ActionResult.HTTP_404:
                    response.setStatus(404);
                    break;
                case ActionResult.HTTP_500:
                default:
                    response.setStatus(500);
                    break;
            }
        }
        
        switch(actionResult.getType() & ActionResult.MASK_RENDERTYPE) {
            case ActionResult.TYPE_STRING:
                try {
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.print((String)actionResult.getResult());
                    outputStream.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case ActionResult.TYPE_JSP:
                String jspPageName = (String)actionResult.getResult();
                forwardJsp(jspPageName, request, response);
                break;
            case ActionResult.TYPE_JSF:
                String jsfPageName = (String)actionResult.getResult();
                forwardJsf(jsfPageName, request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
        return "Front Servlet";
    }
    // </editor-fold>

    private void forwardJsp(String jspPageName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/"+jspPageName+".jsp");
        rd.forward(request, response);
    }
    private void forwardJsf(String jsfPageName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/jsf/"+jsfPageName+".xhtml").forward(request, response);
    }
}
