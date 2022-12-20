package com.springboot.service.implement;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.MyExecption.RessourceNotFoundException;
import com.springboot.model.Avion;
import com.springboot.repository.AvionRepository;
import com.springboot.service.AvionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvionImplementService implements AvionService {
    private AvionRepository rep;

    public AvionImplementService(AvionRepository rep) {
        super();
        this.rep = rep;
    }

    @Override
    public ResponseEntity<ToJsonData> save(Avion exp) {
        ToJsonData<Object> f=null;
        HttpStatus stat = HttpStatus.CREATED;
        try {
            f = new ToJsonData<>(rep.save(exp));

        }catch (Exception e){
            stat=HttpStatus.NOT_FOUND;
            f = new ToJsonData<>(e.getMessage());
        }
        return new ResponseEntity<>(f,stat);
    }

    @Override
    public ResponseEntity<ToJsonData> delete(int id) {
        HttpStatus stat = HttpStatus.OK;
        ToJsonData<String> check = new ToJsonData<>("delete reussi");
        try {
            rep.findById(id).orElseThrow(()->new RessourceNotFoundException("Avion","id",id));
            rep.deleteById(id);
        }catch (RessourceNotFoundException e){
            check = new ToJsonData<>(e.getMessage());
            stat = HttpStatus.NOT_FOUND;
        }
        return  new ResponseEntity<ToJsonData>(check,stat);
    }

    @Override
    public ResponseEntity<ToJsonData> update(Avion up, int id) {
        ToJsonData<String> check = new ToJsonData<>("update reussi");
        HttpStatus stat = HttpStatus.OK;

        try {
            Avion last = rep.findById(id).orElseThrow(()->new RessourceNotFoundException("Avion","id",id));
            last.setId(id);
            last.setPhoto(up.getPhoto());
            last.setNom(up.getNom());
            rep.save(last);
        }catch (RessourceNotFoundException e){
            check = new ToJsonData<>(e.getMessage());
            stat = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<ToJsonData>(check,stat);
    }
    @Override
    public ResponseEntity<ToJsonData> getAll() {
        List<Avion> list=  rep.findAll();
        ToJsonData<List<Avion>> js = new ToJsonData<List<Avion>>(list);
        return new ResponseEntity<ToJsonData>(js,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ToJsonData> getOneById(int id) {
        ToJsonData<Avion> v = new ToJsonData<Avion>(new Avion());
        try {
            Avion last = rep.findById(id).orElseThrow(()->new RessourceNotFoundException("Avion","id",id));
            v = new ToJsonData<Avion>(last);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ToJsonData>(v,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ToJsonData> updateImg(Avion av) {
        Avion v  = rep.findById(av.getId()).orElseThrow(()->new RessourceNotFoundException("Avion","id",av.getId()));
        v.setPhoto(av.getPhoto());
        rep.save(v);
        return new ResponseEntity<>(new ToJsonData<String>("update reussi"),HttpStatus.OK);
    }

}
