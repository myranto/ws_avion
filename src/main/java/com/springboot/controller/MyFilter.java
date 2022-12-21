package com.springboot.controller;

import com.google.gson.Gson;
import com.springboot.FormatToJson.ErrorCode;
import com.springboot.security.Token;
import com.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(1)
public class MyFilter implements Filter {
    private final TokenService serve;

    public MyFilter(TokenService serve) {
        this.serve = serve;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String token = req.getParameter("token");
        String id = req.getParameter("id");
        String action = (req.getParameter("login")!=null)?req.getParameter("login"):"";
        int idu = (id!=null)?Integer.parseInt(id):0;
        Token tok = new Token(idu,token);
        try {
            if ((!action.equals("login"))&&(idu!=0)) {
                serve.checkTokens(token,idu);
            }
        }catch (Exception e){
            res.addHeader("Content-Type","application/json;charset=UTF-8");
            res.addHeader("Access-Control-Allow-Origin","*");
            res.addHeader("Access-Control-Allow-Headers","*");
//            System.out.println(e.getMessage());
            res.getWriter().println(ConvertToJsonObject(e.getMessage()));
            res.setStatus(200);
//            res.sendRedirect("http://localhost:8000/flotte/Exception/message/"+e.getMessage());
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private String ConvertToJsonObject(String mess){
        ErrorCode error = new ErrorCode(mess,401);
        Gson g = new Gson();
        return g.toJson(error);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}