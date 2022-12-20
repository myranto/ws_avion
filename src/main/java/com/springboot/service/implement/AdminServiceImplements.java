package com.springboot.service.implement;

import com.springboot.Connex.Connexion;
import com.springboot.FormatToJson.ErrorCode;
import com.springboot.FormatToJson.ToJsonData;
import com.springboot.MyExecption.RessourceNotFoundException;
import com.springboot.model.Admin;
import com.springboot.repository.AdminRepository;
import com.springboot.security.Token;
import com.springboot.service.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class AdminServiceImplements implements AdminServices {

    @Autowired
    private AdminRepository ar;

    public ResponseEntity<ToJsonData> login(Admin adm) {

        String sql = "Select id from admin where email = '" + adm.getEmail() + "' and pwd = '" + adm.getPwd()+"'";
        int id = 0;
        PreparedStatement pstmt = null;
            try {
            pstmt =  Connexion.getConnection().prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                id = res.getInt("id");
            }
//                System.out.println("tonga ato");
                Admin admin = ar.findById(id).orElseThrow(()->new RessourceNotFoundException("admin","login",adm.getEmail()));
                System.out.println(admin.getEmail());
                Token tok = new Token();
                tok.setId(admin.getId());
                tok.Create(admin.getPwd());
                tok = tok.checkToken(admin.getId());
                admin.setToken(tok.getTok());
                ToJsonData<Admin> ad = new ToJsonData<>(admin);
                return new ResponseEntity<ToJsonData>(ad, HttpStatus.ACCEPTED);

            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<ToJsonData>(new ToJsonData(e.getMessage()), HttpStatus.NOT_FOUND);
            }
    }

    @Override
    public ResponseEntity<ToJsonData> Logout(int id) throws Exception {
        ToJsonData<String> check = new ToJsonData<>("log out reussi");
        Token tok = new Token();
        tok.setId(id);
        tok.delete();
        return new ResponseEntity<ToJsonData>(check, HttpStatus.OK);
    }
}
