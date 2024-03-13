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

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation_avec_nom_prenom_constr_modele> megaList = new ArrayList<>();

            ClientService clientService = ClientService.getInstance();
            ReservationService reservationService = ReservationService.getInstance();
            VehicleService vehicleService = VehicleService.getInstance();
            long id = Long.parseLong(request.getParameter("id"));

            Client C1 = clientService.findById(id);
            List<Reservation> L1 = reservationService.findResaByVehicleId(id);
            L1.forEach(r->{
                try {
                    Vehicle voiture = vehicleService.findById(r.getVehicle_id());
                    Reservation_avec_nom_prenom_constr_modele reservMax = new Reservation_avec_nom_prenom_constr_modele(r.getId(),C1.getNom(), C1.getPrenom(),voiture.getConstructeur(),voiture.getModele(),r.getDebut(),r.getFin(),voiture.getId());
                    megaList.add(reservMax);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            request.setAttribute("nb_resa",L1.size());
            request.setAttribute("user",C1);
            request.setAttribute("rentsmax",megaList);

        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }

}
