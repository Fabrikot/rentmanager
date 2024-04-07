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

@WebServlet("/cars/details")
public class VehicleDetailServlet extends HttpServlet {
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

    /**
     * Permet d'afficher les détails d'un véhicule avec les réservations associées
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
            List<Reservation_avec_nom_prenom_constr_modele> megaList = new ArrayList<>();

            long id = Long.parseLong(request.getParameter("id"));
            Vehicle V1 = vehicleService.findById(id);
            List<Reservation> L1 = reservationService.findResaByVehicleId(id);
            L1.forEach(r->{
                try {
                    Client client = clientService.findById(r.getClient_id());
                    Reservation_avec_nom_prenom_constr_modele reservMax = new Reservation_avec_nom_prenom_constr_modele(r.getId(),client.getNom(),client.getPrenom(),V1.getConstructeur(),V1.getModele(),r.getDebut(),r.getFin(),V1.getId());
                    megaList.add(reservMax);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            });
            request.setAttribute("nb_resa",L1.size());
            request.setAttribute("car",V1);
            request.setAttribute("rentsmax",megaList);
        }catch (ServiceException e){
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }

}
