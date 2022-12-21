package com.springboot.repository;

import com.springboot.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    public void deleteTokenByIdadmin(int idadmin);
    public Token findTokenByIdadminOrderByDateDescTokDesc(int idadmin);
}
