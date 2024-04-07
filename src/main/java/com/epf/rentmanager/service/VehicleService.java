package com.epf.rentmanager.service;

import java.util.LinkedList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service()
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao;

	private VehicleService(VehicleDao vehicleDao,ReservationDao reservationDao) {
		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
	}

	/**
	 * Vérifie si le véhicule est conforme pour être créé
	 * @param vehicle
	 * @return long de l'id créé
	 * @throws ServiceException
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		int nb_places=vehicle.getNb_places();
		if ((vehicle.getConstructeur().isEmpty())||(vehicle.getModele().isEmpty())){
			throw new ServiceException("La vehicule doit avoir un constructeur et un modèle");
		}else if(nb_places<=1||nb_places>9){
			throw new ServiceException("La nombre de places doit être compris entre 1 et 9");
		}
		try{
			return vehicleDao.create(vehicle);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * Vérifie si le véhicule est conforme pour être update
	 * @param vehicle
	 * @return long de l'id créé
	 * @throws ServiceException
	 */
	public long update(Vehicle vehicle) throws ServiceException {
		int nb_places=vehicle.getNb_places();
		if ((vehicle.getConstructeur().isEmpty())||(vehicle.getModele().isEmpty())){
			throw new ServiceException("La vehicule doit avoir un constructeur et un modèle");
		}else if(nb_places<=1||nb_places>9){
			throw new ServiceException("La nombre de places doit être compris entre 1 et 9");
		}
		try{
			return vehicleDao.update(vehicle);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		if (id==0){
			throw new ServiceException("Donnez un autre ID");
		}
		try{
			return vehicleDao.findById(id);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Delete les réservations pour ce véhicule ainsi que le véhicule
	 * @param vehicle
	 * @return
	 * @throws ServiceException
	 */
	public long delete(Vehicle vehicle) throws ServiceException {
		try{
			List<Reservation> LR1 = reservationDao.findResaByVehicleId(vehicle.getId());
            for (Reservation resa : LR1) {
                reservationDao.delete(resa);
            }
            return vehicleDao.delete(vehicle);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	public int count() throws ServiceException {
		try{
			return vehicleDao.countAll();
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
}
