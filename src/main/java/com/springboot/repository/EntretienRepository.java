package com.springboot.repository;

import com.springboot.model.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EntretienRepository extends JpaRepository<Entretien, Integer> {
    List<Entretien> findAllByIdavion(int idavion);

}
