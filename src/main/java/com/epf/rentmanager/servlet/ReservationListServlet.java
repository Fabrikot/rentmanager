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

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

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
     * Permet d'afficher la liste des réservations avec le nom/prénom du client et le modèle/constructeur de la voiture
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
        try {
            List<Reservation> L1 = new ArrayList<Reservation>();
            List<Reservation_avec_nom_prenom_constr_modele> megaList = new ArrayList<Reservation_avec_nom_prenom_constr_modele>();

            L1 = reservationService.findAll();
            L1.forEach(r->{
                try {
                    Vehicle voiture = vehicleService.findById(r.getVehicle_id());
                    Client client = clientService.findById(r.getClient_id());
                    Reservation_avec_nom_prenom_constr_modele reservMax = new Reservation_avec_nom_prenom_constr_modele(r.getId(),client.getNom(),client.getPrenom(),voiture.getConstructeur(),voiture.getModele(),r.getDebut(),r.getFin(),voiture.getId());
                    megaList.add(reservMax);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            request.setAttribute("rentsmax",megaList);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        }catch (ServiceException e){
            throw new ServletException(e.getMessage());
        }
    }
}
