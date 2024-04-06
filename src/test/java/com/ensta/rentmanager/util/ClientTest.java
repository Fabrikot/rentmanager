package com.ensta.rentmanager.util;

import com.epf.rentmanager.model.Client;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ClientTest {
    @Test
    void isLegal_should_return_true_when_age_is_over_18() {
        // Given
        Client legalUser = new Client(1,"John", "Doe", "john.doe@ensta.fr", LocalDate.parse("17/05/2002",
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        // Then
        assertTrue(Users.isLegal(legalUser));
    }

    @Test
    void isLegal_should_return_false_when_age_is_under_18() {
        // Given
        Client illegaUser = new Client(1,"John", "Doe", "john.doe@ensta.fr", LocalDate.now());

        // Then
        assertFalse(Users.isLegal(illegaUser));
    }
}
