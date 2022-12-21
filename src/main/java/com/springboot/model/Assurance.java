package com.springboot.model;

import com.springboot.sgbd.DAO.ObjectBDD;
import com.springboot.sgbd.inter.KeyAnnotation;
import com.springboot.sgbd.inter.TableAnnotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Assurance")
public class Assurance extends ObjectBDD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Override
    protected void colonneLiaison(int idliaison) {}
    @Column(name = "date_assurance",nullable = false)
    private Date date_assurance;
    @Column(name = "datedebut",nullable = false)
    private Date datedebut;


    @Column(name = "a_payer",nullable = false)
    private double a_payer;

    @Column(name = "idavion",nullable = false)
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


    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
