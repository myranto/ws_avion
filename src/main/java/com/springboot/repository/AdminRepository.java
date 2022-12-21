package com.springboot.repository;

import com.springboot.model.Admin;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends  JpaRepository<Admin, Integer>{
    public Admin findAdminByEmailAndAndPwd(String email,String pwd);
}
