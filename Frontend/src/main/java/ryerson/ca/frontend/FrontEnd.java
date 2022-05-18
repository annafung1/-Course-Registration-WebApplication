/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.frontend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ryerson.ca.business.Business;
import ryerson.ca.helper.CoursesXML;

/**
 *
 * @author student
 */
@WebServlet(name = "FrontEnd", urlPatterns = {"/FrontEnd"})
public class FrontEnd extends HttpServlet {

    Authenticate autho;

    public FrontEnd() {
        autho = new Authenticate();
    }
    private final String authenticationCookieName = "login_token";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Map.Entry<String, String> isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        
        System.out.println("TOKEN IS");
        try {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if (cookie.getName().equals(authenticationCookieName)) {
                    token = cookie.getValue();
                }
            }
        } catch (Exception e) {

        }
        if (!token.isEmpty())
           try {
            if (this.autho.verify(token).getKey()) {
                  Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>
                             (token,this.autho.verify(token).getValue());
            return entry;

            } else {
                 Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }

       Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = isAuthenticated(request).getKey();
        String uname = isAuthenticated(request).getValue();
        String hiddenParam = request.getParameter("pageName"); //grabs the userinput from html form called pagename; refer to the html page  
        switch (hiddenParam) {
            //if user logins in set vars, make new cookie for their session, and lastly launch jsp page
            //user can waitlist courses iff login 
            case "login":
                String username = request.getParameter("username");
                String passwrod = request.getParameter("passwrod");
                boolean isAuthenticated = Business.isAuthenticated(username, passwrod);
                if (isAuthenticated) {
                       request.setAttribute("username", username);  //set attribute from the form to the var username orange=from html black=var
                    token = autho.createJWT("FrontEnd", username, 100000); 

                    Cookie newCookie = new Cookie(authenticationCookieName, token);
                    response.addCookie(newCookie);
                    RequestDispatcher requestDispatcher = request.
                            getRequestDispatcher("frontpageWithLogin.jsp");

                    requestDispatcher.forward(request, response);

                }
                break;
                
                   //if user does not login launch jsp page for no login: note you cannot waitlist courses
            case "search":

                CoursesXML result;
                String query = request.getParameter("query");
                if (token.isEmpty()) {
                    result = retreiveServicesFromBackend(query, null);
                    request.setAttribute("courseResults", result);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontpageWithoutLogin.jsp");

                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    request.setAttribute("username", uname);
                    result = retreiveServicesFromBackend(query, token);

                    request.setAttribute("courseResults", result);

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontpageWithLogin.jsp");

                    requestDispatcher.forward(request, response);
                }
                break;
        }

        
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

    private CoursesXML retreiveServicesFromBackend(String query, String token) {
        try {
            return (Business.getServices(query, token));
        } catch (IOException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            return (null);
        }

    }

}
