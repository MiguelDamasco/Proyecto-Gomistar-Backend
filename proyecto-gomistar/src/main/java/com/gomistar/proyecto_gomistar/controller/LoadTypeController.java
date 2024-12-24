package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.ship.loadType.CreateLoadTypeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.loadType.ModifyLoadTypeDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;
import com.gomistar.proyecto_gomistar.service.ship.LoadTypeService;

@RestController
@RequestMapping("/loadType")
public class LoadTypeController {

    private final LoadTypeService loadTypeService;

    public LoadTypeController(LoadTypeService pLoadTypeService) {
        this.loadTypeService = pLoadTypeService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getLoadType(@RequestParam String pId) {
        
        LoadTypeEntity myLoadType = this.loadTypeService.getLoadType(pId);
        ApiResponse<LoadTypeEntity> response = new ApiResponse<LoadTypeEntity>(
            "Tipo de carga encontrado!",
            myLoadType
             );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAllLoadType() {

        List<LoadTypeEntity> myList = this.loadTypeService.getAllLoadType();
        ApiResponse<List<LoadTypeEntity>> response = new ApiResponse<>(
            "Lista de todos los tipos de carga", 
            myList
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoadType(@RequestBody CreateLoadTypeDTO pDTO) {

        LoadTypeEntity myLoadType = this.loadTypeService.saveLoadType(pDTO);
        ApiResponse<LoadTypeEntity> response = new ApiResponse<>(
            "Tipo de carga creada!",
            myLoadType
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyLoadType(@RequestBody ModifyLoadTypeDTO pDTO) {

        LoadTypeEntity myLoadType = this.loadTypeService.modifyLoadType(pDTO);
        ApiResponse<LoadTypeEntity> response = new ApiResponse<LoadTypeEntity>(
        "Tipo de carga modificado con exito!", 
        myLoadType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLoadType(@RequestParam String pId) {
        
        this.loadTypeService.removeLoadType(pId);
        ApiResponse<LoadTypeEntity> response = new ApiResponse<LoadTypeEntity>(
            "Tipo de carga eliminada con exito!",
             null
             );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
