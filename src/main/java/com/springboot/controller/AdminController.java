package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Admin;
import com.springboot.service.AdminServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flotte/Admin")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS})
public class AdminController {

    private AdminServices as;

    public AdminController(AdminServices as) {
        super();
        this.as = as;
    }

    @PostMapping("/login")
    public ResponseEntity<ToJsonData> login(@RequestBody  Admin admin){
            return as.login(admin);
    }
    @DeleteMapping("/log_out/{id}")
    public ResponseEntity<ToJsonData>LogOut(@PathVariable("id") int id){
        try {
            return as.Logout(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
