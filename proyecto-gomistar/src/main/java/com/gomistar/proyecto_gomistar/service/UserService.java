package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final EmployeeService employeeService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository pUserRepository, EmployeeService pEmployeeService, BCryptPasswordEncoder pPasswordEncoder) {
        this.userRepository = pUserRepository;
        this.employeeService = pEmployeeService;
        this.bCryptPasswordEncoder = pPasswordEncoder;
    }

    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public UserEntity save(UserEntity user) {
        return this.userRepository.save(user);
    }

    public boolean existEmail(String pEmail) {
        return this.userRepository.existsByEmail(pEmail);
    }

    public Optional<UserEntity> getByEmployee(String pid) {
        return this.userRepository.findByEmployee(Long.parseLong(pid));
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

    public UserEntity modify(UserDTOModify pUser) {

        Optional<UserEntity> myUserOptional = this.findById(Long.parseLong(pUser.id()));

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-223", "No existe el usuario!");
        }

        UserEntity myUser = myUserOptional.get();

        myUser.setEmail(pUser.email());
        myUser.setUsername(pUser.username());
        myUser.setPassword(pUser.password());

        this.userRepository.save(myUser);

        return myUser;
    }

    public UserEntity save(UserDTO pUser) {

        if(existEmail(pUser.email())) {
            throw new RequestException("p-222", "el email ya existe!");
        }

        String password = this.bCryptPasswordEncoder.encode(pUser.password());

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

    public UserEntity addEmployee(AddEmployeeToUserDTO pDTO) {
        
        Optional<UserEntity> myUserOptional = findById(Long.valueOf(pDTO.idUser()));

        if(!myUserOptional.isPresent()) {
            throw new RequestException("p-225", "El usuario seleccionado no existe");
        }

        UserEntity myUser = myUserOptional.get();

        EmployeeEntity myEmployee = this.employeeService.saveEmployee(pDTO);

        myUser.setEmployee(myEmployee);

        this.userRepository.save(myUser);

        return myUser;

    }
}
