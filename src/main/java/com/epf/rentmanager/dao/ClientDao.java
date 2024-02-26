package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement( CREATE_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException("PB de DAO"+e.getMessage());
		}
		return 0;
	}

	
	public long delete(Client client) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.setLong(1, client.getId());
			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException("PB de DAO"+e.getMessage());
		}
		return 0;
	}

	public Client findById(long id) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.setLong(1, id);
			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				return new Client(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate());
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return new Client();
	}

	public List<Client> findAll() throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENTS_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			List<Client> L1= new ArrayList<Client>();
			do {
				System.out.println(L1);
				L1.add(new Client(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate()));
			} while(resultSet.next());
			return L1;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
