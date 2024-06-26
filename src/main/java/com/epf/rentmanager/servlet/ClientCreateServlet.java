package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    private static final long serialVersionUID = 1L;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    /**
     * Permet de gérer la création d'un nouveau client
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
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate date = LocalDate.parse(request.getParameter("birthdate"));
            Client C1 = new Client(1,nom, prenom, email,date);
            long l = clientService.create(C1);
            response.sendRedirect("/rentmanager/users");
        }catch (ServiceException e){
            request.setAttribute("exception", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
        }
    }
}
