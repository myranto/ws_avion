package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Assurance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/flotte/assurance")
@CrossOrigin
public class AssuranceController {
    public AssuranceController() {
        super();
    }
    @GetMapping("/get-limit")
    public ResponseEntity<ToJsonData> getLimiteAssurance(@RequestParam(name = "mois") int mois){
        try {
            ToJsonData<ArrayList<Assurance>> json = new ToJsonData<>(new ArrayList<Assurance>());
            ArrayList<Assurance> list = new Assurance().selectAllByIdAvionExpiration(mois);
            if (list.size()<=0)
                throw new Exception("there are not assurance");
            json = new ToJsonData<>(list);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }catch (Exception e){
                return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
