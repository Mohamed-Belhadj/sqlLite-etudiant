package com.example.database;

public class Etudiant {

    private int _id;
    private String nom;
    private String prenom;
    private int presence;

    public Etudiant(String nom, String prenom, int presence) {
        this.nom = nom;
        this.prenom = prenom;
        this.presence = presence;
    }

    // Getters
    public int getId() {
        return _id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getPresence() {
        return presence;
    }
    // Setters
    public void setId(int _id) {
        this._id = _id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setPresence(int presence) {
        this.presence = presence;
    }
}

