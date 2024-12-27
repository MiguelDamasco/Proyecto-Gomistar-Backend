package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.EmployeeDTOModify;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponseOne;
import com.gomistar.proyecto_gomistar.model.user.EmployeeEntity;
import com.gomistar.proyecto_gomistar.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService pEmployeeService) {
        this.employeeService = pEmployeeService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllEmployee() {

        List<EmployeeEntity> employeeList = this.employeeService.getAllEmployee();
        ApiResponse<List<EmployeeEntity>> response = new ApiResponse<>(
            "Lista de todos los empleados",
            employeeList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getEmployee(@RequestParam String pId) {

        EmployeeEntity myEmployee = this.employeeService.findEmployeeById(pId);
        ApiResponse<EmployeeEntity> response = new ApiResponse<>(
            "Empleadon encontrado!",
            myEmployee
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

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

    @PutMapping("/modify")
    public ResponseEntity<?> modifyEmployee(@RequestBody EmployeeDTOModify pEmployee) {
        EmployeeEntity myEmployee = this.employeeService.modifyEmployee(pEmployee);
        ApiResponse<EmployeeEntity> response = new ApiResponse<>(
            "Empleado modificado con exito!",
            myEmployee
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam String id) {

        EmployeeEntity myEmployee = this.employeeService.deleteEmployee(id);
        ApiResponse<EmployeeEntity> response = new ApiResponse<>(
            "Empleado eliminado con exito!",
            myEmployee
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
