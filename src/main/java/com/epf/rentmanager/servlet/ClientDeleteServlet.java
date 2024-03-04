package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ClientService clientService = ClientService.getInstance();
            Long id = Long.parseLong(request.getParameter("id"));
            long l = clientService.delete(new Client(id, "", "","", LocalDate.now()));
            response.sendRedirect("/rentmanager/users");
        }catch (ServiceException e){
            throw new ServletException();
        }
        }
}
