package com.gomistar.proyecto_gomistar.service.ship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.ship.DeleteShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.IModify;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ListAllShipsDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.ModifyCargoShipDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;
import com.gomistar.proyecto_gomistar.model.ship.PassengerShipEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;

@Service
public class ShipService {
    
    private final PassengerShipService passengerShipService;

    private final CargoShipService cargoShipService;

    private final LoadTypeService loadTypeService;

    public ShipService(PassengerShipService pPassengerShipService, CargoShipService pCargoShipService, LoadTypeService pLoadTypeService) {
        this.passengerShipService = pPassengerShipService;
        this.cargoShipService = pCargoShipService;
        this.loadTypeService = pLoadTypeService;
    }

    //Hay que optimizarlo!!
    public List<ListAllShipsDTO> listAllShips() {

        List<AbstractShip> passengerAbstractList = this.passengerShipService.listAllPassengerShip();
        List<PassengerShipEntity> passengerList = new ArrayList<>();

        List<AbstractShip> cargoAbstractList = this.cargoShipService.listAll();
        List<CargoShipEntity> cargoList = new ArrayList<>();

        List<ListAllShipsDTO> result = new ArrayList<>();

        for(AbstractShip s : passengerAbstractList) {

            if(s instanceof PassengerShipEntity)
            passengerList.add((PassengerShipEntity) s);
        }

        for(AbstractShip s : cargoAbstractList) {

            if(s instanceof CargoShipEntity)
            cargoList.add((CargoShipEntity) s);
        }

        for(PassengerShipEntity s : passengerList) {

            ListAllShipsDTO myDTO = new ListAllShipsDTO(String.valueOf(s.getId()), s.getName(), String.valueOf(s.getUserList().size()), "Pasajeros");
            result.add(myDTO);
        }

        for(CargoShipEntity s : cargoList) {

            ListAllShipsDTO myDTO = new ListAllShipsDTO(String.valueOf(s.getId()), s.getName(), String.valueOf(s.getUserList().size()), "Carga (" + s.getLoadType().getName() + ")");
            result.add(myDTO);
        }

        return result;
    }

    public void deleteShip(DeleteShipDTO pDTO) {

        if(Integer.parseInt(pDTO.type()) == 0) {
            PassengerShipEntity myShip = (PassengerShipEntity) this.passengerShipService.getPassenger(pDTO.id());
            this.passengerShipService.deletePassengerShip(myShip);
        }
        else if(Integer.parseInt(pDTO.type()) == 1) {
            CargoShipEntity myShip = this.cargoShipService.getCargoShip(pDTO.id());
            this.cargoShipService.deleteShip(myShip);
        }
        else {
            throw new RequestException("P-200", "Error al eliminar el barco, tipo no encontrado");
        }
    }


    public AbstractShip modifyShip(IModify pDTO) {


        if(pDTO instanceof ModifyPassengerShipDTO) {

            ModifyPassengerShipDTO myDTO = (ModifyPassengerShipDTO) pDTO;
            // Cargo ship a Passenger Ship
            if(Integer.parseInt(myDTO.transform()) == 1) {
                List<UserEntity> myList = this.passengerShipService.getPassenger(myDTO.id()).getUserList();
                this.cargoShipService.deleteShip(myDTO.id());
                return cargoShipToPassengerShip(myDTO, myList);
            }  
            // Solo modificar el PassengerShip
            else if (Integer.parseInt(myDTO.transform()) == 0) {
                return modifyPassengerShip(myDTO);
            }
        }
        else if(pDTO instanceof ModifyCargoShipDTO) {

            ModifyCargoShipDTO myDTO = (ModifyCargoShipDTO) pDTO;
            //Passanger Ship a Cargo Ship
            if(Integer.parseInt(myDTO.trnasform()) == 1) {
                List<UserEntity> myList = this.passengerShipService.getPassenger(myDTO.id()).getUserList();
                this.passengerShipService.deletePassengerShip(myDTO.id());
                return passengerShipToCargoShip(myDTO, myList);
            }
             //Modifica solo el Cargo Ship
            else if(Integer.parseInt(myDTO.trnasform()) == 0) {
                return modifyCargoShip(myDTO);
            }
        }
        
        return null;
    }

    public PassengerShipEntity modifyPassengerShip(ModifyPassengerShipDTO pDTO) {

        return this.passengerShipService.modiftPassengerShip(pDTO);
    }

    public CargoShipEntity modifyCargoShip(ModifyCargoShipDTO pDTO) {

        return this.cargoShipService.modifyCargoShip(pDTO);
    }


    public PassengerShipEntity cargoShipToPassengerShip(ModifyPassengerShipDTO pDTO, List<UserEntity> userList) {

        PassengerShipEntity myShip = PassengerShipEntity.builder().build();
        myShip.setId(Long.parseLong(pDTO.id()));
        myShip.setName(pDTO.name());
        myShip.setUserList(userList);
        return this.passengerShipService.savePassengerShip(myShip);

    }

    public CargoShipEntity passengerShipToCargoShip(ModifyCargoShipDTO pDTO, List<UserEntity> userList) {

        LoadTypeEntity myLoad = this.loadTypeService.getLoadType(pDTO.idLoad());
        CargoShipEntity myShip = CargoShipEntity.builder().build();
        myShip.setId(Long.parseLong(pDTO.id()));
        myShip.setName(pDTO.name());
        myShip.setLoadType(myLoad);
        myShip.setUserList(userList);

        return this.cargoShipService.saveCargoShip(myShip);
    }
}
