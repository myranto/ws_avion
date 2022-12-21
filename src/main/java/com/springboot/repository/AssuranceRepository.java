package com.springboot.repository;

import com.springboot.model.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance,Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM assurance where EXTRACT(MONTH FROM date_assurance)=?1")
    List<Assurance> selectAllByIdAvionExpiration(int mois);
}
