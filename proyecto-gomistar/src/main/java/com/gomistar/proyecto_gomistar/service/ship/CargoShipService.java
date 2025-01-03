package com.gomistar.proyecto_gomistar.service.ship;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.AddUserToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.CreateShipCargoDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.ModifyCargoShipDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.ship.CargoShipRepository;
import com.gomistar.proyecto_gomistar.service.UserService;

@Service
public class CargoShipService {
    
    private final CargoShipRepository cargoShipRepository;

    private final LoadTypeService loadTypeService;

    private final UserService userService;

    public CargoShipService(CargoShipRepository pCargoShipRepository, LoadTypeService pLoadTypeService, UserService pUserService) {
        this.cargoShipRepository = pCargoShipRepository;
        this.loadTypeService = pLoadTypeService;
        this.userService = pUserService;
    }

    public List<AbstractShip> listAll() {

        return (List<AbstractShip>) this.cargoShipRepository.findAll();
    }

    public CargoShipEntity getCargoShip(String pId) {

        Optional<AbstractShip> myCargoShipOptional = this.cargoShipRepository.findById(Long.parseLong(pId));

        if(!myCargoShipOptional.isPresent()) {
            throw new RequestException("P-209", "Barco de carga no encontrado");
        }

        return (CargoShipEntity) myCargoShipOptional.get();
    }

    public CargoShipEntity saveCargoShip(CreateShipCargoDTO pDTO) {

        LoadTypeEntity myLoadType = this.loadTypeService.getLoadType(pDTO.idCargo());
        CargoShipEntity myShip = CargoShipEntity.builder().loadType(myLoadType).build();
        myShip.setName(pDTO.name());
        return this.cargoShipRepository.save(myShip);
    }

    public CargoShipEntity saveCargoShip(CargoShipEntity pShip) {

        return this.cargoShipRepository.save(pShip);
    }

    public CargoShipEntity addUser(AddUserToShipDTO pDTO) {
        
        UserEntity myUser = this.userService.getUser(pDTO.idUser());
        CargoShipEntity myShip = this.getCargoShip(pDTO.idShip());
        myShip.addUser(myUser);
        return this.cargoShipRepository.save(myShip);
    }

    public CargoShipEntity modifyCargoShip(ModifyCargoShipDTO pDTO) {
         
        CargoShipEntity myShip = this.getCargoShip(pDTO.id());
        LoadTypeEntity myLoadType = this.loadTypeService.getLoadType(pDTO.idLoad());
        myShip.setName(pDTO.name());
        myShip.setLoadType(myLoadType);

        return this.cargoShipRepository.save(myShip);
    }

    public void deleteShip(String pId) {

        CargoShipEntity myShip = this.getCargoShip(pId);
        this.cargoShipRepository.delete(myShip);
    }

    public void deleteShip(CargoShipEntity pCargoShip) {

        this.cargoShipRepository.delete(pCargoShip);
    }
}
