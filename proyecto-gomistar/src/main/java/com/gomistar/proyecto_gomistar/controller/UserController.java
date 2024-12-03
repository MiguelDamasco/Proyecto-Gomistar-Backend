package com.gomistar.proyecto_gomistar.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    

    private final UserService userService;


    public UserController(UserService pUserService) {
        this.userService = pUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAll());
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
        
        UserEntity myUser = this.userService.addEmployee(myDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Empleado añadido correctamente",
            myUser
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
