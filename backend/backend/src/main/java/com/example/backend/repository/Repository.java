package com.example.backend.repository;

import com.example.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee,Long> {
}
