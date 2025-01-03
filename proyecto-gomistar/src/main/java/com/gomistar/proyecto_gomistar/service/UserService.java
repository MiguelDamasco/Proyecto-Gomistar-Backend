package com.gomistar.proyecto_gomistar.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ConfirmEmailDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.DTO.request.getIdUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CheckUserPasswordDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CheckUserUsernameDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.email.ConfirmationTokenEntity;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;
import com.gomistar.proyecto_gomistar.service.email.ConfirmationTokenService;
import com.gomistar.proyecto_gomistar.service.email.EmailService;

import jakarta.mail.MessagingException;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailService emailService;

    public UserService(UserRepository pUserRepository, PasswordEncoder pPasswordEncoder, ConfirmationTokenService pConfirmationTokenService, EmailService pEmailService) {
        this.userRepository = pUserRepository;
        this.passwordEncoder = pPasswordEncoder;
        this.confirmationTokenService = pConfirmationTokenService;
        this.emailService = pEmailService;
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

    public List<UserEntity> findUsersWithoutShip() {

        List<UserEntity> listResult = new ArrayList<>();
        List<UserEntity> listUsers = (List<UserEntity>) this.userRepository.findAll();

        for(UserEntity user : listUsers) {

            if(user.getShip() == null && user.getEmployee() != null) {

                listResult.add(user);
            }
        }

        return listResult;
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

    public Set<RoleEntity> getRoles(String pIdUser) {

        UserEntity myUser = this.getUser(pIdUser);
        return myUser.getRoles();
    }

    public void addConfirmationToken(String pIdUser) {

        UserEntity myUser = this.getUser(pIdUser);

        ConfirmationTokenEntity myConfirmationToken = this.confirmationTokenService.createConfirmationToken(myUser);

        myUser.addToken(myConfirmationToken);

        this.userRepository.save(myUser);

        String subject = "Confirmación de correo electronico";

        String message = "Clave: " + myConfirmationToken.getToken();

        EmailDTO myDTO = new EmailDTO(myUser.getEmail(), subject, message);

        try {
            this.emailService.sendMail(myDTO);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RequestException("P-230", "Error al mandar el correo de confirmacion de email");
        }

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
                                                .isConfirmed(false)
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
                                                    .isConfirmed(false)
                                                    .build();
    
            this.userRepository.save(myUser);
    
            return myUser;
        }

    @Transactional
    public void updateUserTokens(UserEntity user, List<ConfirmationTokenEntity> newTokens) {
       
        user.getTokens().clear();
        
        user.getTokens().addAll(newTokens);
        
        userRepository.save(user);
    }

    public void confirmEmail(ConfirmEmailDTO pDTO) {

        UserEntity myUser = this.getUser(pDTO.idUser());
        List<ConfirmationTokenEntity> myList = this.confirmationTokenService.updateTokenActive(myUser);
        updateUserTokens(myUser, myList);
        ConfirmationTokenEntity result = this.confirmationTokenService.confimrToken(myList, pDTO.token());
        if(result == null) {
            throw new RequestException("P-211", "Codigo de confirmacion no valido");
        }
        
        myUser.setConfirmed(true);
        this.userRepository.save(myUser);
        this.confirmationTokenService.desactivateToken(result.getUsers().getId());

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

    public void deleteUser(String pId) {

        UserEntity myUser = this.getUser(pId);
        this.userRepository.delete(myUser);
    }

}
