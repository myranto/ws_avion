package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Avion;
import com.springboot.service.AvionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flotte/avion")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.PUT})
public class AvionController {
    private AvionService service;

    public AvionController(AvionService service) {
        super();
        this.service = service;
    }
    @PostMapping()
    public ResponseEntity<ToJsonData> saveVehicule(@RequestBody Avion v){
        return service.save(v);
    }
    @PutMapping("{id}")
    public  ResponseEntity<ToJsonData> UpdateVehicule(@PathVariable("id") int id,@RequestBody Avion e){
        return service.update(e,id);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ToJsonData> deleteVehiculebyID(@PathVariable("id") int id){

        return service.delete(id);

    }
    @PostMapping("/files")
    public ResponseEntity<ToJsonData> updateImg(@RequestBody Avion av){
        return service.updateImg(av);
    }
    @GetMapping("/get-all")
    public ResponseEntity<ToJsonData> getAll(){
        return service.getAll();
    }
    @GetMapping("/get-all/{id}")
    public ResponseEntity<ToJsonData> GetById(@PathVariable("id")  int id)
    {
        return service.getOneById(id);
    }
}
