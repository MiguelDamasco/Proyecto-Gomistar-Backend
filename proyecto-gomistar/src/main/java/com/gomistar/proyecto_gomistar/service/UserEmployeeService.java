package com.gomistar.proyecto_gomistar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.IUserModify;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserNameDTO;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ERole;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.RoleEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.EmployeeRepository;
import com.gomistar.proyecto_gomistar.repository.UserRepository;

@Service
public class UserEmployeeService {
    
    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    private final RoleService roleService;


    public UserEmployeeService(UserRepository pUserRepository, EmployeeRepository pEmployeeRepository, UserService pUserService, RoleService pRoleService) {
        this.userRepository = pUserRepository;
        this.employeeRepository = pEmployeeRepository;
        this.userService = pUserService;
        this.roleService = pRoleService;
    }

    public UserEmployeeResponseDTO getUser(String pId) {

        UserEntity myUser= this.userService.getUser(pId);
        return new UserEmployeeResponseDTO(myUser.getUsername(), myUser.getPassword(), myUser.getEmail(), myUser.getEmployee().getName(), myUser.getEmployee().getLastName());
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
        throw new RequestException("P-297", "Campo a modificar no encontrado");
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
