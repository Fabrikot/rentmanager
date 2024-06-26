package com.epf.rentmanager.service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.stereotype.Service;
import com.epf.rentmanager.utils.IOUtils;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    /**
     * Vérifie si la réservation est conforme pour être créee
     * @param reservation
     * @return long de l'id créé
     * @throws ServiceException
     */
    public long create(Reservation reservation) throws ServiceException {
        try {
            List<Reservation> LR1 = reservationDao.findAll();
            List<Reservation> LResa_par_vehicle = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
            Period temps_ecoule = Period.between(reservation.getDebut(),reservation.getFin());
            if (reservation.getFin().isBefore(reservation.getDebut())){
                throw new ServiceException("Le début de la réservation doit être avant la fin");
            }
            else if (temps_ecoule.getDays()>7||temps_ecoule.getMonths()>0||temps_ecoule.getYears()>0){
                throw new ServiceException("La voiture ne peut pas être réservée plus de 7 jours");
            }
            else if (LR1.stream().anyMatch(reservation_existante -> (reservation_existante.getFin().isAfter(reservation.getDebut())||(reservation_existante.getFin().isEqual(reservation.getDebut())))
            && (reservation_existante.getVehicle_id()==(reservation.getVehicle_id())))){
                throw new ServiceException("Voiture déjà reservée");
            } else if (IOUtils.estReservePdt30j(LResa_par_vehicle,reservation)){
                throw new ServiceException("Voiture réservée trop longtemps");
            }
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur création reservations" + e.getMessage());
        }
    }

    /**
     * Vérifie si la réservation est conforme pour être update
     * @param reservation
     * @return long de l'id créé
     * @throws ServiceException
     */
    public long update(Reservation reservation) throws ServiceException {
        try{
            List<Reservation> LR1 = reservationDao.findAll();
            List<Reservation> LResa_par_vehicle = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
            Period temps_ecoule = Period.between(reservation.getDebut(),reservation.getFin());
            if (reservation.getFin().isBefore(reservation.getDebut())){
                throw new ServiceException("Le début de la réservation doit être avant la fin");
            }
            else if (temps_ecoule.getDays()>7||temps_ecoule.getMonths()>0||temps_ecoule.getYears()>0){
                throw new ServiceException("La voiture ne peut pas être réservée plus de 7 jours");
            }
            else if (LR1.stream().anyMatch(reservation_existante -> (reservation_existante.getFin().isAfter(reservation.getDebut())||(reservation_existante.getFin().isEqual(reservation.getDebut())))
                    && (reservation_existante.getVehicle_id()==(reservation.getVehicle_id())) && (reservation_existante.getId()!=reservation.getId()))){
                throw new ServiceException("Voiture déjà reservée");
            } else if (IOUtils.estReservePdt30j(LResa_par_vehicle,reservation)){
                throw new ServiceException("Voiture réservée trop longtemps");
            }
            return reservationDao.update(reservation);
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    public List<Reservation> findAll() throws ServiceException {
        try{
            return reservationDao.findAll();
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try{
            return reservationDao.delete(reservation);
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    public List<Reservation> findResaByVehicleId(long vehicleid) throws ServiceException {
        try{
            return reservationDao.findResaByVehicleId(vehicleid);
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    public Reservation findResaById(long id) throws ServiceException {
        try{
            return reservationDao.findResaById(id);
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    public List<Reservation> findResaByClientId(long clientid) throws ServiceException {
        try{
            return reservationDao.findResaByClientId(clientid);
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    public int count() throws ServiceException {
        try{
            return reservationDao.countAll();
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
}