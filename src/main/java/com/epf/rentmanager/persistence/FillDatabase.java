package com.epf.rentmanager.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.DeleteDbFiles;

import com.epf.rentmanager.persistence.ConnectionManager;

public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "RentManagerDatabase", true);
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;

        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Client(id INT primary key auto_increment, client_id INT, nom VARCHAR(100), prenom VARCHAR(100), email VARCHAR(100), naissance DATETIME)");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Ve" +
                "hicle(id INT primary key auto_increment, constructeur VARCHAR(100), modele VARCHAR(100), nb_places TINYINT(255))");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Reservation(id INT primary key auto_increment, client_id INT, foreign key(client_id) REFERENCES Client(id), vehicle_id INT, foreign key(vehicle_id) REFERENCES Vehicle(id), debut DATETIME, fin DATETIME)");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
            	createPreparedStatement = connection.prepareStatement(createQuery);
	            createPreparedStatement.executeUpdate();
	            createPreparedStatement.close();
            }

            // Remplissage de la base avec des Vehicules et des Clients
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Renault', 'Austral',  4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Peugeot', '308', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Seat', 'Ibiza', 6)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Nissan', 'Leaf', 4)");
            
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Serien', 'Jean', 'jean.serien@email.com', '2001-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Swift', 'Taylor', 'swift.taylor@email.com', '1999-04-13')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Afleck', 'Ben', 'ben.afleck@email.com', '1998-10-06')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Rousseau', 'Jacques', 'jacques.rousseau@email.com', '2000-08-18')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Cena', 'John', 'john.tututulu@email.com', '1996-06-14')");

            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(1, 1, '2022-02-10', '2022-02-16')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(3, 2, '2023-04-22', '2023-04-24')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(3, 2, '2023-05-11', '2023-05-17')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(3, 4, '2023-05-24', '2023-05-26')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(2, 3, '2023-10-05', '2023-10-10')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(4, 1, '2023-11-09', '2023-11-13')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(5, 3, '2024-04-09', '2023-04-15')");
            stmt.execute("INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(1, 1, '2024-04-12', '2023-04-17')");

            connection.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
