package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.UserEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserEmailDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserLastnameDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserNameDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserPasswordDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserUsernameDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserEmployeeService;

@RestController
@RequestMapping("/userEmployee")
public class UserEmployeeController {
    
    private final UserEmployeeService userEmployeeService;

    public UserEmployeeController(UserEmployeeService pUserEmployeeService) {
        this.userEmployeeService = pUserEmployeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEmployeeDTO pUser) {

        System.out.println("Username: " + pUser.username());
        System.out.println("Email: " + pUser.email());
        System.out.println("Nombre: " + pUser.name());
        System.out.println("Apellido: " + pUser.lastname());
        System.out.println("Password: " + pUser.password());
        System.out.println("Roles: " + pUser.roles());
        UserEntity myUser = this.userEmployeeService.createUserEmployee(pUser);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario creado correctamente!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<?> listUsers() {

        List<UserEmployeeResponseDTO> myList = this.userEmployeeService.listUsers();
        ApiResponse<List<UserEmployeeResponseDTO>> response = new ApiResponse<>(
            "Lista de todos los usuarios",
             myList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam String pId) {

        UserEmployeeResponseDTO myDTO = this.userEmployeeService.getUser(pId);
        ApiResponse<UserEmployeeResponseDTO> response = new ApiResponse<>(
            "Usuario encontrado!",
            myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/modify_name")
    public ResponseEntity<?> modifyName(@RequestBody ModifyUserNameDTO pDTO) {
        
        UserEntity myUser = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Nombre modificado con exito!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/modify_lastname")
    public ResponseEntity<?> modifyLastname(@RequestBody ModifyUserLastnameDTO pDTO) {
     
        UserEntity myUEntity = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Apellido modificado correctamente!",
            myUEntity
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/modify_email")
    public ResponseEntity<?> modifyEmail(@RequestBody ModifyUserEmailDTO pDTO) {
        
        UserEntity myUEntity = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Email modificado correctamente!",
            myUEntity
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/modify_username")
    public ResponseEntity<?> modifyUsername(@RequestBody ModifyUserUsernameDTO pDTO) {

        UserEntity myUEntity = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario modificado correctamente!",
            myUEntity
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/modify_password")
    public ResponseEntity<?> modifyPassword(@RequestBody ModifyUserPasswordDTO pDTO) {

        UserEntity myUEntity = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Contraseña modificado correctamente!",
            myUEntity
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify_user")
    public ResponseEntity<?> modifyUser(@RequestBody ModifyUserDTO pDTO) {

        System.out.println("Username: " + pDTO.username());
        System.out.println("Nombre: " + pDTO.name());
        System.out.println("Apellido: " + pDTO.lastname());
        UserEntity myUser = this.userEmployeeService.modifyUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario modificado con exito!",
            myUser);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String pId) {

        this.userEmployeeService.deleteUser(pId);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario eliminado con exito!",
             null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
