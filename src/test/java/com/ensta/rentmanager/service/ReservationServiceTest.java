package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create_should_fail_when_deja_reserve_meme_journee() throws DaoException {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(99, 1, 2, LocalDate.now().minusDays(4), LocalDate.now()));

        when(reservationDao.findAll()).thenReturn(reservations);
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(2))));
    }
    @Test
    void create_should_fail_when_deja_reserve() throws DaoException {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(99, 1, 2, LocalDate.now().minusDays(4), LocalDate.now().plusDays(1)));

        when(reservationDao.findAll()).thenReturn(reservations);
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(2))));
    }
    @Test
    void create_should_fail_when_debut_resa_avant_fin(){
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().minusDays(2))));
    }
    @Test
    void create_should_fail_when_duree_resa_supp_7j(){
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(8))));
    }
    @Test
    void create_should_fail_when_resa_duree_30j_sans_pause() throws DaoException {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(99, 1, 2, LocalDate.now().minusDays(28), LocalDate.now().minusDays(21)));
        //pause
        reservations.add(new Reservation(99, 2, 2, LocalDate.now().minusDays(23), LocalDate.now().minusDays(16)));
        reservations.add(new Reservation(99, 3, 2, LocalDate.now().minusDays(15), LocalDate.now().minusDays(10)));
        reservations.add(new Reservation(99, 2, 2, LocalDate.now().minusDays(9), LocalDate.now().minusDays(1)));

        when(reservationDao.findResaByVehicleId(2)).thenReturn(reservations);
        assertThrows(ServiceException.class, () -> reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(7))));
    }
    @Test
    void create_should_suceed_when_resa_duree_30j_avec_pause() throws DaoException, ServiceException {
        List<Reservation> reservations = new ArrayList<>();
        long l_predit =0;
        long l_créé;

        reservations.add(new Reservation(99, 1, 2, LocalDate.now().minusDays(28), LocalDate.now().minusDays(21)));
        reservations.add(new Reservation(99, 2, 2, LocalDate.now().minusDays(23), LocalDate.now().minusDays(17)));
        //pause
        reservations.add(new Reservation(99, 3, 2, LocalDate.now().minusDays(15), LocalDate.now().minusDays(10)));
        reservations.add(new Reservation(99, 2, 2, LocalDate.now().minusDays(9), LocalDate.now().minusDays(1)));

        when(reservationDao.findResaByVehicleId(2)).thenReturn(reservations);
        l_créé = reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(7)));
        assertEquals(l_predit,l_créé);
    }
    @Test
    void create_should_suceed_when_tout_ok() throws ServiceException {
        long lpredit=0;
        long l_créé;
        l_créé=reservationService.create(new Reservation(99, 1, 2, LocalDate.now(), LocalDate.now().plusDays(7)));
        assertEquals(lpredit,l_créé);
    }
}
