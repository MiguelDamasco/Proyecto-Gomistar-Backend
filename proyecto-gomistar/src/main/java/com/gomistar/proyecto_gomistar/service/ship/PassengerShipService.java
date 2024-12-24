package com.gomistar.proyecto_gomistar.service.ship;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.PassengerShipDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.PassengerShipEntity;
import com.gomistar.proyecto_gomistar.repository.ship.PassengerShipRepository;

@Service
public class PassengerShipService {
    
    private final PassengerShipRepository passengerShipRepository;

    public PassengerShipService(PassengerShipRepository pPassengerShipRepository) {
        this.passengerShipRepository = pPassengerShipRepository;
    }

    public List<AbstractShip> listAllPassengerShip() {
        
        return (List<AbstractShip>)this.passengerShipRepository.findAll();
    }

    public AbstractShip getPassenger(String pId) {

        Optional<AbstractShip> myShipOtional = this.passengerShipRepository.findById(Long.parseLong(pId));

        if(!myShipOtional.isPresent()) {

            throw new RequestException("P-207", "No se encontro la embarcación de pasajeros");
        }

        return myShipOtional.get();
    }

    public PassengerShipEntity savePassengerShip(PassengerShipDTO pDTO) {

        PassengerShipEntity myShip = PassengerShipEntity.builder().build();
        myShip.setName(pDTO.name());
        return this.passengerShipRepository.save(myShip);
    }

    public PassengerShipEntity modiftPassengerShip(ModifyPassengerShipDTO pDTO) {

        PassengerShipEntity myShip = (PassengerShipEntity)this.getPassenger(pDTO.id());
        myShip.setName(pDTO.name());
        return this.passengerShipRepository.save(myShip);
    }

    public void deletePassengerShip(String pId) {

        PassengerShipEntity myShip = (PassengerShipEntity)this.getPassenger(pId);
        this.passengerShipRepository.delete(myShip);
    }
}
