package com.springboot.model;
import com.springboot.Connex.Connexion;
import com.springboot.sgbd.DAO.ObjectBDD;
import com.springboot.sgbd.inter.KeyAnnotation;
import com.springboot.sgbd.inter.TableAnnotation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kilometrage")
@TableAnnotation(nameTable = "kilometrage")
public class Kilometrage extends ObjectBDD {
    @Id
    @Column(name = "idavion")
    @KeyAnnotation(column = "idavion")
    private int idavion;
    @Column(name = "date_Kil")
    @KeyAnnotation(column = "date_Kil")
    private Date date;
    @Column(name = "debutKM")
    @KeyAnnotation(column = "debutKM")
    private double debut;
    @Column(name = "finKM")
    @KeyAnnotation(column = "finKM")
    private double fin;
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDebut() {
        return debut;
    }

    public void setDebut(double debut) {
        this.debut = debut;
    }

    public double getFin() {
        return fin;
    }

    public void setFin(double fin) {
        this.fin = fin;
    }
    public ArrayList<Kilometrage> selectAllById(int idavion) throws Exception {
        String sql = "select * from kilometrage where idavion="+idavion;
        return SelectAllByQuery(Connexion.getConnection(),sql);
    }

    @Override
    protected void colonneLiaison(int idliaison) {

    }
}