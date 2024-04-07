package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_ID = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private  static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation";

	/**
	 * Crée la réservation
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long create(Reservation reservation) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement( CREATE_RESERVATION_QUERY
							, Statement.RETURN_GENERATED_KEYS);){
			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4,Date.valueOf(reservation.getFin()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				reservation.setId(id);
				return reservation.getId();
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur créer reservation"+e.getSQLState());
		}
		return 0;
	}

	/**
	 * Met à jour la réservation
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long update(Reservation reservation) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( UPDATE_RESERVATION_QUERY
								, Statement.RETURN_GENERATED_KEYS);){
			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5, reservation.getId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				reservation.setId(id);
				return reservation.getId();
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur update reservation"+e.getSQLState());
		}
		return 0;
	}

	/**
	 * Supprime la réservation
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long delete(Reservation reservation) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_RESERVATION_QUERY
							,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setLong(1, reservation.getId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur supprimer reservations"+e.getSQLState());
		}
		return 0;
	}

	/**
	 * Trouve la réservation grâce à son id
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public Reservation findResaById(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =connection.prepareStatement(FIND_RESERVATIONS_BY_ID
						,Statement.RETURN_GENERATED_KEYS);){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return new Reservation(id,resultSet.getLong(1),resultSet.getLong(2),resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate());
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur trouver reservation"+e.getSQLState());
		}
		return null;
	}

	/**
	 * Trouve la réservation grâce à l'id du client
	 * @param clientId
	 * @return
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY
							,Statement.RETURN_GENERATED_KEYS);){
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			List<Reservation> L1= new ArrayList<Reservation>();
			while(resultSet.next()) {
				L1.add(new Reservation(resultSet.getLong(1),clientId,resultSet.getLong(2),resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate()));
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException("Erreur trouver reservations"+e.getSQLState());
		}
	}

	/**
	 * Trouve la réservation grâce à l'id du véhicule
	 * @param vehicleId
	 * @return
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY,
						Statement.RETURN_GENERATED_KEYS);){
			ps.setLong(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();
			List<Reservation> L1= new ArrayList<Reservation>();
			while(resultSet.next()){
				L1.add(new Reservation(resultSet.getLong(1),resultSet.getLong(2),vehicleId,resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate()));
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException("Erreur trouver reservations"+e.getSQLState());
		}
	}

	/**
	 * Compte le nombre de réservations
	 * @return
	 * @throws DaoException
	 */
	public int countAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement( COUNT_RESERVATIONS_QUERY
								,Statement.RETURN_GENERATED_KEYS);
				ResultSet resultSet = ps.executeQuery();
		){
			while(resultSet.next()){
				return resultSet.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur compter reservations"+e.getSQLState());
		}
	}

	/**
	 *
	 * @return La liste de toutes les réservations
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement(FIND_RESERVATIONS_QUERY
								,Statement.RETURN_GENERATED_KEYS);

				ResultSet resultSet = ps.executeQuery();){

			List<Reservation> L1= new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation R1 =new Reservation(resultSet.getLong(1),resultSet.getLong(2),resultSet.getLong(3),resultSet.getDate(4).toLocalDate(),resultSet.getDate(5).toLocalDate());
				L1.add(R1);
			}
			return L1;
		} catch (SQLException e) {
			throw new DaoException("Erreur trouver reservations"+e.getSQLState());
		}
	}
}
