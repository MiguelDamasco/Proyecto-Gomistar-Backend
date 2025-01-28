package com.gomistar.proyecto_gomistar.service.scheduling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;
import com.gomistar.proyecto_gomistar.model.alert.DocumentAlertEntity;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.role.ERole;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.alert.AlertService;
import com.gomistar.proyecto_gomistar.service.email.EmailService;
import com.gomistar.proyecto_gomistar.service.ship.document.AuxiliarClass;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;

import jakarta.mail.MessagingException;

@Component
public class SchedulingService {

    private final AlertService alertService;

    private final EmailService emailService;

    private final UserService userService;

    private final DocumentShipService documentShipService;

    private final AuxiliarClass auxiliarClass;

    public SchedulingService(AlertService pAlertService, EmailService pEmailService, UserService pUserService, DocumentShipService pDocumentShipService, AuxiliarClass pAuxiliarClass) {
        this.alertService = pAlertService;
        this.emailService = pEmailService;
        this.userService = pUserService;
        this.documentShipService = pDocumentShipService;
        this.auxiliarClass = pAuxiliarClass;
    }

    public void resetAmountAlerts() {

        List<UserEntity> myUsers = this.userService.getAllUser();

        for(UserEntity user : myUsers) {
            user.setAmountAlerts(0);
            this.userService.save(user);
        }
    }

    private void checkUserAlert() throws MessagingException {

        LocalDate currentDateZone = LocalDate.now(ZoneId.of("America/Montevideo"));

        List<DocumentAlertEntity> myUserAlerts = this.alertService.listAllUserAlerts();

        for(DocumentAlertEntity alert : myUserAlerts) {

            LocalDate baseDateMinus10 = alert.getDate().minusDays(10);

            LocalDate baseDateMinus1 = alert.getDate().minusDays(1);

            if(currentDateZone.isEqual(baseDateMinus10)) {
                sendAlert10DaysUser(this.getTypeUser(alert.getType()), alert.getDate(), alert.getUser());
            }
            else if(currentDateZone.isEqual(baseDateMinus1)) {
                sendAlert1DaysUser(this.getTypeUser(alert.getType()), alert.getDate(), alert.getUser());
            }

        }
    }


    private void checkShipAlerts() throws MessagingException {

        LocalDate currentDateZone = LocalDate.now(ZoneId.of("America/Montevideo"));
        
        List<ShipAlertEntity> myShipAlerts = this.alertService.listAllShipAlerts();

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
        else if(pType == 6) {
            return "Minimo equipo de seguridad";
        }

        return null;
    }

    private String getTypeUser(Byte pType) {

        if(pType == 1) {
            return "Carnet de salud";
        }
        else if(pType == 2) {
            return "Título habilitante";
        }
        else if(pType == 3) {
            return "Certificado vacunación tétanos";
        }
        else if(pType == 4) {
            return "Cedula identidad";
        }

        return null;
    }

    private void sendAlert10DaysUser(String pType, LocalDate pDate, UserEntity pUser) throws MessagingException {

        String subject = "Vencimiento de documento en 10 días";

        String message = "Su documento " + pType + " vencerá dentro de 10 días, en la fecha " + this.auxiliarClass.getDate(pDate);

        EmailDTO myDTO = new EmailDTO(pUser.getEmail(), subject, message);
        int myAmountAlerts = pUser.getAmountAlerts() + 1;
        pUser.setAmountAlerts(myAmountAlerts);
        this.userService.save(pUser);
        this.emailService.sendMail(myDTO);

    }


    private void sendAlert1DaysUser(String pType, LocalDate pDate, UserEntity pUser) throws MessagingException {

        String subject = "Vencimiento de documento mañana";

        String message = "Su documento " + pType + " vencerá mañana " + this.auxiliarClass.getDate(pDate);

        EmailDTO myDTO = new EmailDTO(pUser.getEmail(), subject, message);
        int myAmountAlerts = pUser.getAmountAlerts() + 1;
        pUser.setAmountAlerts(myAmountAlerts);
        this.userService.save(pUser);
        this.emailService.sendMail(myDTO);

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
            int myAmountAlerts = user.getAmountAlerts() + 1;
            user.setAmountAlerts(myAmountAlerts);
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
            int myAmountAlerts = user.getAmountAlerts() + 1;
            user.setAmountAlerts(myAmountAlerts);
            this.userService.save(user);
            this.emailService.sendMail(myDTO);
        }
    }

    @Scheduled(fixedRate = 300000)
    public void prueba() throws MessagingException {
        System.out.println("Se ejecutó!");
        resetAmountAlerts();
        checkShipAlerts();
        checkUserAlert();
    }
}
