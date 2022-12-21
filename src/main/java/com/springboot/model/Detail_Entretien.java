package com.springboot.model;

import com.springboot.sgbd.DAO.ObjectBDD;
import com.springboot.sgbd.inter.KeyAnnotation;
import com.springboot.sgbd.inter.TableAnnotation;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "Detail_Entretien")
public class Detail_Entretien extends ObjectBDD {
    @KeyAnnotation
    @Id
    @Column(name = "identretien",nullable = false)
    private int identretien;
    @Column(name = "libelle",nullable = false)
    private String libelle;
    @Column(name = "depense",nullable = false)
    private int depense;

    public int getIdentretien() {
        return identretien;
    }

    public void setIdentretien(int identretien) {
        this.identretien = identretien;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getDepense() {
        return depense;
    }

    public void setDepense(int depense) {
        this.depense = depense;
    }

    public Detail_Entretien() {
    }

    public Detail_Entretien(int identretien, String libelle, int depense) {
        this.identretien = identretien;
        this.libelle = libelle;
        this.depense = depense;
    }


    @Override
    protected void colonneLiaison(int idliaison) {

    }
}
