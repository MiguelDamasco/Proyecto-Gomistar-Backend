package com.gomistar.proyecto_gomistar.service.document;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserEmployeeService {
    
    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    public UserEmployeeService(UserRepository pUserRepository, EmployeeRepository pEmployeeRepository) {
        this.userRepository = pUserRepository;
        this.employeeRepository = pEmployeeRepository;
    }

    public UserEntity addEmployee(AddEmployeeToUserDTO pDTO) {
        
        Optional<UserEntity> myUserOptional = this.userRepository.findById(Long.parseLong(pDTO.idUser()));

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-223", "El usuario seleccionado no existe");
        }

        UserEntity myUser = myUserOptional.get();

        EmployeeEntity myEmployee = EmployeeEntity.builder().name(pDTO.name())
                                                            .lastName(pDTO.lastname())
                                                            .isActive(true)
                                                            .build();

                
        this.employeeRepository.save(myEmployee);
        
        myUser.setEmployee(myEmployee);

        this.userRepository.save(myUser);

        return myUser;
    }

    public void removeEmployee(long pId) {
        
        Optional<UserEntity> myUserOptional = this.userRepository.findByEmployee(pId);

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-288", "El usuario a eliminar su empleado no existe");
        }

        UserEntity myUser = myUserOptional.get();

        myUser.setEmployee(null);

        this.userRepository.save(myUser);
    }

}
