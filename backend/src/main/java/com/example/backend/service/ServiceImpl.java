package com.example.backend.service;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.dto.UserDto;
import com.example.backend.mapper.EmployeeMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Employee;
import com.example.backend.model.User;
import com.example.backend.repository.Repository;
import com.example.backend.repository.UserRepository;
import lombok.extern.flogger.Flogger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    private Repository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private ModelMapper modelMapper;

    public List<EmployeeDto> getAllById(Long id) {
        List<Employee> employees=repository.findAllById(Collections.singleton(id));
        List<EmployeeDto> employeeDtoList=employees.stream().map((EmployeeMapper::mapToEmployeeDto)).toList();
        return employeeDtoList;
    }
    public List<EmployeeDto> get(){
        List<Employee> employees=repository.findAll();

        List<EmployeeDto> employeeDtoList=employees.stream().map((EmployeeMapper::mapToEmployeeDto)).toList();
        return employeeDtoList;
    }
    public EmployeeDto post(EmployeeDto employeeDto){
        Employee employee=EmployeeMapper.mapToEmployee(employeeDto);
        employee=repository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public EmployeeDto update(Long id,EmployeeDto employeeDto){
        Employee exsitedEmployee=repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        exsitedEmployee.setName(employeeDto.getName());
        exsitedEmployee.setAddress(employeeDto.getAddress());
        repository.save(exsitedEmployee);
        return EmployeeMapper.mapToEmployeeDto(exsitedEmployee);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
    public UserDto register(UserDto userDto) {



        System.out.println(userDto);
        User response=userRepository.findByUsername(userDto.getUsername());
        if(response!=null){
            throw new RuntimeException("User name already exists");
        }
        User result =modelMapper.map(userDto,User.class);
        System.out.println("test"+result);
        result.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        userRepository.save(result);
        System.out.println("test"+result);
        return modelMapper.map(result,UserDto.class);
    }

}
