package com.gomistar.proyecto_gomistar.service.ship;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.loadType.CreateLoadTypeDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.loadType.ModifyLoadTypeDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;
import com.gomistar.proyecto_gomistar.repository.ship.LoadTypeRepository;

@Service
public class LoadTypeService {
    
    private final LoadTypeRepository loadTypeRepository;

    public LoadTypeService(LoadTypeRepository pLoadTypeRepository) {
        this.loadTypeRepository = pLoadTypeRepository;
    }

    public List<LoadTypeEntity> getAllLoadType() {

        return (List<LoadTypeEntity>) this.loadTypeRepository.findAll();
    }

    public LoadTypeEntity getLoadType(String pId) {

        Optional<LoadTypeEntity> myLoadTypeOptional = this.loadTypeRepository.findById(Long.parseLong(pId));

        if(!myLoadTypeOptional.isPresent()) {
            throw new RequestException("P-208", "Tipo de carga no encontrada");
        }

        return myLoadTypeOptional.get();
    }

    public LoadTypeEntity saveLoadType(CreateLoadTypeDTO pDTO) {

        LoadTypeEntity myLoadType = LoadTypeEntity.builder().name(pDTO.name()).build();
        return this.loadTypeRepository.save(myLoadType);
    }

    public LoadTypeEntity modifyLoadType(ModifyLoadTypeDTO pDTO) {

        LoadTypeEntity myLoadType = this.getLoadType(pDTO.id());
        myLoadType.setName(pDTO.name());
        return this.loadTypeRepository.save(myLoadType);
    }

    public void removeLoadType(String pId) {

        LoadTypeEntity myLoadType = this.getLoadType(pId);
        this.loadTypeRepository.delete(myLoadType);
    }

    public void addShip(String pId, CargoShipEntity pShip) {

        LoadTypeEntity myLoadType = this.getLoadType(pId);
        myLoadType.addShip(pShip);
        this.loadTypeRepository.save(myLoadType);
    }

    public void removeShip(String pId, CargoShipEntity pShip) {

        LoadTypeEntity myLoadType = this.getLoadType(pId);
        myLoadType.removeShip(pShip);
        this.loadTypeRepository.save(myLoadType);
    }

}
