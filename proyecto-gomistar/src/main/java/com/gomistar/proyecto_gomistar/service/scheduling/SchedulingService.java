package com.gomistar.proyecto_gomistar.service.scheduling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.role.ERole;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.alert.ShipAlertService;
import com.gomistar.proyecto_gomistar.service.email.EmailService;
import com.gomistar.proyecto_gomistar.service.ship.document.AuxiliarClass;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;

import jakarta.mail.MessagingException;

@Component
public class SchedulingService {

    private final ShipAlertService shipAlertService;

    private final EmailService emailService;

    private final UserService userService;

    private final DocumentShipService documentShipService;

    private final AuxiliarClass auxiliarClass;

    public SchedulingService(ShipAlertService pShipAlertService, EmailService pEmailService, UserService pUserService, DocumentShipService pDocumentShipService, AuxiliarClass pAuxiliarClass) {
        this.shipAlertService = pShipAlertService;
        this.emailService = pEmailService;
        this.userService = pUserService;
        this.documentShipService = pDocumentShipService;
        this.auxiliarClass = pAuxiliarClass;
    }


    private void checkShipAlerts() throws MessagingException {

        LocalDate currentDateZone = LocalDate.now(ZoneId.of("America/Montevideo"));
        
        List<ShipAlertEntity> myShipAlerts = this.shipAlertService.listAll();

        for(ShipAlertEntity alert : myShipAlerts) {

            LocalDate baseDateMinus10 = alert.getDate().minusDays(10);

            LocalDate baseDateMinus1 = alert.getDate().minusDays(1);

            if(currentDateZone.isEqual(baseDateMinus10)) {
                sendAlert10DaysShip(this.getTypeShip(alert.getType()), alert.getShip().getName(), alert.getDate());
            }
            else if(currentDateZone.isEqual(baseDateMinus1)) {
                sendAlert1DayShip(this.getTypeShip(alert.getType()), alert.getShip().getName(), alert.getDate());
            }
        }

    }

    private String getTypeShip(Byte pType) {

        if(pType == 1) {
            return "Registro de Embarcación";
        }
        else if(pType == 2) {
            return "Certificado de Navegabilidad";
        }
        else if(pType == 3) {
            return "Inspección Técnica";
        }
        else if(pType == 4) {
            return "Seguro Obligatorio";
        }
        else if(pType == 5) {
            return "Radio Comunicación";
        }
        else if(pType == 5) {
            return "Minimo equipo de seguridad";
        }

        return null;
    }

    private void sendAlert10DaysShip(String pType, String pShipName, LocalDate pDate) throws MessagingException {

        String subject = "Vencimiento de documento en 10 días";

        String message = "El documento " + pType + " de la embarcación " + pShipName + " vencerá dentro de 10 días, en la fecha " + this.auxiliarClass.getDate(pDate);

        List<UserEntity> myUsers = this.userService.getAllUser();

        List<UserEntity> myAdminUsers = myUsers.stream()
            .filter(user -> user.getRoles().stream()
            .anyMatch(role -> role.getName() == ERole.ADMIN))
            .collect(Collectors.toList());

        for(UserEntity user : myAdminUsers) {
            EmailDTO myDTO = new EmailDTO(user.getEmail(), subject, message);
            user.setAmountAlerts(user.getAmountAlerts() + 1);
            this.userService.save(user);
            this.emailService.sendMail(myDTO);
        }
    }

    private void sendAlert1DayShip(String pType, String pShipName, LocalDate pDate) throws MessagingException {

        String subject = "Vencimiento de documento mañana";

        String message = "El documento " + pType + " de la embarcación " + pShipName + " vencerá mañana " + this.auxiliarClass.getDate(pDate);

        List<UserEntity> myUsers = this.userService.getAllUser();

        List<UserEntity> myAdminUsers = myUsers.stream()
            .filter(user -> user.getRoles().stream()
            .anyMatch(role -> role.getName() == ERole.ADMIN))
            .collect(Collectors.toList());

        for(UserEntity user : myAdminUsers) {
            EmailDTO myDTO = new EmailDTO(user.getEmail(), subject, message);
            user.setAmountAlerts(user.getAmountAlerts() + 1);
            this.userService.save(user);
            this.emailService.sendMail(myDTO);
        }
    }

    @Scheduled(fixedRate = 300000)
    public void prueba() throws MessagingException {
        System.out.println("Se ejecutó!");
        checkShipAlerts();
    }
}
