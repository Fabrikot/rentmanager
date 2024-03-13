package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			VehicleService vehicleService = VehicleService.getInstance();
			ClientService clientService = ClientService.getInstance();
			ReservationService reservationService = ReservationService.getInstance();

			int nbvehi = vehicleService.count();
			int nbuser = clientService.count();
			int nbresa = reservationService.count();

			request.setAttribute("nb_vehi",nbvehi);
			request.setAttribute("nb_user",nbuser);
			request.setAttribute("nb_resa",nbresa);
		}catch (ServiceException e){
			throw new ServletException();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
