package com.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "avion")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nom",nullable = false)
    private String nom;
    @Column(name = "Photo",nullable = false)
    private String Photo;

    public Avion() {
    }

    public Avion(int id, String nom, String photo) {
        this.id = id;
        this.nom = nom;
        Photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
