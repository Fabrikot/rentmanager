package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Reservation_avec_nom_prenom_constr_modele;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation_avec_nom_prenom_constr_modele> megaList = new ArrayList<>();

            long id = Long.parseLong(request.getParameter("id"));

            Client C1 = clientService.findById(id);
            List<Reservation> LR1 = reservationService.findResaByClientId(id);
            List<Vehicle> LV1 = new ArrayList<>();
            LR1.forEach(r->{
                try {
                    Vehicle voiture = vehicleService.findById(r.getVehicle_id());
                    Reservation_avec_nom_prenom_constr_modele reservMax = new Reservation_avec_nom_prenom_constr_modele(r.getId(),C1.getNom(), C1.getPrenom(),voiture.getConstructeur(),voiture.getModele(),r.getDebut(),r.getFin(),voiture.getId());
                    megaList.add(reservMax);
                    Vehicle vehicle_trouve = LV1.stream()
                            .filter(vehicle -> voiture.getId() == (vehicle.getId()))
                            .findAny()
                            .orElse(null);
                    if (vehicle_trouve==null){
                        LV1.add(voiture);
                    }
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            request.setAttribute("nb_resa",LR1.size());
            request.setAttribute("user",C1);
            request.setAttribute("rentsmax",megaList);
            request.setAttribute("cars",LV1);

        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }

}
