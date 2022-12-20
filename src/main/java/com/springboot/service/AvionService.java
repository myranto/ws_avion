package com.springboot.service;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Avion;
import org.springframework.http.ResponseEntity;

public interface AvionService {
    public ResponseEntity<ToJsonData> save(Avion exp);
    public ResponseEntity<ToJsonData> delete(int id);
    public ResponseEntity<ToJsonData> update(Avion up, int id);
    public ResponseEntity<ToJsonData> getAll();
    public ResponseEntity<ToJsonData> getOneById(int id);
    public ResponseEntity<ToJsonData> updateImg(Avion av);
}
