/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.cms.web.actions.pages;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ncedu.core.data.accessobjects.PageDAO;
import ru.ncedu.core.data.entities.Page;
import ru.ncedu.core.data.factories.DAOFactory;
import ru.ncedu.core.jstree.JSTreeCache;
import ru.ncedu.core.security.AuthentificationBean;
import ru.ncedu.core.utils.StringUtils;

/**
 *
 * @author Roman Semenov <romansemenov3@gmail.com>
 */
@WebServlet(name = "EditPageServlet", urlPatterns = {"/editpage"})
public class EditPageServlet extends HttpServlet {
    @EJB
    private AuthentificationBean authBean;
    
    @EJB
    JSTreeCache jsTreeCache;

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
        PageDAO pageDAO = DAOFactory.getDAOFactory(DAOFactory.DAOType.LOCAL).getPageDAO();
        
        String pageId = request.getParameter("id");
        if (StringUtils.isNotEmpty(pageId)) {
            Page page = pageDAO.findById(Long.valueOf(pageId));
            
            Map<String, String[]> parameters = request.getParameterMap();
            
            if(parameters.containsKey("content") && parameters.containsKey("title"))
            {
                String content = request.getParameter("content");
                String title = request.getParameter("title");

                page.setContent(content);
                page.setTitle(title);
                page.setModifiedBy(authBean.authorization().getUserId());
                page.setModifiedWhen(new Date(System.currentTimeMillis()));
                
                jsTreeCache.updatePageRecord(page);
            }
            
            request.setAttribute("page", page);
            request.getRequestDispatcher("/views/mockup/editpage.jsp").forward(request, response);
        }
        else
        {
            request.setAttribute("message", "Enter page id");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
