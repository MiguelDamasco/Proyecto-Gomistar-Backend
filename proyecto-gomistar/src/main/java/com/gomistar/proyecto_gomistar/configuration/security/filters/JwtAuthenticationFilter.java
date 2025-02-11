package com.gomistar.proyecto_gomistar.configuration.security.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.security.core.userdetails.User;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomistar.proyecto_gomistar.configuration.security.jwt.JwtUtils;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils pJwtUtils) {
        this.jwtUtils = pJwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                  
        UserEntity userEntity = null;
        String username;
        String password;

        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        }
        catch (StreamReadException e) {
            throw new RuntimeException(e);
        }
        catch (DatabindException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                     FilterChain chain, Authentication authResult) throws IOException, ServletException    {    
                                        
      User user = (User) authResult.getPrincipal();

      String token = this.jwtUtils.generateAccesToken(user.getUsername());

      response.addHeader("Authorization", token);
      
      Map<String, Object> httpResponse = new HashMap<>();

      httpResponse.put("token", token);
      httpResponse.put("message", "Autenticación correcta!");
      httpResponse.put("username", user.getUsername());

      response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
      response.setStatus(200);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().flush();
    }
    
}
