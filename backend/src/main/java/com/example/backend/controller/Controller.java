package com.example.backend.controller;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.jwt.JwtUtils;
import com.example.backend.service.Service;
import com.example.backend.service.ServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class Controller {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Service service;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "Welcome this is git";
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<EmployeeDto>> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.getAllById(id),HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDto>> get() {
        return new ResponseEntity<>(service.get(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/post")
    public ResponseEntity<EmployeeDto> post(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(service.post(employeeDto),HttpStatus.CREATED);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<EmployeeDto> put(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(service.update(id,employeeDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);
    }


}
