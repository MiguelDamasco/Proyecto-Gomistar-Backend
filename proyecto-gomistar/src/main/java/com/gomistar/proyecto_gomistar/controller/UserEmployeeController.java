package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.UserEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ModifyUserNameDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.model.UserEntity;
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

        UserEntity myUser = this.userEmployeeService.createUserEmployee(pUser);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Usuario creado correctamente!",
            myUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
}
