package com.springboot.service.implement;

import com.springboot.FormatToJson.ErrorCode;
import com.springboot.FormatToJson.ToJsonData;
import com.springboot.MyExecption.RessourceNotFoundException;
import com.springboot.model.Admin;
import com.springboot.repository.AdminRepository;
import com.springboot.security.Token;
import com.springboot.service.AdminServices;
import com.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class AdminServiceImplements implements AdminServices {

    private final AdminRepository rep;
    private final TokenService serv;
    public AdminServiceImplements(AdminRepository rep, TokenService serv) {
        super();
        this.rep = rep;
        this.serv = serv;
    }

    public ResponseEntity<ToJsonData> login(Admin adm) {

        String sql = "Select id from admin where email = '" + adm.getEmail() + "' and pwd = '" + adm.getPwd()+"'";
        int id = 0;
//        PreparedStatement pstmt = null;
            try {
            Admin ress= rep.findAdminByEmailAndAndPwd(adm.getEmail(),adm.getPwd());
//                System.out.println("tonga ato");
//                Admin admin = ar.findById(id).orElseThrow(()->new RessourceNotFoundException("admin","login",adm.getEmail()));
                System.out.println(ress.getEmail());
                Token tok = new Token();
                tok.setId(ress.getId());
                serv.Create(ress.getPwd(),ress.getId());
                tok = serv.checkToken(ress.getId());
                ress.setToken(tok.getTok());
                ToJsonData<Admin> ad = new ToJsonData<>(ress);
                return new ResponseEntity<ToJsonData>(ad, HttpStatus.ACCEPTED);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                return new ResponseEntity<ToJsonData>(new ToJsonData(e.getMessage()), HttpStatus.NOT_FOUND);
            }
    }

    @Override
    public ResponseEntity<ToJsonData> Logout(int id) throws Exception {
        ToJsonData<String> check = new ToJsonData<>("log out reussi");
        serv.delete(id);;
        return new ResponseEntity<ToJsonData>(check, HttpStatus.OK);
    }
}
