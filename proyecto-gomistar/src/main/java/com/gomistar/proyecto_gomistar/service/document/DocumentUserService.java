package com.gomistar.proyecto_gomistar.service.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.user.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.DocumentResponseDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.IdentityCardLectureDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.ViewIdentityCardDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.user.document.HealthCardEntity;
import com.gomistar.proyecto_gomistar.model.user.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.model.user.document.QualifyingTitleEntity;
import com.gomistar.proyecto_gomistar.model.user.document.TetanusVaccineCertificateEntity;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.alert.AlertService;
import com.gomistar.proyecto_gomistar.service.alert.UserAlertService;
import com.gomistar.proyecto_gomistar.service.ship.document.AuxiliarClass;

@Service
public class DocumentUserService {
    
    private final HealthCardService healthCardService;

    private final IdentityCardService identityCardService;

    private final QualifyingTitleService qualifyingTitleService;

    private final TetanusVaccineCertificateService tetanusVaccineCertificateService;

    private final UserService userService;

    private final AuxiliarClass auxiliarClass;

    private final AlertService alertService;

    public DocumentUserService(HealthCardService pHealthCardService, IdentityCardService pIdentityCardService, UserService pUserService, QualifyingTitleService pQualifyingService, TetanusVaccineCertificateService pTetanusVaccineCertificateService, AuxiliarClass pAuxiliarClass, AlertService pAlertService) {
        this.identityCardService = pIdentityCardService;
        this.healthCardService = pHealthCardService;
        this.qualifyingTitleService = pQualifyingService;
        this.userService = pUserService;
        this.tetanusVaccineCertificateService = pTetanusVaccineCertificateService;
        this.auxiliarClass = pAuxiliarClass;
        this.alertService = pAlertService;
    }

    public boolean existsHealthCard(UserEntity pUser) {

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof HealthCardEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsIdentityCard(UserEntity pUser) {

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof IdentityCardDocument) {
                return true;
            }
        }

