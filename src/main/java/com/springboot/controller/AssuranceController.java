package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Assurance;
import com.springboot.repository.AssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flotte/assurance")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.PUT})
public class AssuranceController {
    private AssuranceRepository rep=null;
    public AssuranceController(AssuranceRepository rep) {
        super();
        this.rep = rep;
    }
    @GetMapping("/get-limit")
    public ResponseEntity<ToJsonData> getLimiteAssurance(@RequestParam(name = "mois") int mois){
        try {
            ToJsonData<List<Assurance>> json = new ToJsonData<>(new ArrayList<Assurance>());
//            ArrayList<Assurance> list = new Assurance().selectAllByIdAvionExpiration(mois);
            System.out.println(mois);
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            sqlDate.setMonth(sqlDate.getMonth()+mois);
            System.out.println(String.valueOf(sqlDate));
//            String sql = "WHERE EXTRACT(MONTH FROM date_assurance) = EXTRACT(MONTH FROM '"+sqlDate+"')";
            List<Assurance> list = rep.selectAllByIdAvionExpiration(sqlDate.getMonth());
            if (list.size()<=0)
                throw new Exception("there are not assurance");
            json = new ToJsonData<>(list);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
                return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
