package com.gomistar.proyecto_gomistar.service.ship;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.CreateShipCargoDTO;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;
import com.gomistar.proyecto_gomistar.repository.ship.CargoShipRepository;

@Service
public class CargoShipService {
    
    private final CargoShipRepository cargoShipRepository;

    private final LoadTypeService loadTypeService;

    public CargoShipService(CargoShipRepository pCargoShipRepository, LoadTypeService pLoadTypeService) {
        this.cargoShipRepository = pCargoShipRepository;
        this.loadTypeService = pLoadTypeService;
    }

    public CargoShipEntity saveCargoShip(CreateShipCargoDTO pDTO) {

        LoadTypeEntity myLoadType = this.loadTypeService.getLoadType(pDTO.idCargo());
        CargoShipEntity myShip = CargoShipEntity.builder().loadType(myLoadType).build();
        myShip.setName(pDTO.name());
        return this.cargoShipRepository.save(myShip);
    }
}
