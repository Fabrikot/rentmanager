package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;

    /**
     * Charge les informations du client à update sur la page de modification
     * @param request   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *
     * @param response  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            long id = Long.parseLong(request.getParameter("id"));
            Client C1 = clientService.findById(id);
            request.setAttribute("client",C1);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }

    /**
     * Permet de gérer la modification d'un client (on ne permet pas de modifier la date de naissance)
     * En cas  d'erreur on affiche en rouge la raison sur la jsp
     * @param request   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *
     * @param response  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate date = LocalDate.of(2002,5,15);
            Client C1 = new Client(id,nom, prenom, email, date);
            long l = clientService.update(C1);
            response.sendRedirect("/rentmanager/users");
        }catch (ServiceException e){
            try {
                long id = Long.parseLong(request.getParameter("id"));
                Client C1 = clientService.findById(id);
                request.setAttribute("client",C1);
                request.setAttribute("exception",e.getMessage());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
