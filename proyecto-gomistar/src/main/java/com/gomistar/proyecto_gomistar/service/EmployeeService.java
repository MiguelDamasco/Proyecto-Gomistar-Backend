package com.gomistar.proyecto_gomistar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.response.ResponseGetEmployeeDTO;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository pEmployeeRepository) {
        this.employeeRepository = pEmployeeRepository;
    }


    public ResponseGetEmployeeDTO getEmployee(String id) {

        Optional<EmployeeEntity> myEmployee = this.employeeRepository.findById(Long.parseLong(id));

        return myEmployee.map(employee -> 
                ResponseGetEmployeeDTO.builder()
                                      .employeeEntity(employee)
                                      .message("Empleado encontrado!")
                                      .build())
                         .orElseGet(() -> 
                ResponseGetEmployeeDTO.builder()
                                      .employeeEntity(null)
                                      .message("Error, no se ha encontrado al empleado")
                                      .build());
    }


    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return this.employeeRepository.save(employee);
    }

    public Optional<EmployeeEntity> findById(Long id) {
        return this.employeeRepository.findById(id);
    }

}