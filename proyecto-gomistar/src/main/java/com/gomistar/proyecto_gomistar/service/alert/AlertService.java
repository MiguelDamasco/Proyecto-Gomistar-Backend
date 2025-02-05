package com.gomistar.proyecto_gomistar.service.alert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.response.ViewAlertDTO;
import com.gomistar.proyecto_gomistar.model.alert.AbstractAlert;
import com.gomistar.proyecto_gomistar.model.alert.DocumentAlertEntity;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserService;

@Service
public class AlertService {
    
    private final ShipAlertService shipAlertService;

    private final UserAlertService userAlertService;

    private final UserService userService;

    public AlertService(ShipAlertService pShipAlertService, UserAlertService pUserAlertService, UserService pUserService) {
        this.shipAlertService = pShipAlertService;
        this.userAlertService = pUserAlertService;
        this.userService = pUserService;
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

    public ShipAlertEntity modifyShipAlert(ShipAlertEntity pAlert, LocalDate pDate) {

        return this.shipAlertService.modifyShipAlert(pAlert, pDate);
    }

    public DocumentAlertEntity modifyUserAlert(DocumentAlertEntity pAlert, LocalDate pDate) {

        return this.userAlertService.modifyAlert(pAlert, pDate);
    }

    public void deleteAlert(String pIdAlert) {

        AbstractAlert myAlert = this.userAlertService.getAbstractAlert(pIdAlert);

        if(myAlert instanceof ShipAlertEntity) {
            this.deleteShipAlert((ShipAlertEntity) myAlert);
        }
        else if(myAlert instanceof DocumentAlertEntity) {
            this.deleteUserAlert((DocumentAlertEntity) myAlert);
        }
    }

    public void modifyAlert(String pIdAlert, LocalDate pDate) {

        AbstractAlert myAlert = this.userAlertService.getAbstractAlert(pIdAlert);

        if(myAlert instanceof ShipAlertEntity) {
            this.modifyShipAlert((ShipAlertEntity) myAlert, pDate);
        }
        else if(myAlert instanceof DocumentAlertEntity) {
            this.modifyUserAlert((DocumentAlertEntity) myAlert, pDate);
        }

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

    public String getFormatDate(LocalDate pDate) {

        int day = pDate.getDayOfMonth();
        int month = pDate.getMonthValue();
        int year = pDate.getYear();

        return (day < 10 ? "0" + day : day) + "/" + (month < 10 ? "0" + month : month) + "/" + year;
    }

    public String daysUntilExpiration(LocalDate pDate) {

        LocalDate currentDateZone = LocalDate.now(ZoneId.of("America/Montevideo"));

        long days = ChronoUnit.DAYS.between(currentDateZone, pDate);

        if(days > 0)
        {
            return days + (days > 1L ? " Días" : " Día");
        }
        else {
            return "Expirado!";
        }    
    }

    public List<ViewAlertDTO> getAlertsAdmin(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);
        StringBuilder myType = new StringBuilder();
        List<ViewAlertDTO> resultList = new ArrayList<>();
        List<ShipAlertEntity> shipAlertList = this.shipAlertService.listAll();

        for(AbstractAlert alert : new ArrayList<>(myUser.getAlertList())) {

                DocumentAlertEntity myDocumentAlert = (DocumentAlertEntity) alert;

                if(myDocumentAlert.getType() == 1) {
                    myType.append("Carnet de salud");
                }
                else if(myDocumentAlert.getType() == 2) {
                    myType.append("Título habilitante");
                }
                else if(myDocumentAlert.getType() == 3) {
                    myType.append("Certificado vacunación tétanos");
                }
                else if(myDocumentAlert.getType() == 4) {
                    myType.append("Cedula identidad");
                }

                ViewAlertDTO myDTO = new ViewAlertDTO(String.valueOf(alert.getId()), myType.toString(), this.getFormatDate(alert.getDate()), this.daysUntilExpiration(alert.getDate()));
                resultList.add(myDTO);
                myType.setLength(0);
        
        }
           for (ShipAlertEntity alert : shipAlertList) {

                if(alert.getType() == 1) {
                    myType.append("Registro de Embarcació");
                }
                else if(alert.getType() == 2) {
                    myType.append("Certificado de Navegabilidad");
                }
                else if(alert.getType() == 3) {
                    myType.append("Inspección Técnica");
                }
                else if(alert.getType() == 4) {
                    myType.append("Seguro Obligatorio");
                }
                else if(alert.getType() == 5) {
                    myType.append("Radio Comunicación");
                }
                else if(alert.getType() == 6) {
                    myType.append("Minimo equipo de seguridad");
                }

                ViewAlertDTO myDTO = new ViewAlertDTO(String.valueOf(alert.getId()), myType.toString() + " (" + alert.getShip().getName() + ")", this.getFormatDate(alert.getDate()), this.daysUntilExpiration(alert.getDate()));
                resultList.add(myDTO);

            myType.setLength(0);

        }

        return resultList;
    }

    public List<ViewAlertDTO> getAlertsUser(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);
        StringBuilder myType = new StringBuilder();
        List<ViewAlertDTO> resultList = new ArrayList<>();

        for(AbstractAlert alert : new ArrayList<>(myUser.getAlertList())) {

                DocumentAlertEntity myDocumentAlert = (DocumentAlertEntity) alert;

                if(myDocumentAlert.getType() == 1) {
                    myType.append("Carnet de salud");
                }
                else if(myDocumentAlert.getType() == 2) {
                    myType.append("Título habilitante");
                }
                else if(myDocumentAlert.getType() == 3) {
                    myType.append("Certificado vacunación tétanos");
                }
                else if(myDocumentAlert.getType() == 4) {
                    myType.append("Cedula identidad");
                }

                ViewAlertDTO myDTO = new ViewAlertDTO(String.valueOf(alert.getId()), myType.toString(), this.getFormatDate(alert.getDate()), this.daysUntilExpiration(alert.getDate()));
                resultList.add(myDTO);
                myType.setLength(0);
        }

        return resultList;
    }
}
