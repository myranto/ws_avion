package com.springboot.security;


import com.springboot.Connex.Connexion;
import com.springboot.MyExecption.ExpirationException;
import com.springboot.MyExecption.RessourceNotFoundException;
import com.springboot.MyExecption.SecurityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;

public class Token {

    private int id;
    private String tok;
    private Timestamp date=null;

    public String getTok() {
        return tok;
    }

    public void setTok(String tok) {
        this.tok = tok;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Token() {
    }

    public Token(int id, String tok) {
        this.id = id;
        this.tok = tok;
    }

    public Token(int id, String tok, Timestamp date) {
        this.id = id;
        this.tok = tok;
        this.date = date;
    }

    public static int Rand(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
//    generate token
    public String generate(String mdp,int id) throws NoSuchAlgorithmException {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] mess = md.digest(String.valueOf(id).getBytes());
        BigInteger no = new BigInteger(1,mess);
        byte[] pwd = md.digest(String.valueOf(t).getBytes());
        BigInteger p = new BigInteger(1,pwd);
        String hashmdp = p.toString(16);
        String Hash = no.toString(16);
        while (Hash.length()<16)
        {
            Hash+="0";
        }
        while (hashmdp.length()<16){
            hashmdp+="1";
        }
        String value = hashmdp+Hash;
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Token checkToken(int ida) throws ExpirationException, SQLException {
        String sql = "select *,current_timestamp cur from Token where idadmin="+ida+" order by dateexpiration desc limit 1";
        System.out.println(sql);
        Connection con = null;
        try {
            con = Connexion.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Statement stat = con.createStatement();
        ResultSet res = stat.executeQuery(sql);
        Token tok = null;
        Timestamp cur = null;
        if(res.next()){
            tok = new Token();
            tok.setTok(res.getString("token"));
            tok.setId(res.getInt("idadmin"));
            tok.setDate(res.getTimestamp("dateexpiration"));
            cur = res.getTimestamp("cur");
        }
        setDate(new Timestamp(System.currentTimeMillis()));
        try {

            if ((cur.compareTo(tok.getDate())>0) || (tok.getDate()==null)){
                setDate(cur);
                throw new ExpirationException("expiration");
            }
        }catch (Exception e){
//            throw new ExpirationException("expirerr")
            throw e;
        }
        return tok;
    }
    public void delete() throws Exception {
        String sql = "delete from Token where idadmin="+getId();
        Connection con =Connexion.getConnection();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        con.commit();
    }
//    public void saveToken(int ida,String mdp,boolean isLog) throws Exception {
//        try {
//            this.checkToken(ida);
//            if (isLog)
//                throw new Exception("new session log");
//        } catch (Exception e) {
//          String token=  generate(mdp,ida);
//          execute(token,getDate(),ida);
//        }
//    }
    public void execute(String tok,Timestamp date,int ida) throws Exception {

        date.setMinutes(date.getMinutes()+15);
        String sql = "INSERT INTO Token(idadmin,token,dateexpiration) values ("+ida+",'"+tok+"','"+date+"')";
        Connection con =Connexion.getConnection();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        con.commit();
    }
    public Token getToken() throws Exception {
        String sql = "select * from Token where idadmin="+getId()+" and token='"+getToken()+"'";
        Connection con =Connexion.getConnection();
        Statement stat = con.createStatement();
       ResultSet res =stat.executeQuery(sql);
       Token result = null;
        if (res.next()) {
            result = new Token(res.getInt("id"),res.getString("token"),res.getTimestamp("dateexpiration"));
        }
        if (result==null){
            throw new SecurityException("Token not found for the id "+getId());
        }
        return result;
    }
    public void Create(String mdp) throws Exception {
        String token=  generate(mdp,getId());
        execute(token,new Timestamp(System.currentTimeMillis()),getId());
    }
//    check token
    public  void checkTokens(String tok) throws RessourceNotFoundException, ExpirationException {
        try {
           Token t = checkToken(getId());
           if (!tok.equals(t.getTok())){
               throw new RessourceNotFoundException("token is expired ","id",getId());
           }
        } catch (ExpirationException e) {
           throw e;
        } catch (SQLException es) {
            throw new RuntimeException(es.getMessage());
        }catch (RessourceNotFoundException ex){
            throw ex;
        }

    }
    public int getId() {
        return id;
    }
//    insert into admin(nom,email,pwd) values ('moi','moi@gmail.com','mdp1');
//    public static void main(String[] args) throws Exception {
//        Token tok = new Token();
//        tok.setId(1);
//        tok.saveToken(1,"mdp1");
//    }

}
