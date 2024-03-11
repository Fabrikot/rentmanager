package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Reservation_avec_nom_prenom_constr_modele;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation> L1 = new ArrayList<Reservation>();
            List<Reservation_avec_nom_prenom_constr_modele> megaList = new ArrayList<Reservation_avec_nom_prenom_constr_modele>();
            ReservationService reservationService = ReservationService.getInstance();
            VehicleService vehicleService = VehicleService.getInstance();
            ClientService clientService = ClientService.getInstance();
            L1 = reservationService.findAll();
            System.out.println(L1.size());
            L1.forEach(r->{
                try {
                    Vehicle voiture = vehicleService.findById(r.getVehicle_id());
                    Client client = clientService.findById(r.getClient_id());
                    Reservation_avec_nom_prenom_constr_modele reservMax = new Reservation_avec_nom_prenom_constr_modele(r.getId(),client.getNom(),client.getPrenom(),voiture.getConstructeur(),voiture.getModele(),r.getDebut(),r.getFin());
                    megaList.add(reservMax);
                    System.out.println(reservMax);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            request.setAttribute("rentsmax",megaList);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
    }
}
