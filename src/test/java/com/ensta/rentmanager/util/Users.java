package com.ensta.rentmanager.util;

import com.epf.rentmanager.model.Client;

import java.time.LocalDate;

public class Users {
    /**
     * Renvoie true si l’utilisateur passé en paramètre a un age >= 18 ans
     * @param user L'instance d’utilisateur à tester
     * @return Résultat du test (>= 18 ans)
     */
    public static boolean isLegal(Client user) {
        return
                user.getNaissance().isBefore(LocalDate.now().minusYears(18));
    }
}
