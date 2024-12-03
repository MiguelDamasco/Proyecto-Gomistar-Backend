package com.gomistar.proyecto_gomistar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.EmployeeDTOModify;
import com.gomistar.proyecto_gomistar.DTO.response.ResponseGetEmployeeDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;
import com.gomistar.proyecto_gomistar.service.document.UserEmployeeService;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    private final UserEmployeeService userEmployeeService;

    public EmployeeService(EmployeeRepository pEmployeeRepository, UserEmployeeService pUserEmployeeService) {
        this.employeeRepository = pEmployeeRepository;
        this.userEmployeeService = pUserEmployeeService;
    }

    public EmployeeEntity modifyEmployee(EmployeeDTOModify pEmployee) {

        Optional<EmployeeEntity> myEmployeeOptional = this.employeeRepository.findById(Long.parseLong(pEmployee.id()));

        if(!myEmployeeOptional.isPresent()) {
            throw new RequestException("P-226", "El empleado a modificar no existe");
        }

        EmployeeEntity myEMployee = myEmployeeOptional.get();

        myEMployee.setName(pEmployee.name());
        myEMployee.setLastName(pEmployee.lastname());

        this.employeeRepository.save(myEMployee);

        return myEMployee;
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


    public EmployeeEntity saveEmployee(CreateEmployeeDTO pEmployee) {

        EmployeeEntity myEmployee = EmployeeEntity.builder().name(pEmployee.name())
                                                            .lastName(pEmployee.lastname())
                                                            .isActive(true)
                                                            .build();

        return this.employeeRepository.save(myEmployee);
    }


    public EmployeeEntity saveEmployee(AddEmployeeToUserDTO pDto) {

        EmployeeEntity myEmployee = EmployeeEntity.builder().name(pDto.name())
                                                            .lastName(pDto.lastname())
                                                            .isActive(true)
                                                            .build();

        return this.employeeRepository.save(myEmployee);
    }

    public EmployeeEntity deleteEmployee(String pId) {

        Optional<EmployeeEntity> myEmployeeOptional = this.employeeRepository.findById(Long.parseLong(pId));

        if(!myEmployeeOptional.isPresent()) {
            throw new RequestException("P-227", "El empleado no existe");
        }

        EmployeeEntity myEmployee = myEmployeeOptional.get();

        myEmployee.setActive(false);

        this.userEmployeeService.removeEmployee(Long.parseLong(pId));

        return myEmployee;

    }

    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return this.employeeRepository.save(employee);
    }

    public Optional<EmployeeEntity> findById(Long id) {
        return this.employeeRepository.findById(id);
    }

}
