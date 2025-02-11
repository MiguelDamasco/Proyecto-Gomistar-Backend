package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    
    private final RoleService roleService;

    public RoleController(RoleService pRoleService) {
        this.roleService = pRoleService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveRole(@RequestParam String pName) {

        RoleEntity myRole = this.roleService.save(pName);
        ApiResponse<RoleEntity> response = new ApiResponse<>(
            "Rol creado!",
            myRole
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRoles() {

        List<RoleEntity> roleList = this.roleService.listAll();
        ApiResponse<List<RoleEntity>> response = new ApiResponse<>(
        "Lista de roles",
        roleList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
