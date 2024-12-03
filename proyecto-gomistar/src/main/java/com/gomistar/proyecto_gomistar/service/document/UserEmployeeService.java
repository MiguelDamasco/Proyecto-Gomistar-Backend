package com.gomistar.proyecto_gomistar.service.document;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.exception.RequestException;
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
