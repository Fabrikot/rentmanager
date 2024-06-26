package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {
    @InjectMocks
    VehicleService vehicleService;
    @Mock
    VehicleDao vehicleDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create_should_fail_when_constructeur_vide(){
        assertThrows(ServiceException.class,() -> vehicleService.create(new Vehicle(99,"","3008",4)));
    }
    @Test
    void create_should_fail_when_modele_vide(){
        assertThrows(ServiceException.class,() -> vehicleService.create(new Vehicle(99,"Peugeot","",4)));
    }
    @Test
    void create_should_fail_when_nbplaces_incorrect(){
        assertThrows(ServiceException.class,() -> vehicleService.create(new Vehicle(99,"Peugeot","3008",1)));
        assertThrows(ServiceException.class,() -> vehicleService.create(new Vehicle(99,"Peugeot","3008",10)));
    }
    @Test
    void create_should_suceed_when_tout_ok() throws ServiceException {
        long l_predit =0;
        long l_créé;
        l_créé =vehicleService.create(new Vehicle(99,"Peugeot","3008",4));
        assertEquals(l_predit,l_créé);
    }
}
