package com.gomistar.proyecto_gomistar.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.document.UserEmployeeService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    private final UserEmployeeService userEmployeeService;

    public UserController(UserService pUserService, UserEmployeeService pUserEmployeeService) {
        this.userService = pUserService;
        this.userEmployeeService = pUserEmployeeService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllUsers() {

        List<UserEntity> myUserList = this.userService.getAllUser();
        ApiResponse<List<UserEntity>> response = new ApiResponse<>(
            "Lista con todos los usuarios",
            myUserList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getUser(@RequestParam String pId) {

        UserEntity myUser = this.userService.getUser(pId);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario encontrado!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO pUser) {

        UserEntity myUser = this.userService.save(pUser);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario creado correctamente!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyUser(@RequestBody UserDTOModify pUser) {

        UserEntity myUser = this.userService.modify(pUser);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario modificado correctamente!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeToUserDTO myDTO) {
        
        UserEntity myUser = this.userEmployeeService.addEmployee(myDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Empleado añadido correctamente",
            myUser
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
