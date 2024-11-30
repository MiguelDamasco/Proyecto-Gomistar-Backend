package com.gomistar.proyecto_gomistar.controller;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.CreateEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateUserDTO;
import com.gomistar.proyecto_gomistar.model.ERole;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.RoleEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;
import com.gomistar.proyecto_gomistar.repository.UserRepository;
import com.gomistar.proyecto_gomistar.service.EmployeeService;
import com.gomistar.proyecto_gomistar.service.UserService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/hola")
public class PrincipalController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String saludo2() {
        return "Hola!";
    }

    @GetMapping("/saludo_usuaro")
    @PreAuthorize("hasRole('USER')")
    public String saludo() {
        return "Hola solo usuarios!";
    }

    @GetMapping("/saludo_seguro")
    @PreAuthorize("hasRole('ADMIN')")
    public String saludoSeguro() {
        return "Hola desde un lugar seguro!";
    }
/* 
    @PostMapping("/crear_empleado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeDTO employee) {
        EmployeeEntity myEmployee = EmployeeEntity.builder().name(employee.getName())
                                                            .lastName(employee.getLastName())
                                                            .build();

        this.employeeService.addEmployee(myEmployee);

        return ResponseEntity.ok(myEmployee);
    } */
  
    @PostMapping("/añadir_empleado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@RequestParam String idEmployee, @RequestParam String idUser) {
        Optional<EmployeeEntity> myEmployee = this.employeeService.findById(Long.valueOf(idEmployee));
        Optional<UserEntity> myUser = this.userService.findById(Long.valueOf(idUser));

        if(myEmployee.isPresent() && myUser.isPresent()) {
            UserEntity user = myUser.get();
            if(user.getEmployee() != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya tiene empleado asignado");
            }
            user.setEmployee(myEmployee.get());
            this.userService.save(user);
            return ResponseEntity.ok(user);
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createuser(@Valid @RequestBody CreateUserDTO userDTO) {

        Set<RoleEntity> roles = userDTO.getRoles().stream()
                                .map(role -> RoleEntity.builder()
                                                        .name(ERole.valueOf(role))
                                                        .build())
                                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                                        .username(userDTO.getUsername())
                                        .email(userDTO.getEmail())
                                        .password(passwordEncoder.encode(userDTO.getPassword()))
                                        .roles(roles)
                                        .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> deleteUser(@RequestParam String id) {
        userRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok("Eliminado correctamente!");
    }
}
