package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;


    public UserService(UserRepository pUserRepository) {
        this.userRepository = pUserRepository;
    }

    public Long findIdByUsername(String pUsername) {

        Optional<UserEntity> myUserOptional = this.userRepository.findByUsername(pUsername);

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-233", "El usuario: " + pUsername + " no existe");
        }

        return myUserOptional.get().getId();
    }

    public boolean existEmail(String pEmail) {
        return this.userRepository.existsByEmail(pEmail);
    }

    public List<UserEntity> getAllUser() {
        return (List<UserEntity>) this.userRepository.findAll();
    }

    public UserEntity getUser(String pId) {

        Optional<UserEntity> myEmployeeOptional = this.userRepository.findById(Long.parseLong(pId));

        if(!myEmployeeOptional.isPresent()) {
            throw new RequestException("P-223", "El usuario buscado no existe");
        }

        return myEmployeeOptional.get();
    }

    public UserEntity save(UserDTO pUser) {

        if(existEmail(pUser.email())) {
            throw new RequestException("p-222", "el email ya existe!");
        }

        BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
        String password = passwordEnconder.encode(pUser.password());

       UserEntity myUser = UserEntity.builder().email(pUser.email())
                                                .password(password)
                                                .username(pUser.username())
                                                .build();

        this.userRepository.save(myUser);

        return myUser;
    }

    public UserEntity modify(UserDTOModify pUser) {

        Optional<UserEntity> myUserOptional = this.userRepository.findById(Long.parseLong(pUser.id()));

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

}
