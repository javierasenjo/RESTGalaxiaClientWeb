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
public class ModificarPlaneta extends HttpServlet {

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
            token = (String) getServletContext().getAttribute("token");
            Comprobaciones comprobaciones = new Comprobaciones();

            ServiciosGalaxia serviciosGalaxia = new ServiciosGalaxia();
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
            String nombrePlaneta = request.getParameter("nombrePlaneta");
            String edadPlanetaBeta = request.getParameter("edadPlaneta");
            String radioPlanetaBeta = request.getParameter("radioPlaneta");
            int edadPlaneta = Integer.parseInt(edadPlanetaBeta);
            double radioPlaneta = Double.parseDouble(radioPlanetaBeta);
            
            planeta = new Planeta(nombrePlaneta, edadPlaneta, radioPlaneta);

            Planeta planetaNuevo = serviciosGalaxia.putPlaneta(planeta, Planeta.class, numPlanetaBeta, token);

            RequestDispatcher rd = getServletContext().getNamedDispatcher("ExplorarGalaxia");
            rd.forward(request, response);

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
