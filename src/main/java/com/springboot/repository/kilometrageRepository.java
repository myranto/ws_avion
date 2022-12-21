package com.springboot.repository;

import com.springboot.model.Kilometrage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface kilometrageRepository extends JpaRepository<Kilometrage, Integer>{
    List<Kilometrage> findAllByIdavion(int idavion);
}
