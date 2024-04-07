package com.ensta.rentmanager.service;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.FillDatabase;
import com.epf.rentmanager.service.ClientService;
import org.h2.tools.DeleteDbFiles;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    void create_should_fail_when_nom_inf_a_trois_carac(){
        assertThrows(ServiceException.class,() -> clientService.create(new Client(99,"De","Fabiceps","fabdev@gmail", LocalDate.now().minusYears(20))));
    }
    @Test
    void create_should_fail_when_prenom_inf_a_trois_carac(){
        assertThrows(ServiceException.class,() -> clientService.create(new Client(99,"Deville","Fa","fabdev@gmail", LocalDate.now().minusYears(20))));
    }
    @Test
    void create_should_fail_when_client_mineur(){
        assertThrows(ServiceException.class,() -> clientService.create(new Client(99,"Deville","Fabiceps","fabdev@gmail", LocalDate.now())));
    }
    @Test
    void create_should_fail_when_email_existant() throws DaoException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(99, "Deville", "Fabiceps", "ben.afleck@email.com", LocalDate.now().minusYears(20)));

        when(clientDao.findAll()).thenReturn(clients);
        assertThrows(ServiceException.class, () -> clientService.create(new Client(99, "Testos", "Terone", "ben.afleck@email.com", LocalDate.now().minusYears(20))));
    }
    @Test
    void create_should_suceed_when_tout_ok() throws ServiceException {
        long l_predit =0;
        long l_créé;
        l_créé =clientService.create(new Client(99, "Testos", "Terone", "ben.afleck@email.com", LocalDate.now().minusYears(20)));
        assertEquals(l_predit,l_créé);
    }
}
