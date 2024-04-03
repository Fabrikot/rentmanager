package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private VehicleDao() {}
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id=?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private  static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle";

	public long create(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( CREATE_VEHICLE_QUERY
								,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				vehicle.setId(id);
				return vehicle.getId();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return 0;
	}
	public long update(Vehicle vehicle) throws  DaoException{
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( UPDATE_VEHICLE_QUERY
								,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.setLong(4, vehicle.getId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				vehicle.setId(id);
				return vehicle.getId();
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
		){
			ps.setLong(1, vehicle.getId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
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
				PreparedStatement ps =connection.prepareStatement( FIND_VEHICLE_QUERY,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return new Vehicle(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return null;
	}
	public int countAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( COUNT_VEHICLES_QUERY
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

	public List<Vehicle> findAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( FIND_VEHICLES_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery();
			){
			List<Vehicle> L1= new ArrayList<Vehicle>();
			while(resultSet.next()){
				Vehicle V1 = new Vehicle(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
				L1.add(V1);
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
