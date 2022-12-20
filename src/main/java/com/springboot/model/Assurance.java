package com.springboot.model;

import com.springboot.Connex.Connexion;
import com.springboot.sgbd.DAO.ObjectBDD;
import com.springboot.sgbd.inter.KeyAnnotation;
import com.springboot.sgbd.inter.TableAnnotation;

import java.sql.Date;
import java.util.ArrayList;

@TableAnnotation(nameTable = "assurance")
public class Assurance extends ObjectBDD {
    @Override
    protected void colonneLiaison(int idliaison) {}
    @KeyAnnotation
    private Date date_assurance;
    @KeyAnnotation
    private Date datedebut;

    @KeyAnnotation
    private double a_payer;
    @KeyAnnotation
    private int idavion;

    public int getIdavion() {
        return idavion;
    }

    public void setIdavion(int idavion) {
        this.idavion = idavion;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDate_assurance() {
        return date_assurance;
    }

    public void setDate_assurance(Date date_assurance) {
        this.date_assurance = date_assurance;
    }

    public double getA_payer() {
        return a_payer;
    }

    public void setA_payer(double a_payer) {
        this.a_payer = a_payer;
    }

    public void saveAll() throws Exception {
        super.saveAll(Connexion.getConnection());
    }
    public ArrayList<Assurance> selectAll() throws Exception {
        return super.SelectAll(Connexion.getConnection());
    }
    public ArrayList<Assurance> selectAllByIdAvionExpiration(int mois) throws Exception {
        String sql = "select * from assurance where date_part('month',date_assurance) = (SELECT date_part('month',current_date + interval '"+mois+" month'))";
        System.out.println(sql);
        return super.SelectAllByQuery(Connexion.getConnection(),sql);
    }
}
