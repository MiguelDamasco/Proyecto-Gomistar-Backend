package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.DTO.request.getIdUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CheckUserPasswordDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CheckUserUsernameDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.RoleEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository pUserRepository, PasswordEncoder pPasswordEncoder) {
        this.userRepository = pUserRepository;
        this.passwordEncoder = pPasswordEncoder;
    }

    public getIdUserDTO getId(String pUsername) {

        Optional<UserEntity> myUserOptional = this.userRepository.findByUsername(pUsername);

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-233", "El usuario: " + pUsername + " no existe");
        }

        return new getIdUserDTO(String.valueOf(myUserOptional.get().getId()));
    }

    public Long findIdByUsername(String pUsername) {

        Optional<UserEntity> myUserOptional = this.userRepository.findByUsername(pUsername);

        if(!myUserOptional.isPresent()) {
            throw new RequestException("P-233", "El usuario: " + pUsername + " no existe");
        }

        return myUserOptional.get().getId();
    }

    public boolean existUsername(String pUsername) {
        return this.userRepository.existsByUsername(pUsername);
    }

    public boolean existEmail(String pEmail) {
        return this.userRepository.existsByEmail(pEmail);
    }

    public boolean checkPassword(CheckUserPasswordDTO pDTO) {

        UserEntity myUser = this.getUser(pDTO.idUser());

        if(!this.passwordEncoder.matches(pDTO.password(), myUser.getPassword())) {
            throw new RequestException("P-296", "Contraseña incorrecta!");
        }

        return true;
    }

    public boolean checkUsername(CheckUserUsernameDTO pDTO) {

        boolean result = this.existUsername(pDTO.username());

        if(result) {
            throw new RequestException("P-293", "Ya existe el username, ingrese uno que no exista.");
        }

        return true;
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

    public UserEntity save(UserDTO pUser, Set<RoleEntity> roleList) {

        if(existEmail(pUser.email())) {
            throw new RequestException("p-222", "el email ya existe!");
        }

        String password = this.passwordEncoder.encode(pUser.password());

       UserEntity myUser = UserEntity.builder().email(pUser.email())
                                                .password(password)
                                                .username(pUser.username())
                                                .roles(roleList)
                                                .build();

        this.userRepository.save(myUser);

        return myUser;

    }
        public UserEntity save(UserDTO pUser) {

            if(existEmail(pUser.email())) {
                throw new RequestException("p-222", "el email ya existe!");
            }
    
            
            String password = this.passwordEncoder.encode(pUser.password());
    
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
