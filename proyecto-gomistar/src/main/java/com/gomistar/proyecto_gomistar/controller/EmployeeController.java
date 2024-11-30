package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiFaildResponse;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponseOne;
import com.gomistar.proyecto_gomistar.DTO.response.ResponseGetEmployeeDTO;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService pEmployeeService) {
        this.employeeService = pEmployeeService;
    }


    @GetMapping("/list")
    public ResponseEntity<?> getEmployee(@RequestParam String id) {

        ResponseGetEmployeeDTO response = this.employeeService.getEmployee(id);

        if(response.getEmployeeEntity() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveEmployee(@RequestBody CreateEmployeeDTO pEmployee) {

        EmployeeEntity myEmployee = this.employeeService.saveEmployee(pEmployee);
        ApiResponseOne<EmployeeEntity> response = new ApiResponseOne<>(
            "empleado creado exitosamente!",
            HttpStatus.CREATED.value(),
            myEmployee
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
