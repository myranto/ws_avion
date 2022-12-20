package com.springboot.model;

import com.springboot.Connex.Connexion;
import com.springboot.sgbd.DAO.ObjectBDD;
import com.springboot.sgbd.inter.KeyAnnotation;
import com.springboot.sgbd.inter.TableAnnotation;

import java.util.ArrayList;

@TableAnnotation(nameTable = "detail_entretien")
public class Detail_Entretien extends ObjectBDD {
    @KeyAnnotation
    private int identretien;
    @KeyAnnotation
    private String libelle;
    @KeyAnnotation
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
    public ArrayList<Detail_Entretien> selectByIdEntretien(int id) throws Exception {
        return SelectAllByQuery(Connexion.getConnection(),"select * from detail_entretien where identretien="+id);
    }

    @Override
    protected void colonneLiaison(int idliaison) {

    }
}
