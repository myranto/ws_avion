package com.springboot.Connex;
import java.sql.*;
public class Connexion {
    public static Connection getConnection() throws Exception {
        Connection con=null;
        try{
//            Class.forName("org.postgresql.Driver");
            String url="postgres://jjcqkhti:IHmR4dqpmE1uEJMpgSEV8WNOHYRCtqgF@floppy.db.elephantsql.com/jjcqkhti";
	   		String user="jjcqkhti";
	   		String password="IHmR4dqpmE1uEJMpgSEV8WNOHYRCtqgF";
	   		con=(Connection) DriverManager.getConnection(url, user, password);
               con.setAutoCommit(false);
            return con;
        }
        catch(Exception e){
            throw e;
        }
    }
}
