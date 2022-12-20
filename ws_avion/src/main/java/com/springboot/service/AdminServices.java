package com.springboot.service;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminServices {

    public ResponseEntity<ToJsonData> login(Admin adm);
    public ResponseEntity<ToJsonData> Logout(int id)throws Exception;
}
