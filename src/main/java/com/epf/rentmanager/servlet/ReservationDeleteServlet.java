package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
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

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            ReservationService reservationService = ReservationService.getInstance();
            long id = Long.parseLong(request.getParameter("id"));
            long l = reservationService.delete(new Reservation(id,1,1, LocalDate.now(),LocalDate.now()));
            response.sendRedirect("/rentmanager/rents");
        }catch (ServiceException e){
            throw new ServletException();
        }
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
