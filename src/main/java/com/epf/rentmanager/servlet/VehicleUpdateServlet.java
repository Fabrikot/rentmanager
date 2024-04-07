package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;
    private static final long serialVersionUID = 1L;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * Affiche les informations du véhicule à mettre à jour dans la jsp
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
            long id = Long.parseLong(request.getParameter("id"));
            Vehicle V1 = vehicleService.findById(id);
            request.setAttribute("vehicle",V1);
        }catch (ServiceException e){
            throw new ServletException(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }

    /**
     * Permet la mise à jour d'un véhicule
     * Si une erreur est lancée, on affiche le message en rouge dans la jsp
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
            long id = Long.parseLong(request.getParameter("id"));
            String manu = request.getParameter("manufacturer");
            String mod = request.getParameter("modele");
            int seat = Integer.parseInt(request.getParameter("seats"));
            Vehicle V1 = new Vehicle(id,manu, mod, seat);
            long l = vehicleService.update(V1);
            response.sendRedirect("/rentmanager/cars");
        }catch (ServiceException e){
            try {
                long id = Long.parseLong(request.getParameter("id"));
                Vehicle V1 = vehicleService.findById(id);
                request.setAttribute("vehicle",V1);
                request.setAttribute("exception",e.getMessage());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
