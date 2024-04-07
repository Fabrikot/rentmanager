package com.epf.rentmanager.utils;

import com.epf.rentmanager.model.Reservation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;
public class IOUtils {
	
	/**
	 * Affiche un message sur la sortie standard
	 * @param message
	 */
	public static void print(String message) {
		System.out.println(message); 
	}
	
	/**
	 * Affiche un message sur la sortie standard
	 * @param message
	 * @param mandatory
	 */
	public static String readString(String message, boolean mandatory) {
		print(message);
		
		String input = null;
		int attempt = 0;
		
		do {
			if (attempt >= 1) {
				print("Cette valeur est obligatoire");
			}
			
			input = readString();
			attempt++;
		} while (mandatory && (input.isEmpty() || input == null));
		
		return input;
	}
	
	/**
	 * Lit un message sur l'entrée standard
	 */
	public static String readString() {
		Scanner scanner = new Scanner(System.in);
		String value = scanner.nextLine();
		
		return value;
	}
	
	/**
	 * Lit un entier sur l'entrée standard
	 * @param message
	 * @return
	 */
	public static int readInt(String message) {
		print(message);
		
		String input = null;
		int output = 0;
		boolean error = false;
		
		do {
			input = readString();
			error = false;
			
			try {
				output = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				error = true;
				print("Veuillez saisir un nombre");
			}
		} while (error);
		
		return output;
	}
	
	/**
	 * Lit une date sur l'entrée standard
	 * @param message
	 * @param mandatory
	 * @return
	 */
	public static LocalDate readDate(String message, boolean mandatory) {
		print(message);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
		LocalDate output = null;
		boolean error = false;
		
		do {
			try {
				error = false;
				String stringDate = readString();
	        	output = LocalDate.parse(stringDate, formatter);
	        } catch (DateTimeParseException e) {
	        	error = true;
	        	print("Veuillez saisir une date valide (dd/MM/yyyy)");
	        } 
		} while (error && mandatory);
        
		return output;
	}

	/**
	 * Permet de vérifier si une nouvelle réservation est possible par rapport à la list des réservations existantes
	 * @param reservations
	 * @param new_reservation
	 * @return vrai si il n'y a pas de réservations consécutives pdt 30j, faux sinon
	 */
	public static boolean estReservePdt30j(List<Reservation> reservations,Reservation new_reservation) {
		reservations.sort(Comparator.comparing(Reservation::getDebut));
		reservations.add(new_reservation);
		LocalDate datedefin_temporaire = reservations.get(0).getFin();
		int totaljours = Period.between(reservations.get(0).getDebut(),datedefin_temporaire).getDays();
		for (int i = 1; i < reservations.size(); i++) {
			Reservation reservation = reservations.get(i);
			if (reservation.getDebut().isAfter(datedefin_temporaire.plusDays(1))) {
				if (totaljours >= 30) {
					return true;
				}
				datedefin_temporaire = reservation.getDebut();
			} else {
				datedefin_temporaire = reservation.getFin();
				totaljours += Period.between(reservation.getDebut(), reservation.getFin()).getDays() + 1;
			}
		}
		if (totaljours >= 30) {
			return true;
		}
		return false;
	}
}
