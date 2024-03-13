package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation_avec_nom_prenom_constr_modele {
    private long id;
    private String nom;
    private String prenom;
    private String constructeur;
    private String modele;
    private LocalDate debut;
    private LocalDate fin;
    private long vehicle_id;

    public Reservation_avec_nom_prenom_constr_modele(long id, String nom, String prenom, String constructeur, String modele, LocalDate debut, LocalDate fin,long vehicle_id) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.constructeur = constructeur;
        this.modele = modele;
        this.debut = debut;
        this.fin = fin;
        this.vehicle_id = vehicle_id;
    }

    public Reservation_avec_nom_prenom_constr_modele() {
        this.id = 1;
        this.nom = "dev";
        this.prenom = "fab";
        this.constructeur = "audi";
        this.modele = "R04";
        this.debut = LocalDate.now();
        this.fin = LocalDate.now();
        this.vehicle_id=1;
    }

    @Override
    public String toString() {
        return "Reservation_avec_nom_prenom_constr_modele{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", debut=" + debut +
                ", fin=" + fin +
                ", vehicle_id="+vehicle_id+
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
}

