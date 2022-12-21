package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Detail_Entretien;
import com.springboot.model.Entretien;
import com.springboot.repository.DetailEntretienRepository;
import com.springboot.repository.EntretienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/avion/entretien")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.PUT})
public class EntretienController {

    private final EntretienRepository EntretienRepository;
    private final DetailEntretienRepository rep;

    public EntretienController(EntretienRepository EntretienRepository, DetailEntretienRepository rep) {
        super();
        this.EntretienRepository = EntretienRepository;
        this.rep = rep;
    }

    @GetMapping
    public ResponseEntity<ToJsonData> getAllEntretien() {
        ToJsonData<List<Entretien>> entretien = new ToJsonData<>(EntretienRepository.findAll());
        return new ResponseEntity<>(entretien, HttpStatus.ACCEPTED);
    }
    @GetMapping("/detail")
    public ResponseEntity<ToJsonData> getDetail(@RequestParam(name = "identretien") int identretien){
        try {
            ToJsonData<List<Detail_Entretien>> entretien = new ToJsonData<>(rep.find(identretien));
            return new ResponseEntity<>(entretien, HttpStatus.OK);
        }catch (Exception e){
            ToJsonData<String> error = new ToJsonData<>(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<ToJsonData> updateEntretien(@PathVariable int id, @RequestBody Entretien entretiens) {
        try {
            Entretien entretien = EntretienRepository.findById(id).orElseThrow(() -> new AssertionError("Entretien Id not found !!!"));
            entretien.setDate(entretiens.getDate());
            entretien.setIdavion(entretiens.getIdavion());
            EntretienRepository.save(entretien);
            ToJsonData<Entretien> ent = new ToJsonData<>(entretien);
            return ResponseEntity.ok(ent);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ToJsonData> getEntretienById(@PathVariable int id) {
        try {
            List<Entretien> entretien = EntretienRepository.findAllByIdavion(id);
            ToJsonData<List<Entretien>> ent = new ToJsonData<>(entretien);
            return ResponseEntity.ok(ent);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ToJsonData>  DeleteEntretien(@PathVariable int id) {
        try {
            Entretien entretien = EntretienRepository.findById(id).orElseThrow(() -> new AssertionError("Entretien Id not found !!!"));
            EntretienRepository.deleteById(id);
            ToJsonData<String> message = new ToJsonData<>("deleting object successfully");
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

}
