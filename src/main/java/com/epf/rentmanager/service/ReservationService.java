package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur création reservations" + e.getMessage());
        }
    }
    public long update(Reservation reservation) throws ServiceException {
        try{
            return reservationDao.update(reservation);
        }catch(DaoException e){
            throw new ServiceException("Erreur update réservation"+e.getMessage());
        }
    }
    public List<Reservation> findAll() throws ServiceException {
        try{
            return reservationDao.findAll();
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservations"+e.getMessage());
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try{
            return reservationDao.delete(reservation);
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservations"+e.getMessage());
        }
    }
    public List<Reservation> findResaByVehicleId(long vehicleid) throws ServiceException {
        try{
            return reservationDao.findResaByVehicleId(vehicleid);
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservations"+e.getMessage());
        }
    }
    public Reservation findResaById(long id) throws ServiceException {
        try{
            return reservationDao.findResaById(id);
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservation"+e.getMessage());
        }
    }
    public List<Reservation> findResaByClientId(long clientid) throws ServiceException {
        try{
            return reservationDao.findResaByClientId(clientid);
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservations"+e.getMessage());
        }
    }
    public int count() throws ServiceException {
        try{
            return reservationDao.countAll();
        }catch(DaoException e){
            throw new ServiceException("Erreur trouver reservations"+e.getMessage());
        }
    }
}