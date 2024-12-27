package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.IDocument;
import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.EmployeeDTOModify;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.user.EmployeeEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    private final UserEmployeeService userEmployeeService;

    public EmployeeService(EmployeeRepository pEmployeeRepository, UserEmployeeService pUserEmployeeService) {
        this.employeeRepository = pEmployeeRepository;
        this.userEmployeeService = pUserEmployeeService;
    }

    public List<EmployeeEntity> getAllEmployee() {

        return (List<EmployeeEntity>) this.employeeRepository.findAll();
    }

    public EmployeeEntity findEmployeeById(String pId) {

        Optional<EmployeeEntity> myEmployeeOptional = this.employeeRepository.findById(Long.parseLong(pId));

        if(!myEmployeeOptional.isPresent()) {
            throw new RequestException("P-280", "El empleado que buscas no existe");
        }

        return myEmployeeOptional.get();
    }

    public EmployeeEntity saveEmployee(CreateEmployeeDTO pEmployee) {

        EmployeeEntity myEmployee = EmployeeEntity.builder().name(pEmployee.name())
                                                            .lastName(pEmployee.lastname())
                                                            .isActive(true)
                                                            .build();

        return this.employeeRepository.save(myEmployee);
    }
    
    public EmployeeEntity saveEmployee(EmployeeEntity pEmployee) {

        return this.employeeRepository.save(pEmployee);
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

    public void addDocument(IDocument pDocument) {

    }
}
