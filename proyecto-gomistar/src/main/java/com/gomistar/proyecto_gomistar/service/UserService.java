package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final EmployeeService employeeService;

    public UserService(UserRepository pUserRepository, EmployeeService pEmployeeService) {
        this.userRepository = pUserRepository;
        this.employeeService = pEmployeeService;
    }

    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public UserEntity save(UserEntity user) {
        return this.userRepository.save(user);
    }

    public UserEntity save(UserDTO pUser) {

        String password = new BCryptPasswordEncoder().encode(pUser.password());

       UserEntity myUser = UserEntity.builder().email(pUser.email())
                                                .password(password)
                                                .username(pUser.username())
                                                .build();

        this.userRepository.save(myUser);

        return myUser;
    }

    public List<UserEntity> getAll() {
        return (List<UserEntity>) this.userRepository.findAll();
    }

    public boolean addEmployee(AddEmployeeToUserDTO pDTO) {
        
        Optional<UserEntity> myUserOptional = findById(Long.valueOf(pDTO.idUser()));

        if(!myUserOptional.isPresent()) {
            return false;
        }

        UserEntity myUser = myUserOptional.get();

        EmployeeEntity myEmployee = this.employeeService.saveEmployee(pDTO);

        myUser.setEmployee(myEmployee);

        this.userRepository.save(myUser);

        return true;

    }
}
