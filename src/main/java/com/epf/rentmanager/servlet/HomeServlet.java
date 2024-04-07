package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
	 * Calcule le nombre de véhicules, clients et réservations et affiche la Home page
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
