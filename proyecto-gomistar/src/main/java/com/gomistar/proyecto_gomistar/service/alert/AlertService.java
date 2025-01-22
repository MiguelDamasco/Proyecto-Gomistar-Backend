package com.gomistar.proyecto_gomistar.service.alert;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.model.alert.DocumentAlertEntity;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;

@Service
public class AlertService {
    
    private final ShipAlertService shipAlertService;

    private final UserAlertService userAlertService;

    public AlertService(ShipAlertService pShipAlertService, UserAlertService pUserAlertService) {
        this.shipAlertService = pShipAlertService;
        this.userAlertService = pUserAlertService;
    }

    public ShipAlertEntity getByTypeShip(AbstractShip pShip, Byte pType) {

        return this.shipAlertService.getByType(pShip, pType);
    }

    public DocumentAlertEntity getByTypeUser(UserEntity pUser, Byte pType) {

        return this.userAlertService.getByType(pUser, pType);
    }

    public List<ShipAlertEntity> listAllShipAlerts() {

        return this.shipAlertService.listAll();
    }

    public List<DocumentAlertEntity> listAllUserAlerts() {

        return this.userAlertService.listAll();
    }

    public ShipAlertEntity createShipAlert(LocalDate pDate, AbstractShip pShip, Byte pType) {

        return this.shipAlertService.createShipAlert(pDate, pShip, pType);
    }

    public DocumentAlertEntity createAlertUser(LocalDate pDate, UserEntity pUser, Byte pByte) {

        return this.userAlertService.createAlert(pDate, pUser, pByte);
    }

    public ShipAlertEntity modifyShipAlert(String pIdAlert, LocalDate pDate) {

        return this.shipAlertService.modifyShipAlert(pIdAlert, pDate);
    }

    public DocumentAlertEntity modifyUserAlert(String pIdAlert, LocalDate pDate) {

        return this.userAlertService.modifyAlert(pIdAlert, pDate);
    }

    public void deleteShipAlertById(String pIdAlert) {

        this.shipAlertService.deleteShipAlertById(pIdAlert);
    }

    public void deleteAlertById(String pIdAlert) {

        this.userAlertService.deleteAlertById(pIdAlert);
    }

    public void deleteShipAlert(ShipAlertEntity pAlert) {

        this.shipAlertService.deleteShipAlert(pAlert);
    }

    public void deleteUserAlert(DocumentAlertEntity pAlert) {

        this.userAlertService.deleteAlert(pAlert);
    }
}
