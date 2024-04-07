package com.epf.rentmanager.ui.cli;

import static com.epf.rentmanager.utils.IOUtils.*;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.CLIException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.FillDatabase;
import com.epf.rentmanager.service.*;
import java.util.List;

import java.time.LocalDate;

public class CLI {
    /**
     * Legacy : interface en ligne de commande
     * @param args
     * @throws CLIException
     */
    public static void main(String[] args) throws CLIException {
        //ClientService instance=ClientService.getInstance();
        print("Bienvenue dans l'interface CLI :");
        print("Veuillez sélectionner 1 ou 2 pour Client ou Véhicules");
        boolean bool=false;
        do {
            String x=readString();
            if (x.equals("1")){
                bool=true;
                print("Bienvenue dans votre espace Client");
                print("Selectionner 1 ou 2 pour créer client ou lister tous les clients");
                String x1=readString();
                if (x1.equals("1")){
                    try {
                        print("Veuillez saisir les informations du client à créer :");
                        long id = readInt("ID :");
                        String nom = readString("Nom :", true);
                        String prenom = readString("Prenom :", true);
                        String email = readString("Email :", true);
                        LocalDate date = readDate("Date de naissance :", true);
                        //long idcreate =instance.create(new Client(id, nom, prenom, email, date));
                        //print(String.valueOf(idcreate));
                    }catch (Exception e){
                        throw new CLIException(e.getMessage());
                    }
                } else if(x1.equals("2")){
                    try {
                        print("Voici les informations des clients de la base de données :");
                        //List<Client> L1 =instance.findAll();
                    }catch (Exception e){
                        throw new CLIException(e.getMessage());
                    }

                }else {
                    bool=false;
                }

            } else if (x.equals("2")) {
                bool=true;
                print("Bienvenue votre espace Vehicules");
                print("Selectionner 1 ou 2 pour créer véhicules ou lister tous les véhicules");
            }
            print("Veuillez sélectionner une bonne réponse.");
        }while (!bool);

    }
}
