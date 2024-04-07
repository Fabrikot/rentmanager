package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;

@Service
@Validated
public class ClientService {
	private ClientDao clientDao;
	private ReservationDao reservationDao;

	private ClientService(ClientDao clientDao, ReservationDao reservationDao) {
		this.clientDao = clientDao;
		this.reservationDao = reservationDao;
	}

	/**
	 * Vérifie si le client est conforme pour être créé
	 * @param client
	 * @return long de l'id créé
	 * @throws ServiceException
	 */
	public long create(Client client) throws ServiceException {
		try{
			List<Client> LC1 = clientDao.findAll();
			if ((client.getNom().length()<3)){
				throw new ServiceException("Nom incorrect");
			} else if (client.getPrenom().length()<3) {
				throw new ServiceException("Prenom incorrect");
			} else if (client.getNaissance().isAfter(LocalDate.now().minusYears(18))) {
				throw new ServiceException("Client trop jeune");
			} else if (LC1.stream().anyMatch(client1 -> client1.getEmail().equals(client.getEmail()))){
				throw new ServiceException("E-mail déjà existant");
			}
			client.setNom(client.getNom().toUpperCase());
			return clientDao.create(client);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Vérifie si le client est conforme  pour être update
	 * @param client
	 * @return long de l'id créé
	 * @throws ServiceException
	 */
	public long update(Client client) throws ServiceException {
		try{
			List<Client> LC1 = clientDao.findAll();
			if ((client.getNom().length()<3)){
				throw new ServiceException("Nom incorrect");
			} else if (client.getPrenom().length()<3) {
				throw new ServiceException("Prenom incorrect");
			} else if (client.getNaissance().isAfter(LocalDate.now().minusYears(18))) {
				throw new ServiceException("Client trop jeune");
			} else if (LC1.stream().anyMatch(client1 -> client1.getEmail().equals(client.getEmail()))){
				throw new ServiceException("E-mail déjà existant");
			}
			client.setNom(client.getNom().toUpperCase());
			return clientDao.update(client);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	public Client findById(long id) throws ServiceException {
		if (id==0){
			throw new ServiceException("Donnez un autre ID");
		}
		try{
			return clientDao.findById(id);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Delete les réservations pour ce client ainsi que le client
	 * @param client
	 * @return
	 * @throws ServiceException
	 */
	public long delete(Client client) throws ServiceException {
		try{
			List<Reservation> LR1 = reservationDao.findResaByClientId(client.getId());
			for (Reservation resa : LR1) {
				reservationDao.delete(resa);
			}
			return clientDao.delete(client);
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	public int count() throws ServiceException {
		try{
			return clientDao.countAll();
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
}
