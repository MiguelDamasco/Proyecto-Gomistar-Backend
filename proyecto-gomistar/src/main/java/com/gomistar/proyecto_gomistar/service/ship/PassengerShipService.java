package com.gomistar.proyecto_gomistar.service.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.AddEmployeesToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.PassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.PassengerShipEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.ship.PassengerShipRepository;
import com.gomistar.proyecto_gomistar.service.UserService;

@Service
public class PassengerShipService {
    
    private final PassengerShipRepository passengerShipRepository;

    private final UserService userService;

    public PassengerShipService(PassengerShipRepository pPassengerShipRepository, UserService pUserService) {
        this.passengerShipRepository = pPassengerShipRepository;
        this.userService = pUserService;
    }


    public List<AbstractShip> listPassengerShip() {
        
        return (List<AbstractShip>) this.passengerShipRepository.findAll();
    }


    public List<PassengerShipEntity> listAllPassengerShip() {
        
        List<PassengerShipEntity> resultList = new ArrayList<>();
        List<AbstractShip> myList = (List<AbstractShip>)this.passengerShipRepository.findAll();

        for(AbstractShip ship : myList) {

            if(ship instanceof PassengerShipEntity) {

                resultList.add((PassengerShipEntity) ship);
            }
        }

        return resultList;
    }

    public AbstractShip getPassenger(String pId) {

        Optional<AbstractShip> myShipOtional = this.passengerShipRepository.findById(Long.parseLong(pId));

        if(!myShipOtional.isPresent()) {

            throw new RequestException("P-207", "No se encontro la embarcación de pasajeros");
        }

        return myShipOtional.get();
    }

    public void save(PassengerShipEntity pShip) {
        this.passengerShipRepository.save(pShip);
    }

    public PassengerShipEntity savePassengerShip(PassengerShipDTO pDTO) {

        PassengerShipEntity myShip = PassengerShipEntity.builder().build();
        myShip.setName(pDTO.name());
        return this.passengerShipRepository.save(myShip);
    }

    public PassengerShipEntity savePassengerShip(PassengerShipEntity pShip) {

        return this.passengerShipRepository.save(pShip);
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

    public void deletePassengerShip(PassengerShipEntity pShip) {

        this.passengerShipRepository.delete(pShip);
    }

    public List<UserEmployeeResponseDTO> getUsers(String pId) {

        PassengerShipEntity myShip = (PassengerShipEntity) this.getPassenger(pId);
        List<UserEntity> usersList = myShip.getUserList();
        List<UserEmployeeResponseDTO> listDTO = new ArrayList<>();

        for(UserEntity user : usersList) {

            UserEmployeeResponseDTO myDTO = new UserEmployeeResponseDTO(String.valueOf(user.getId()), user.getUsername(), user.getPassword(), user.getEmail(), "prueba", "prueba");
            listDTO.add(myDTO);
        }

        return listDTO;
    }

    public void removeUserFromPassengerShip(String pIdUser, String pIdShip) {

        UserEntity myUser = this.userService.getUser(pIdUser);
        PassengerShipEntity myShip = (PassengerShipEntity) this.getPassenger(pIdShip);
        myShip.removeUser(myUser);
        this.savePassengerShip(myShip);
    }

    public void addEmployeesToPassenger(AddEmployeesToShipDTO pDTO) {
    
        PassengerShipEntity myShip = (PassengerShipEntity) this.getPassenger(pDTO.id());
        List<String> myUsers = pDTO.usersList();

        for(String s : myUsers) {

            UserEntity myUser = this.userService.getUser(s);
            myShip.addUser(myUser);
        }

        this.savePassengerShip(myShip);
    }

    
}
