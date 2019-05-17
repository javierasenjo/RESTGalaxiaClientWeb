/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Pojo.Galaxia;
import Pojo.ListaPlanetas;
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
public class ExplorarGalaxia extends HttpServlet {

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

        try {
            token = (String) request.getServletContext().getAttribute("token");
            System.out.println("tokeeen" + token);
            response.setContentType("text/html;charset=UTF-8");

            ServiciosGalaxia serviciosGalaxia = new ServiciosGalaxia();
            Comprobaciones comprobaciones = new Comprobaciones();

            if (token == null || !comprobaciones.comprobarToken(token)) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noToken.html");
                rd.forward(request, response);
                response.sendError(400, "The specified passenger does not exist.");
            }
            Galaxia galaxia = null;
            galaxia = serviciosGalaxia.getGalaxia(Galaxia.class, token);
            //System.out.println(galaxia.getNombre());
            if (galaxia == null || comprobaciones.comprobarGalaxia(galaxia) == false) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/noGalaxia.html");
                rd.forward(request, response);
            }
            ListaPlanetas listaPlanetas = (ListaPlanetas) serviciosGalaxia.getPlanetas(ListaPlanetas.class, token);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ExplorarGalaxia</title>");
                out.println("<style>");
                out.println("table, th, td {\n"
                        + "  border: 1px solid black;\n"
                        + "}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h3>Galaxia: " + galaxia.getNombre() + "</h3>");
                out.println("<table>");
                out.println("<tr><th>Nombre del Planeta</th>"
                        + "<th>Id del Planeta</th></tr>");
                for (int i = 0; i < listaPlanetas.getPlanetas().size(); i++) {
                    out.println("<tr>");
                    out.println("<td>" + listaPlanetas.getPlanetas().get(i).getNombre() + "</td>");
                    out.println("<td>" + listaPlanetas.getPlanetas().get(i).getIdPlaneta() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table> <br>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>Añadir Planeta</td>");
                out.println("<td colspan=\"5\" style=\"text-align:center;\"> <form name=\"GetPlanetaForm\" method=\"post\" action=\"annadirPlaneta.html\">\n"
                        + "          <input type=\"submit\" value=\"Añadir un planeta\" style=\"width:100%\"/></td> </form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"GetPlanetaForm\" method=\"post\" action=\"ObtenerPlaneta\">\n"
                        + "            Obtener Planeta</td>"
                        + "<td>Número del planeta <input type=\"text\" name=\"numeroPlaneta\"/></td> "
                        + "<td colspan=\"4\" style=\"text-align:center;\"><input type=\"submit\" value=\"Obtener planeta\" style=\"width:100%\"/> </td></form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"PutPlanetaForm\" method=\"post\" action=\"ModificarPlaneta\">\n"
                        + "            Modificar Planeta</td>"
                        + "<td>Número del planeta <input type=\"text\" name=\"numeroPlaneta\"/></td> "
                        + "<td>Nombre del planeta <input type=\"text\" name=\"nombrePlaneta\"/></td> "
                        + "<td>Edad del planeta <input type=\"int\" name=\"edadPlaneta\"/></td> "
                        + "<td>Radio del planeta <input type=\"float\" name=\"radioPlaneta\"/></td> "
                        + "<td><input type=\"submit\" value=\"Modificar planeta\"/> </td></form> ");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td> <form name=\"DeletePlanetaForm\" method=\"post\" action=\"BorrarPlaneta\">\n"
                        + "            Borrar Planeta</td>"
                        + "<td>Número del planeta <input type=\"text\" name=\"numeroPlaneta\"/></td> "
                        + "<td colspan=\"4\" style=\"text-align:center;\"><input type=\"submit\" value=\"Borrar planeta\" style=\"width:100%\"/> </td></form> ");
                out.println("</tr>");
                out.println("</table>");
                out.println("<a href=\"http://localhost:8080/RESTGalaxiaClientWeb/index.html\">Volver al índice</a>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
            System.out.println(ex);
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