        return false;
    }


    public boolean existsQualifyingTtile(UserEntity pUser) {

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof QualifyingTitleEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsTetanusVaccineCertificate(UserEntity pUser) {

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof TetanusVaccineCertificateEntity) {
                return true;
            }
        }

        return false;
    }

    public String[] getDocuments(String pIdUser) {

        String[] result = new String[4];
        UserEntity myUser = this.userService.getUser(pIdUser);

        for(AbstractDocument document : new ArrayList<>(myUser.getDocuments())) {

            if(document instanceof HealthCardEntity) {
                result[0] = "1";
            }
            else if(document instanceof QualifyingTitleEntity) {
                result[1] = "1";
            }
            else if(document instanceof TetanusVaccineCertificateEntity) {
                result[2] = "1";
            }
            else if(document instanceof IdentityCardDocument) {
                result[3] = "1";
            }
        }

        return result;
    }

    public String getImageIdentityCard(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        if(existsIdentityCard(myUser)) {

            for(AbstractDocument document : new ArrayList<>(myUser.getDocuments())) {

                if(document instanceof IdentityCardDocument) {
                    IdentityCardDocument myDocument = (IdentityCardDocument) document;
                    return myDocument.getImage();
                }
            }
        }

        return null;
    }

    public IdentityCardLectureDTO getLecture(MultipartFile pFile) throws IOException {
        return this.identityCardService.fileLecture(pFile);
    }

    public void addDocument(String pIdUser,  MultipartFile pFile, LocalDate pExpirationDate, Byte pDocumentNumber) throws IOException {

        UserEntity myUser = this.userService.getUser(pIdUser);

        if(pDocumentNumber == 1) {

            if(!existsHealthCard(myUser)) {
               HealthCardEntity myDocument = this.healthCardService.createHealthCard(pFile, pExpirationDate);
               myUser.addDocument(myDocument);
               this.userService.save(myUser);
               //this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("1"));
               this.alertService.createAlertUser(pExpirationDate, myUser, pDocumentNumber);
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 2) {

            if(!existsQualifyingTtile(myUser)) {
               QualifyingTitleEntity myDocument = this.qualifyingTitleService.createHealthCard(pFile, pExpirationDate);
               myUser.addDocument(myDocument);
               this.userService.save(myUser);
               //this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("1"));
               this.alertService.createAlertUser(pExpirationDate, myUser, pDocumentNumber);
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 3) {

            if(!existsTetanusVaccineCertificate(myUser)) {
                TetanusVaccineCertificateEntity myDocument = this.tetanusVaccineCertificateService.createTetanusVaccineCertificate(pFile, pExpirationDate);
               myUser.addDocument(myDocument);
               this.userService.save(myUser);
               //this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("1"));
               this.alertService.createAlertUser(pExpirationDate, myUser, pDocumentNumber);
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        
    }

     public void createIdentityCard(CreateIdentityCardDTO pDTO) throws IOException { 

        UserEntity myUser = this.userService.getUser(pDTO.idUser());

        if(!existsIdentityCard(myUser)) {
            IdentityCardDocument myDocument = this.identityCardService.createIdentityCard(pDTO);
            myUser.addDocument(myDocument);
            this.userService.save(myUser);
            this.alertService.createAlertUser(pDTO.expirationData(), myUser, Byte.parseByte("4"));
        }
        else {
            throw new RequestException("P-202", "Ya existe el documento!");
        }
     }

     public ViewIdentityCardDTO getIdentityCard(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        return this.identityCardService.getIdentityCard(myUser);
     }

    public DocumentResponseDTO getHealthCard(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        for(AbstractDocument document : new ArrayList<>(myUser.getDocuments())) {

            if(document instanceof HealthCardEntity) {

                HealthCardEntity myHealthCard = (HealthCardEntity) document;
                DocumentResponseDTO myResponse = new DocumentResponseDTO(myHealthCard.getImage(), this.auxiliarClass.getDate(myHealthCard.getExpirationDate()));
                return myResponse;
            }
        }

        return null;
    }

    public DocumentResponseDTO getQualifyingTitle(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        for(AbstractDocument document : new ArrayList<>(myUser.getDocuments())) {

            if(document instanceof QualifyingTitleEntity) {

                QualifyingTitleEntity myHealthCard = (QualifyingTitleEntity) document;
                DocumentResponseDTO myResponse = new DocumentResponseDTO(myHealthCard.getImage(), this.auxiliarClass.getDate(myHealthCard.getExpirationDate()));
                return myResponse;
            }
        }

        return null;
    }

    public DocumentResponseDTO getTetanusVaccineCertificate(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        for(AbstractDocument document : new ArrayList<>(myUser.getDocuments())) {

            if(document instanceof TetanusVaccineCertificateEntity) {

                TetanusVaccineCertificateEntity myHealthCard = (TetanusVaccineCertificateEntity) document;
                DocumentResponseDTO myResponse = new DocumentResponseDTO(myHealthCard.getImage(), this.auxiliarClass.getDate(myHealthCard.getExpirationDate()));
                return myResponse;
            }
        }

        return null;
    }


    public String getDownloadHealthCard(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        return this.healthCardService.getDownload(myUser);
    }

    public String getDownloadQualifyingTitle(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        return this.qualifyingTitleService.getDownload(myUser);
    }

    public String getDownloadTetanusVaccineCertificate(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        return this.tetanusVaccineCertificateService.getDownload(myUser);
    }

    public String getDownloadIdentityCard(String pIdUser) {

        UserEntity myUser = this.userService.getUser(pIdUser);

        return this.identityCardService.getDownload(myUser);
    }

    public void deleteHealthCard(String pIdUser, String pType) throws IOException {

        UserEntity myUser = this.userService.getUser(pIdUser);

        HealthCardEntity myDocument = this.healthCardService.deleteBoatRegistration(myUser);
        
        //ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsHealthCard(myUser)) {

            myUser.removeDocument(myDocument);
            //myShip.removeAlert(myAlert);
            this.userService.save(myUser);
        }
    }

    public void deleteQualifyingTitle(String pIdUser, String pType) throws IOException {

        UserEntity myUser = this.userService.getUser(pIdUser);

        QualifyingTitleEntity myDocument = this.qualifyingTitleService.deleteBoatRegistration(myUser);
        
        //ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsQualifyingTtile(myUser)) {

            myUser.removeDocument(myDocument);
            //myShip.removeAlert(myAlert);
            this.userService.save(myUser);
        }

    }

    public void deleteTetanusVaccineCertificate(String pIdUser, String pType) throws IOException {

        UserEntity myUser = this.userService.getUser(pIdUser);

        TetanusVaccineCertificateEntity myDocument = this.tetanusVaccineCertificateService.deleteBoatRegistration(myUser);
        
        //ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsTetanusVaccineCertificate(myUser)) {

            myUser.removeDocument(myDocument);
            //myShip.removeAlert(myAlert);
            this.userService.save(myUser);
        }

    }

    public void deleteIdentityCard(String pIdUser, String pType) throws IOException {

        UserEntity myUser = this.userService.getUser(pIdUser);

        IdentityCardDocument myDocument = this.identityCardService.deleteIdentityCard(myUser);
        
        //ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));



        if(existsIdentityCard(myUser)) {

            myUser.removeDocument(myDocument);
            //myShip.removeAlert(myAlert);
            this.userService.save(myUser);
        }
    }

    public void cancelIdentityCard(String pFile) throws IOException {
        this.identityCardService.cancelIdentityCard(pFile);
    }
}
