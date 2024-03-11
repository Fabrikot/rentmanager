package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            List<Vehicle> L1 = new ArrayList<>();
            List<Client> C1 = new ArrayList<>();
            VehicleService vehicleService = VehicleService.getInstance();
            ClientService clientService = ClientService.getInstance();
            L1 = vehicleService.findAll();
            C1 = clientService.findAll();
            request.setAttribute("cars",L1);
            request.setAttribute("clients",C1);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            ReservationService reservationService = ReservationService.getInstance();
            long vehicleid = Long.parseLong(request.getParameter("car"));
            long clientid = Long.parseLong(request.getParameter("client"));
            LocalDate debut = LocalDate.parse(request.getParameter("begin"));
            LocalDate fin = LocalDate.parse(request.getParameter("end"));
            Reservation V1 = new Reservation(1,clientid, vehicleid, debut, fin);
            long l = reservationService.create(V1);
            response.sendRedirect("/rentmanager/rents");
        }catch (ServiceException e){
            throw new ServletException();
        }
    }
}
