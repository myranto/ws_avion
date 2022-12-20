package com.springboot.repository;

import com.springboot.model.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntretienRepository extends JpaRepository<Entretien, Integer> {
    List<Entretien> findAllByIdavion(int idavion);
}
