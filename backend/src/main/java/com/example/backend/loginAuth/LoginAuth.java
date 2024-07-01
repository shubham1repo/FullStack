package com.example.backend.loginAuth;

import com.example.backend.dto.LoginResponse;
import com.example.backend.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoginAuth extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthenticationManager authenticationManager;
     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
             throws ServletException, IOException {
         Authentication authentication;
         try {
             logger.info("AuthManager called");
             Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
             String username = requestBody.get("username");
             String password = requestBody.get("password");
             logger.info(username+" "+password);

             authentication = authenticationManager
                     .authenticate(new UsernamePasswordAuthenticationToken(username,password));
         } catch (AuthenticationException exception) {
             Map<String, Object> map = new HashMap<>();
             map.put("message", "Bad credentials");
             map.put("status", false);
//             return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
             logger.error(map, exception);
             return;
         }
         SecurityContextHolder.getContext().setAuthentication(authentication);

         UserDetails userDetails = (UserDetails) authentication.getPrincipal();

         String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

         List<String> roles = userDetails.getAuthorities().stream()
                 .map(item -> item.getAuthority())
                 .collect(Collectors.toList());
            response.setHeader("Authorization", "Bearer " + jwtToken);
         filterChain.doFilter(request, response);
//         LoginResponse response = new LoginResponse(userDetails.getUsername(),jwtToken,roles,true);
//
//         return ResponseEntity.ok(response);
     }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/login");
    }
}
