package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		if ((vehicle.getConstructeur().isEmpty())||(vehicle.getNb_places()<=1)){
			throw new ServiceException("La vehicule doit avoir un constructeur et un nb de places correct");
		}
		try{
			return vehicleDao.create(vehicle);
		}catch(DaoException e){
			throw new ServiceException("Erreur création vehicule");
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		if (id==0){
			throw new ServiceException("Donnez un autre ID");
		}
		try{
			return vehicleDao.findById(id);
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver vehicle");
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try{
			return vehicleDao.delete(vehicle);
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver véhicules");
		}
	}
	public int count() throws ServiceException {
		try{
			return vehicleDao.countAll();
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver véhicules");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver véhicules");
		}
	}
}
