package com.example.backend.service;

import com.example.backend.dto.EmployeeDto;

import java.util.List;

public interface Service {
    public List<EmployeeDto> getAllById(Long id);
    public List<EmployeeDto> get();
    public EmployeeDto post(EmployeeDto employeeDto);
    public EmployeeDto update(Long id,EmployeeDto employeeDto);

    void delete(Long id);
}
