package com.gomistar.proyecto_gomistar.service.alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.alert.AbstractAlert;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.repository.alert.ShipAlertRepository;

@Service
public class ShipAlertService {
    
    private final ShipAlertRepository shipAlertRepository;

    public ShipAlertService(ShipAlertRepository pShipAlertRepository) {
        this.shipAlertRepository = pShipAlertRepository;
    }

    public List<ShipAlertEntity> listAll() {

        List<ShipAlertEntity> result = new ArrayList<>();
        List<AbstractAlert> alertList = (List<AbstractAlert>) this.shipAlertRepository.findAll();

        for(AbstractAlert alert : alertList) {

            if(alert instanceof ShipAlertEntity) {
                result.add((ShipAlertEntity) alert);
            }
        }

        return result;
    }

    public ShipAlertEntity getAlert(String pId) {

        Optional<AbstractAlert> myAlertOptional = this.shipAlertRepository.findById(Long.parseLong(pId));

        if(!myAlertOptional.isPresent()) {
            throw new RequestException("p-209", "No existe la alerta buscada");
        }

        return (ShipAlertEntity) myAlertOptional.get();
    }

    public ShipAlertEntity createShipAlert(LocalDate pDate, AbstractShip pShip) {

        ShipAlertEntity myAlert = ShipAlertEntity.builder().build();
        myAlert.setDate(pDate);
        myAlert.setShip(pShip);

        return this.shipAlertRepository.save(myAlert);
    }

    public ShipAlertEntity modifyShipAlert(String pIdAlert, LocalDate pDate, AbstractShip pShip) {

        ShipAlertEntity myAlert = this.getAlert(pIdAlert);
        myAlert.setDate(pDate);
        myAlert.setShip(pShip);

        return this.shipAlertRepository.save(myAlert);
    }

    public void deleteShipAlert(String pId) {

        ShipAlertEntity myAlert = this.getAlert(pId);

        this.shipAlertRepository.delete(myAlert);
    }
}
