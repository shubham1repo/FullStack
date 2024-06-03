package com.example.backend.service;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.mapper.EmployeeMapper;
import com.example.backend.model.Employee;
import com.example.backend.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    private Repository repository;

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

}
