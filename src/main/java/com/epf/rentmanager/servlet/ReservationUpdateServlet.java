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

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {

    /**
     *
     */
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;
    private static final long serialVersionUID = 1L;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            List<Vehicle> LV1 = new ArrayList<>();
            List<Client> LC1 = new ArrayList<>();

            long id = Long.parseLong(request.getParameter("id"));

            Reservation R1 = reservationService.findResaById(id);
            Vehicle V1 = vehicleService.findById(R1.getVehicle_id());
            Client C1 = clientService.findById(R1.getClient_id());

            LV1 = vehicleService.findAll();
            LC1 = clientService.findAll();
            LV1.remove(Integer.parseInt(String.valueOf(V1.getId()-1)));
            LC1.remove(Integer.parseInt(String.valueOf(C1.getId()-1)));

            request.setAttribute("car",V1);
            request.setAttribute("client",C1);
            request.setAttribute("cars",LV1);
            request.setAttribute("clients",LC1);
            request.setAttribute("rent",R1);

        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            long id = Long.parseLong(request.getParameter("id"));
            long vehicleid = Long.parseLong(request.getParameter("car"));
            long clientid = Long.parseLong(request.getParameter("client"));
            LocalDate debut = LocalDate.parse(request.getParameter("begin"));
            LocalDate fin = LocalDate.parse(request.getParameter("end"));
            Reservation R1 = new Reservation(id, clientid, vehicleid, debut, fin);
            long l = reservationService.update(R1);
            response.sendRedirect("/rentmanager/rents");
        }catch (ServiceException e){
            try {
                List<Vehicle> LV1 = new ArrayList<>();
                List<Client> LC1 = new ArrayList<>();

                long id = Long.parseLong(request.getParameter("id"));

                Reservation R1 = reservationService.findResaById(id);
                Vehicle V1 = vehicleService.findById(R1.getVehicle_id());
                Client C1 = clientService.findById(R1.getClient_id());

                LV1 = vehicleService.findAll();
                LC1 = clientService.findAll();
                LV1.remove(Integer.parseInt(String.valueOf(V1.getId()-1)));
                LC1.remove(Integer.parseInt(String.valueOf(C1.getId()-1)));

                request.setAttribute("car",V1);
                request.setAttribute("client",C1);
                request.setAttribute("cars",LV1);
                request.setAttribute("clients",LC1);
                request.setAttribute("rent",R1);
                request.setAttribute("exception",e.getMessage());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
