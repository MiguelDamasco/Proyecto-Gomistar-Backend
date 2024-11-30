package com.gomistar.proyecto_gomistar.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.AddEmployeeToUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.UserDTO;
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

        if(myUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("usuario creado con exito!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario!");
    }

    @PatchMapping
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeToUserDTO myDTO) {
        
        boolean result = this.userService.addEmployee(myDTO);

        if(!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya tiene empleado");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Empleado añadido correctamente!");
    }

}
