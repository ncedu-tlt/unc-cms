/*
 * Copyright 2014 ncedu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ncedu.cms.web.mockup;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ncedu.core.data.accessobjects.PageDAO;
import ru.ncedu.core.data.entities.Page;
import ru.ncedu.core.data.factories.DAOFactory;
import ru.ncedu.core.jstree.JSTreeBean;
import ru.ncedu.core.security.RightBean;
import ru.ncedu.core.utils.StringUtils;

/**
 *
 * @author Alexander Zvyagintsev <alzv0411@gmail.com>
 */
@WebServlet(name = "MockupPageServlet", urlPatterns = {"/mockup/page"})
public class PageServlet extends HttpServlet {

    @EJB
    RightBean rightBean;

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
            if (page == null) {
                request.setAttribute("message", "Page with id=" + pageId + " doesn't exist");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            
            request.setAttribute("page", page);
            request.setAttribute("right", rightBean.getRight(Long.valueOf(pageId)));
            request.getRequestDispatcher("/views/mockup/page.jsp").forward(request, response);
        }

        List<Page> pages = pageDAO.findAll();
        request.setAttribute("pages", pages);
        request.getRequestDispatcher("/views/mockup/pages.jsp").forward(request, response);
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
