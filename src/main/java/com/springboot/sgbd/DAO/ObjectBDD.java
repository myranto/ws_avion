package com.springboot.sgbd.DAO;



import com.springboot.sgbd.inter.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class ObjectBDD {
    private int id;
    protected abstract void colonneLiaison(int idliaison);
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String getNomTable(){
        TableAnnotation table = getClass().getAnnotation(TableAnnotation.class);
        return table.nameTable();
    }

    private Field[] getField()
    {
        return getClass().getDeclaredFields();
    }
    public ArrayList<String> getAnnotationsValue(int iSsave) {
        Field[] champs = getField();
//        System.out.println(champs.length+"vttvzv");
        ArrayList<String> listeAnnot = new ArrayList<String>();
        for (Field f:champs) {
            if (f.getAnnotation(KeyAnnotation.class) == null) {
                continue;
            }
            if (f.getAnnotation(KeyAnnotation.class).column().equals("")) {
               if(iSsave==1) if (f.getAnnotation(DefaultValueAnnotation.class)!=null) continue;
                listeAnnot.add(f.getName());
                continue;
            }
            if(iSsave==1) if (f.getAnnotation(DefaultValueAnnotation.class)!=null) continue;
            listeAnnot.add(f.getAnnotation(KeyAnnotation.class).column());
        }
        return listeAnnot;
    }
    private ArrayList<String> getFieldValid(int iSsave)
    {
        Field[] champs = getField();
        ArrayList<String> listefield = new ArrayList<String>();
//        System.out.println(champs.length+" le tsy met");
        for (Field f:champs) {
            if (f.getAnnotation(KeyAnnotation.class) == null) {
                continue;
            }
            if (f.getAnnotation(KeyAnnotation.class).column().equals("")) {
                if(iSsave==1)  if (f.getAnnotation(DefaultValueAnnotation.class)!=null) continue;
                listefield.add(f.getName());
                continue;
            }
            if(iSsave==1) if (f.getAnnotation(DefaultValueAnnotation.class)!=null) continue;
            listefield.add(f.getName());
        }

        return listefield;
    }
    private ArrayList<String> ToStringStar(String type,int iSsave) {
        ArrayList<String> fieldValid=getFieldValid(iSsave);
        ArrayList<String> mety=new ArrayList<String>();
        for (String nom:fieldValid) {
//            System.out.println(nom);
            String tmp = type+nom;
//            System.out.println(tmp);
            mety.add(tmp);
        }
        return mety;
    }
    private  Boolean checkMethod(Method m, ArrayList<String> check) {
        for (String s : check) {
//            System.out.println(m.getName());
//            System.out.println("test "+s);
            if (m.getName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Method> getMethods(String type,int isSave) {
        Field[] champs = getField();
        Method[] list = getClass().getDeclaredMethods();
        ArrayList<Method> nameMethod = new ArrayList<Method>();
        int j = 0;
        ArrayList<String> list_method = ToStringStar(type,isSave);
        for (Method method : list) {
            if (checkMethod(method, list_method)) {
                nameMethod.add(method);
            }
        }
        ArrayList<Method> trie = new ArrayList<Method>();
//       for (int i = 0; i < champs.length; i++) {
        for (int l = 0; l < champs.length; l++) {
            String nom = type + champs[l].getName().toLowerCase();
            for (Method mm:nameMethod) {
                if (nom.equals(mm.getName().toLowerCase())) {
//                       System.out.println("method "+mm.getName());
                    trie.add(mm);
                }
            }
        }
//       }
//        System.out.println("size anle arry "+trie.size());
        return trie;
    }
    private Class[] getTypeMethods(ArrayList<Method> trie)
    {
        Class[] toReturn = new Class[trie.size()];
        for (int i = 0; i < trie.size() ; i++) {
//            System.out.println(" metthod "+trie.get(i).getName()+" indice "+i+" size "+trie.size());
            toReturn[i] = trie.get(i).getReturnType();
        }
        return toReturn;
    }
    public  void saveAll( Connection con) throws Exception {
        Field[] list = getField();
        ArrayList<Method> m = getMethods("get",1);
        ArrayList<String> listAnnot = getAnnotationsValue(1);
        for (int i = 0; i < listAnnot.size(); i++) {
            if (listAnnot.get(i).equals("id")) {
                listAnnot.remove(i);
            }
        }
//        TableAnnotation table = getClass().getAnnotation(TableAnnotation.class);
        for (int i = 0; i < m.size(); i++) {
            if (m.get(i).getName().toLowerCase().equals("getid")) {
                m.remove(i);
            }
        }
        StringBuilder sql = new StringBuilder("INSERT INTO " + getNomTable() + "(");
        for (int i = 0; i < listAnnot.size(); i++) {

            if (i == listAnnot.size() - 1) {
                sql.append(listAnnot.get(i)).append(") ");
                break;
            }
            sql.append(listAnnot.get(i)).append(",");
        }
        sql.append("VALUES (");
        for (int i = 0; i < m.size(); i++) {
            Object invocation = m.get(i).invoke(this, new Object[0]);
            if (i == m.size() - 1) {
                if ((invocation instanceof Integer)
                        || (invocation instanceof Double) || (invocation instanceof Float)) {
                    sql.append(invocation).append(")");
                } else {
                    sql.append("'").append(invocation).append("')");
                }
                break;
            }
            if ((invocation instanceof Integer)
                    || (invocation instanceof Double)) {
                sql.append(invocation).append(",");
            } else {
                sql.append("'").append(invocation).append("',");
            }
        }
//        System.out.println(sql);
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql.toString());
            stat.executeUpdate();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
    }
    public void Update(Connection con) throws Exception {
//        TableAnnotation table = getClass().getAnnotation(TableAnnotation.class);
        ArrayList<Method> m = getMethods("get",2);
        ArrayList<String> listAnnot = getAnnotationsValue(2);
        String sql = "update "+getNomTable()+" set ";
        for (int i = 0; i < listAnnot.size(); i++) {
            Object invocation = m.get(i).invoke(this, new Object[0]);
            if (i==listAnnot.size()-1){
                if ((invocation instanceof Integer) ||  (invocation instanceof Double) || (invocation instanceof Float))
                    invocation = invocation;
                else
                    invocation = "'"+invocation+"'";
                sql+= listAnnot.get(i)+"="+invocation+", id="+getId()+" where id="+getId()+"";
                break;
            }
            if ((invocation instanceof Integer) ||  (invocation instanceof Double) || (invocation instanceof Float))
                invocation = invocation;
            else
                invocation = "'"+invocation+"'";
            sql+= listAnnot.get(i)+"="+invocation+", ";
        }
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            stat.executeUpdate();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
    }
    public void delete(Connection con) throws Exception {
//        TableAnnotation table = getClass().getAnnotation(TableAnnotation.class);
        String sql = "delete from " + getNomTable() + " where id="+getId();
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            stat.executeUpdate();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
    }
    public Integer getId(Connection con) throws SQLException {
        ArrayList<Method> m = getMethods("set",2);
        Class[] type = getTypeMethods(getMethods("get",2));
        ArrayList<String> listAnnot = getAnnotationsValue(2);
        Integer list =0;
        String sql = "select id from "+getNomTable()+ " order by id desc limit 1";
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            ResultSet res =  stat.executeQuery();
            if (res.next()) {
               list = res.getInt("id");
            }
        } catch (Exception e) {
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
        return list;
    }
    public  <T extends ObjectBDD>ArrayList<T> SelectAllByQuery(Connection con,String sql) throws Exception{
        ArrayList<Method> m = getMethods("set",2);
        Class[] type = getTypeMethods(getMethods("get",2));
        ArrayList<String> listAnnot = getAnnotationsValue(2);
        ArrayList<T> list = new ArrayList<T>();
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            ResultSet res =  stat.executeQuery();
            int j=0;
            while (res.next()) {
                ObjectBDD obj =  this.getClass().getConstructor().newInstance();
                try {
                    obj.setId(res.getInt("id"));
                }catch (Exception ex){}
                for (Method met:m) {
                    Object zvt =  res.getObject(listAnnot.get(j));
                    if (zvt.getClass()==Integer.class)
                    {
                        met.invoke(obj,(int)zvt);
                    } else if (zvt.getClass()==Double.class) {
                        met.invoke(obj,(double)zvt);
                    }else if (zvt.getClass()==Float.class) {
                        met.invoke(obj,(float)zvt);
                    }
                    else{
                        met.invoke(obj,type[j].cast(zvt));
                    }
                    j++;
                }
                list.add((T) obj);
                j=0;
            }
        } catch (Exception e) {
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
        return list;
    }
    public  <T extends ObjectBDD>ArrayList<T> SelectAll(Connection con) throws Exception
    {
        ArrayList<Method> m = getMethods("set",2);
        Class[] type = getTypeMethods(getMethods("get",2));
//        System.out.println(type.length);
        ArrayList<String> listAnnot = getAnnotationsValue(2);
        ArrayList<T> list = new ArrayList<T>();
        String sql = "select * from "+getNomTable();
//        System.out.println(sql);
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            ResultSet res =  stat.executeQuery();
            int j=0;
            while (res.next()) {
               ObjectBDD obj =  this.getClass().getConstructor().newInstance();
               try {
                obj.setId(res.getInt("id"));
               }catch (Exception e){}
                for (Method met:m) {
                    Object zvt =  res.getObject(listAnnot.get(j));
                    if (zvt.getClass()==Integer.class)
                    {
                        met.invoke(obj,(int)zvt);
                    } else if (zvt.getClass()==Double.class) {
                        met.invoke(obj,(double)zvt);
                    }else if (zvt.getClass()==Float.class) {
                        met.invoke(obj,(float)zvt);
                    } else{
//                        System.out.println(j);
                        met.invoke(obj,type[j].cast(zvt));
                    }
                    j++;
                }
                list.add((T) obj);
                j=0;
            }
        } catch (Exception e) {
            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
        return list;
    }
    public  <T extends ObjectBDD>ArrayList<T> FindById(Connection con) throws Exception
    {
        ArrayList<T> list = new ArrayList<T>();
        ArrayList<Method> set = getMethods("set",2);
        Class[] type = getTypeMethods(getMethods("get",2));
        ArrayList<String> listAnnot = getAnnotationsValue(2);
        String sql = "select * from "+getNomTable()+" where id="+getId();
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(sql);
            ResultSet res =  stat.executeQuery();
            int j=0;
            while (res.next()) {
               ObjectBDD obj = this.getClass().getConstructor().newInstance();
                obj.setId(res.getInt("id"));
                for (Method met:set) {
                    Object zvt = res.getObject(listAnnot.get(j));
                    if (zvt.getClass()==Integer.class)
                    {
                        met.invoke(obj,(Integer)zvt);
                    } else if (zvt.getClass()==Double.class) {
                        met.invoke(obj,(Double)zvt);
                    } else{
                        met.invoke(obj,type[j].cast(zvt));
                    }
                    j++;
                }
                list.add((T) obj);
                j=0;
            }
        } catch (Exception e) {

            throw e;
        }
        finally{
            stat.close();
            con.close();
        }
        return list;
    }
    public Object getValue(String sql,String key,Connection con) throws SQLException {
        PreparedStatement stat = null;
        Object reste = 0;
        try {
            stat = con.prepareStatement(sql);
            ResultSet res = stat.executeQuery();
            if (res.next()){
                reste = res.getObject(key);
//                System.out.println(reste);
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            stat.close();
            con.close();
        }
        if ((reste instanceof  Long) || (reste instanceof Integer)) reste = Integer.valueOf( String.valueOf(reste));
        return reste;
    }
}
