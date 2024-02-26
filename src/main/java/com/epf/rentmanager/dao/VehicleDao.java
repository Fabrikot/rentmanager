package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Vehicle;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( CREATE_VEHICLE_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery()
		){
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( DELETE_VEHICLE_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery()
		){
			ps.setLong(1, vehicle.getId());
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return 0;
	}

	public Vehicle findById(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( FIND_VEHICLE_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery()
		){
			ps.setLong(1, id);
			if (resultSet.next()) {
				return new Vehicle(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return new Vehicle();
	}

	public List<Vehicle> findAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( FIND_VEHICLES_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery()
			){
			List<Vehicle> L1= new ArrayList<Vehicle>();
			while(resultSet.next()){
				L1.add(new Vehicle(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4)));
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
