package com.gomistar.proyecto_gomistar.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.gomistar.proyecto_gomistar.DTO.request.UserDTOModify;
import com.gomistar.proyecto_gomistar.DTO.request.getIdUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CheckUserPasswordDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.CreateUserDTO;
import com.gomistar.proyecto_gomistar.DTO.request.user.ViewUserDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.ViewAlertDTO;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.alert.AlertService;
import com.gomistar.proyecto_gomistar.service.document.DocumentUserService;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    private final ShipService shipService;

    private final DocumentUserService documentUserService;

    private final AlertService alertService;

    public UserController(UserService pUserService, ShipService pShipService, DocumentUserService pDocumentUserService,AlertService pAlertService) {
        this.userService = pUserService;
        this.shipService = pShipService;
        this.documentUserService = pDocumentUserService;
        this.alertService = pAlertService;
    }

    @GetMapping("/check_username")
    public ResponseEntity<?> checkUsername(@RequestParam String pUsername) {

        Boolean result = this.userService.checkUsername(pUsername);
        ApiResponse<Boolean> response = new ApiResponse<>(
        "Exito!",
        result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/check_email")
    public ResponseEntity<?> checkEmail(@RequestParam String pEmail) {

        Boolean result = this.userService.checkEmail(pEmail);
        ApiResponse<Boolean> response = new ApiResponse<>(
        "Exito!",
        result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @GetMapping("/find_All_Without_Ship")
    public ResponseEntity<?> getAllUsersWithoutShip() {

        List<UserEntity> myUsers = this.userService.findUsersWithoutShip();
        ApiResponse<List<UserEntity>> response = new ApiResponse<>(
            "Lista de usuarios sin embarcación asignada",
            myUsers
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find_by_ship")
    public ResponseEntity<?> getByShip(@RequestParam String pId) {

        List<UserEntity> myUsers = this.shipService.getUsersByShip(pId);
        ApiResponse<List<UserEntity>> response = new ApiResponse<>(
            "Lista de usuarios",
            myUsers
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getId")
    public ResponseEntity<?> getId(@RequestParam String username) {

        getIdUserDTO myUsername = this.userService.getId(username);
        ApiResponse<getIdUserDTO> response = new ApiResponse<>(
            "Id encontrada!",
            myUsername
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

    @GetMapping("/list_users")
    public ResponseEntity<?> getUsers() {

        List<ViewUserDTO> myList = this.userService.listAllUser();
        ApiResponse<List<ViewUserDTO>> response = new ApiResponse<>(
        "Lista encontrada!",
        myList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/amount_documents")
    public ResponseEntity<?> getAmountDocuments(@RequestParam String pId) {

        String[] result = this.documentUserService.getDocuments(pId);
        ApiResponse<String[]> response = new ApiResponse<>("Documentos disponibles",
        result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/amount_alerts")
    public ResponseEntity<?> getAmountAlerts(@RequestParam String pId) {

        Integer myAmountAlerts = this.userService.getAmountAlerts(pId);
        ApiResponse<Integer> response = new ApiResponse<>(
        "Cantidad de alertas", 
        myAmountAlerts
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/alerts_admin")
    public ResponseEntity<?> getAlerts(@RequestParam String pIdUser) {

        List<ViewAlertDTO> myList = this.alertService.getAlertsAdmin(pIdUser);
        ApiResponse<List<ViewAlertDTO>> response = new ApiResponse<>(
        "Lista de alertas",
        myList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<?> checkUserPassword(@RequestBody CheckUserPasswordDTO pDTO) {

        boolean myUserExist = this.userService.checkPassword(pDTO);
        ApiResponse<Boolean> response = new ApiResponse<>(
            "La contraseñas coinciden!",
            myUserExist
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

   // @PostMapping("/checkUsername")
   // public ResponseEntity<?> checkUserUsername(@RequestBody CheckUserUsernameDTO pDTO) {
        
       // boolean myUsernameExist = this.userService.checkUsername(pDTO);
       // ApiResponse<Boolean> response = new ApiResponse<>(
       //     "Username disponible!",
        //    myUsernameExist
        //);

       // return ResponseEntity.status(HttpStatus.OK).body(response);
    //}

    @PostMapping("/add_document")
    public ResponseEntity<?> addDocument(@RequestParam String pIdUser, @RequestParam MultipartFile pFile, @RequestParam LocalDate pDate, @RequestParam String pNumber) throws NumberFormatException, IOException {
        
        this.documentUserService.addDocument(pIdUser, pFile, pDate, Byte.parseByte(pNumber));
        ApiResponse<AbstractDocument> response = new ApiResponse<>("Documento añadido con exito!",
        null
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


    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles(@RequestParam String pIdUser) {

        Set<RoleEntity> myRoles = this.userService.getRoles(pIdUser);
        ApiResponse<Set<RoleEntity>> response = new ApiResponse<>(
            "Roles obtenidos!",
            myRoles
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO pDTO) {

        UserEntity myUser = this.userService.createUser(pDTO);
        ApiResponse<UserEntity> response = new ApiResponse<>(
        "Usuario creado!",
        myUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
