package com.springboot.service;

import com.springboot.MyExecption.ExpirationException;
import com.springboot.MyExecption.RessourceNotFoundException;
import com.springboot.security.Token;

import java.security.NoSuchAlgorithmException;

public interface TokenService {
    public String generate(String mdp,int id) throws NoSuchAlgorithmException;
    public void delete(int idadmin) throws Exception;
    public Token checkToken(int ida) throws ExpirationException;
    public  void Create(String mdp,int id) throws Exception;
    public  void checkTokens(String tok,int id) throws RessourceNotFoundException, ExpirationException;
}
