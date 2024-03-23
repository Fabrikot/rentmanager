package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

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

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VehicleService vehicleService = VehicleService.getInstance();
            long id = Long.parseLong(request.getParameter("id"));
            Vehicle V1 = vehicleService.findById(id);
            request.setAttribute("vehicle",V1);
        }catch (ServiceException e){
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            VehicleService vehicleService = VehicleService.getInstance();
            String manu = request.getParameter("manufacturer");
            String mod = request.getParameter("modele");
            int seat = Integer.parseInt(request.getParameter("seats"));
            long id = Long.parseLong(request.getParameter("id"));
            Vehicle V1 = new Vehicle(id,manu, mod, seat);
            long l = vehicleService.update(V1);
            response.sendRedirect("/rentmanager/cars");
        }catch (ServiceException e){
            throw new ServletException();
        }
    }
}
