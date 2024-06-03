package com.example.backend.mapper;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.model.Employee;
import jakarta.persistence.EntityManager;

public interface EmployeeMapper{
    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(employee.getId(),employee.getName(),employee.getAddress());
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getAddress());
    }
}
