package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
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

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    /**
     *
     */
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;

    /**
     * Charge la liste de clients et de voitures et affiche la page de création
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
            List<Vehicle> LV1 = vehicleService.findAll();
            List<Client> LC1 = clientService.findAll();
            request.setAttribute("cars",LV1);
            request.setAttribute("clients",LC1);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    /**
     * Permet de gérer la création d'une nouvelle réservation
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
            long vehicleid = Long.parseLong(request.getParameter("car"));
            long clientid = Long.parseLong(request.getParameter("client"));
            LocalDate debut = LocalDate.parse(request.getParameter("begin"));
            LocalDate fin = LocalDate.parse(request.getParameter("end"));
            Reservation V1 = new Reservation(1,clientid, vehicleid, debut, fin);
            long l = reservationService.create(V1);
            response.sendRedirect("/rentmanager/rents");
        }catch (ServiceException e){
            try {
                List<Vehicle> LV1 = vehicleService.findAll();
                List<Client> LC1 = clientService.findAll();
                request.setAttribute("cars",LV1);
                request.setAttribute("clients",LC1);
                request.setAttribute("exception",e.getMessage());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
