package com.app.ws.springboot.security;

import com.app.ws.springboot.SpringApplicationContext;
import com.app.ws.springboot.services.UserService;
import com.app.ws.springboot.services.imp.UserServiceImp;
import com.app.ws.springboot.shared.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import com.app.ws.springboot.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserRequest credential = new ObjectMapper().readValue(request.getInputStream(),UserRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getEmail(),credential.getPassword(),new ArrayList<>())
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String email = ((User)authResult.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis()+WebSecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,WebSecurityConstant.TOKEN_SECRET)
                .compact();
        UserService u = (UserService) SpringApplicationContext.getBean("userServiceImp");
        UserDto userDto = u.getUser(email);

        response.addHeader(WebSecurityConstant.HEADER_STRING,WebSecurityConstant.TOKEN_PREFIX+token);
        response.addHeader("user_id",userDto.getUserId());
        response.getWriter().write("{\"token\": \"" + token + "\", \"id\": \""+ userDto.getUserId() + "\"}");
    }
}
