/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Pojo.Galaxia;
import Pojo.Planeta;
import RestServices.ServiciosGalaxia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author javie
 */
public class ObtenerPlaneta extends HttpServlet {

    String token = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Planeta planeta = null;
        try {
            ServiciosGalaxia serviciosGalaxia = new ServiciosGalaxia();
            token = (String) getServletContext().getAttribute("token");
            Comprobaciones comprobaciones = new Comprobaciones();

            if (token == null || !comprobaciones.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
            }
            Galaxia galaxia = serviciosGalaxia.getGalaxia(Galaxia.class, token);
            if (comprobaciones.comprobarGalaxia(galaxia) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noGalaxia.html");
                rd.forward(request, response);
            }
            String numPlanetaBeta = request.getParameter("numeroPlaneta");

            planeta = serviciosGalaxia.getPlaneta(Planeta.class, numPlanetaBeta, token);

            PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Obtener Planeta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>El planeta " + planeta.getNombre() + " contiene la siguiente información</h3>");
            out.println("<ul>");
            out.println("<li>Radio del planeta: " + planeta.getRadio() + "</li>");
            out.println("<li>Edad del planeta: " + planeta.getEdad()+ "</li>");
            out.println("</ul>");
            out.println("<a href=\"http://localhost:8080/RESTGalaxiaClientWeb/ExplorarGalaxia\">Volver a explorar la galaxia</a>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/noPlaneta.html");
            rd.forward(request, response);
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

}
