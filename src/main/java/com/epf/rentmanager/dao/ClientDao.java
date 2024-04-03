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
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	private ClientDao() {}
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ? WHERE id=?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private  static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client";

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
				long id = resultSet.getLong(1);
				client.setId(id);
				return client.getId();
			}
		} catch (SQLException e) {
			throw new DaoException("PB de DAO"+e.getMessage());
		}
		return 0;
	}
	public long update(Client client) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( UPDATE_CLIENT_QUERY
								,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setLong(4, client.getId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				client.setId(id);
				return client.getId();
			}
		} catch (SQLException e) {
			throw new DaoException("PB de DAO"+e.getMessage());
		}
		return 0;
	}
	public int countAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( COUNT_CLIENTS_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery();
		){
			while(resultSet.next()){
				return resultSet.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public long delete(Client client) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.setLong(1, client.getId());
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

	public Client findById(long id) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);
			){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return new Client(id,resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate());
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
			ResultSet resultSet = ps.executeQuery();
			){
			List<Client> L1= new ArrayList<Client>();
			while(resultSet.next()){
				Client C1 = new Client(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate());
				L1.add(C1);
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
}