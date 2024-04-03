package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.dao.ClientDao;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException {
		if ((client.getNom().isEmpty())||(client.getPrenom().isEmpty())){
			throw new ServiceException("L'utilisateur doit avoir un nom/prénom");
		}
		try{
			client.setNom(client.getNom().toUpperCase());
			return clientDao.create(client);
		}catch(DaoException e){
			throw new ServiceException("Erreur création client"+e.getMessage());
		}
	}
	public long update(Client client) throws ServiceException {
		if ((client.getNom().isEmpty())||(client.getPrenom().isEmpty())){
			throw new ServiceException("L'utilisateur doit avoir un nom/prénom");
		}
		try{
			client.setNom(client.getNom().toUpperCase());
			return clientDao.update(client);
		}catch(DaoException e){
			throw new ServiceException("Erreur création client"+e.getMessage());
		}
	}

	public Client findById(long id) throws ServiceException {
		if (id==0){
			throw new ServiceException("Donnez un autre ID");
		}
		try{
			return clientDao.findById(id);
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver client");
		}
	}

	public long delete(Client client) throws ServiceException {
		try{
			return clientDao.delete(client);
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver clients"+e.getMessage());
		}
	}
	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver clients"+e.getMessage());
		}
	}
	public int count() throws ServiceException {
		try{
			return clientDao.countAll();
		}catch(DaoException e){
			throw new ServiceException("Erreur trouver clients"+e.getMessage());
		}
	}
	
}
