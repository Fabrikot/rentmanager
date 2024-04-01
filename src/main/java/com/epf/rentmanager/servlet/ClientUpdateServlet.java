package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import jdk.vm.ci.meta.Local;

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

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            ClientService clientService = ClientService.getInstance();
            long id = Long.parseLong(request.getParameter("id"));
            Client C1 = clientService.findById(id);
            request.setAttribute("client",C1);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            ClientService clientService = ClientService.getInstance();
            long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate date = LocalDate.of(2002,5,15);
            Client C1 = new Client(id,nom, prenom, email, date);
            long l = clientService.update(C1);
            response.sendRedirect("/rentmanager/users");
        }catch (ServiceException e){
            throw new ServletException();
        }
    }
}
