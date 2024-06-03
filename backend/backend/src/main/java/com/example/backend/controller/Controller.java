package com.example.backend.controller;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.service.Service;
import com.example.backend.service.ServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class Controller {

    @Autowired
    private Service service;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "Welcome";
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<EmployeeDto>> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.getAllById(id),HttpStatus.ACCEPTED);
    }

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

}
