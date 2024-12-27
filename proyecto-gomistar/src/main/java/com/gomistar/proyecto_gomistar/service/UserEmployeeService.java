package com.gomistar.proyecto_gomistar.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.IUserModify;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserEmailDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserLastnameDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserNameDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserPasswordDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserUsernameDTO;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.role.ERole;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.model.user.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserEmployeeService {
    
    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;


    public UserEmployeeService(UserRepository pUserRepository, EmployeeRepository pEmployeeRepository, UserService pUserService, RoleService pRoleService, PasswordEncoder pPasswordEncoder) {
        this.userRepository = pUserRepository;
        this.employeeRepository = pEmployeeRepository;
        this.userService = pUserService;
        this.roleService = pRoleService;
        this.passwordEncoder = pPasswordEncoder;
    }

    public UserEmployeeResponseDTO getUser(String pId) {

        UserEntity myUser= this.userService.getUser(pId);
        return new UserEmployeeResponseDTO(String.valueOf(myUser.getId()),myUser.getUsername(), myUser.getPassword(), myUser.getEmail(), myUser.getEmployee().getName(), myUser.getEmployee().getLastName());
    }

    public List<UserEmployeeResponseDTO> listUsers() {

        List<UserEntity> myList = this.userService.getAllUser();
        List<UserEmployeeResponseDTO> resultList = new ArrayList<>();

        for(UserEntity myUser : myList) {
            if(myUser.getEmployee() != null) {
                UserEmployeeResponseDTO dto = new UserEmployeeResponseDTO(String.valueOf(myUser.getId()),myUser.getUsername(), myUser.getPassword(), myUser.getEmail(), myUser.getEmployee().getName(), myUser.getEmployee().getLastName());
                resultList.add(dto);
            }
        }

        return resultList;
    }

    public Set<RoleEntity> obtainRoles(List<String> pRole) {

        Set<RoleEntity> listRole = new HashSet<>();
        for(String s : pRole) {
            ERole myRole = this.roleService.obtainRole(s);
            RoleEntity myRoleEntity = this.roleService.findByName(myRole);
            listRole.add(myRoleEntity);
        }
        return listRole;
    }

    public UserEntity createUserEmployee(UserEmployeeDTO pUser) {

        Set<RoleEntity> listRole = this.obtainRoles(pUser.roles());
        UserEntity myUser = this.userService.save(new UserDTO(pUser.username(), pUser.password(), pUser.email()), listRole);
        UserEntity result = this.addEmployee(new AddEmployeeToUserDTO(String.valueOf(myUser.getId()), pUser.name(), pUser.lastname()));
        return result;
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

    public UserEntity modifyUser(IUserModify pUser) {

        UserEntity myUser;
        if(pUser instanceof ModifyUserNameDTO) {

            ModifyUserNameDTO myDTO = (ModifyUserNameDTO) pUser;
            myUser = this.userService.getUser(myDTO.idUser());
            myUser.getEmployee().setName(myDTO.name());
            return this.userRepository.save(myUser);
        }
        else if(pUser instanceof ModifyUserLastnameDTO) {

            ModifyUserLastnameDTO myDTO = (ModifyUserLastnameDTO) pUser;
            myUser = this.userService.getUser(myDTO.idUser());
            myUser.getEmployee().setLastName(myDTO.lastname());
            return this.userRepository.save(myUser);
        }
        else if(pUser instanceof ModifyUserEmailDTO) {

            ModifyUserEmailDTO myDTO = (ModifyUserEmailDTO) pUser;
            myUser = this.userService.getUser(myDTO.idUser());
            myUser.setEmail(myDTO.email());
            myUser.setConfirmed(false);
            return this.userRepository.save(myUser);
        }
        else if(pUser instanceof ModifyUserUsernameDTO) {

            ModifyUserUsernameDTO myDTO = (ModifyUserUsernameDTO) pUser;
            myUser = this.userService.getUser(myDTO.idUser());
            myUser.setUsername(myDTO.username());
            return this.userRepository.save(myUser);
        }
        else if(pUser instanceof ModifyUserPasswordDTO) {

            ModifyUserPasswordDTO myDTO = (ModifyUserPasswordDTO) pUser;
            myUser = this.userService.getUser(myDTO.idUser());
            System.out.println("idUsuario: " + myDTO.idUser());
            System.out.println("Contraseña vieja: " + myDTO.oldPassword());
            System.out.println("Contraseña nueva: " + myDTO.newPassword());
            System.out.println("igualdad: " + this.passwordEncoder.matches(myDTO.oldPassword(), myUser.getPassword()));
            this.checkPassword(myUser, myDTO.newPassword(), myDTO.oldPassword());
            String newPasswordCrypt = this.passwordEncoder.encode(myDTO.newPassword());
            myUser.setPassword(newPasswordCrypt);
            return this.userRepository.save(myUser);

        }
        throw new RequestException("P-297", "Campo a modificar no encontrado");
    }

    public void checkPassword(UserEntity pUser, String newPassword, String oldPassword) {

        if(!this.passwordEncoder.matches(oldPassword, pUser.getPassword())) {
            throw new RequestException("P-296", "Contraseña vieja incorrecta");
        }
        else if(newPassword.equals(oldPassword)) {
            throw new RequestException("P-255", "La nueva contraseña debe ser diferente de la actual!");
        }
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

    public UserEntity modifyUser(ModifyUserDTO pDTO) {
        
        UserEntity myUser = this.userService.getUser(pDTO.id());
        if(!myUser.getEmail().equalsIgnoreCase(pDTO.email())) {

            myUser.setEmail(pDTO.email());
            myUser.setConfirmed(false);
        }
        myUser.setUsername(pDTO.username());
        myUser.getEmployee().setName(pDTO.name());
        myUser.getEmployee().setLastName(pDTO.lastname());
        return this.userRepository.save(myUser);
    }

    public void deleteUser(String pId) {

        this.userService.deleteUser(pId);
    }

}
