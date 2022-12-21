package com.springboot.repository;

import com.springboot.model.Detail_Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DetailEntretienRepository extends JpaRepository<Detail_Entretien,Integer> {
    @Query(nativeQuery = true,value = "select * from detail_entretien where identretien=?1")
    List<Detail_Entretien> find(int id);
}
