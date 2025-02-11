package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.ShipDocumentResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.ship.document.BoatRegistrationEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.TechnicalInspectionEntity;
import com.gomistar.proyecto_gomistar.service.alert.ShipAlertService;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@Service
public class DocumentShipService {
    
    private final BoatRegistrationService boatRegistrationService;

    private final CertificateNavigabilityService certificateNavigabilityService;

    private final TechnicalInspectionService technicalInspectionService;

    private final MandatoryInsuranceService mandatoryInsuranceService;

    private final RadioCommunicationsService radioCommunicationsService;

    private final MinimumSecurityEquipmentService minimumSecurityEquipmentService;

    private final ShipService shipService;

    private final ShipAlertService shipAlertService;

    private final AuxiliarClass auxiliarClass;

    public DocumentShipService(BoatRegistrationService pBoatRegistrationService, ShipService pShipService, CertificateNavigabilityService pCertificateNavigabilityService, TechnicalInspectionService pTechnicalInspectionService, MandatoryInsuranceService pMandatoryInsuranceService, RadioCommunicationsService pRadioCommunicationsService, MinimumSecurityEquipmentService pMinimumSecurityEquipmentService, ShipAlertService pShipAlertService, AuxiliarClass pAuxiliarClass) {
        this.boatRegistrationService = pBoatRegistrationService;
        this.shipService = pShipService;
        this.certificateNavigabilityService = pCertificateNavigabilityService;
        this.technicalInspectionService = pTechnicalInspectionService;
        this.mandatoryInsuranceService = pMandatoryInsuranceService;
        this.radioCommunicationsService = pRadioCommunicationsService;
        this.minimumSecurityEquipmentService = pMinimumSecurityEquipmentService;
        this.shipAlertService = pShipAlertService;
        this.auxiliarClass = pAuxiliarClass;
    }

    public boolean existsBoatRegistration(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof BoatRegistrationEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsCertificateNavigability(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof CertificateNavigabilityEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsTechnicalInspection(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof TechnicalInspectionEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsMandatoryInsurance(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof MandatoryInsuranceEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsRadioCommunications(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof RadioCommunicationsEntity) {
                return true;
            }
        }

        return false;
    }

    public boolean existsMinimumSecurityEquipment(AbstractShip pShip) {

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof MinimumSecurityEquipmentEntity) {
                return true;
            }
        }

        return false;
    }

    public String[] getAmountDocuments(String pIdShip) {

        String[] result = new String[6];
        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof BoatRegistrationEntity) {
                result[0] = "1";
                continue;
            }
            else if(document instanceof CertificateNavigabilityEntity) {
                result[1] = "1";
                continue;
            }
            else if(document instanceof TechnicalInspectionEntity) {
                result[2] = "1";
                continue;
            }
            else if(document instanceof MandatoryInsuranceEntity) {
                result[3] = "1";
                continue;
            }
            else if(document instanceof RadioCommunicationsEntity) {
                result[4] = "1";
                continue;
            }
            else if(document instanceof MinimumSecurityEquipmentEntity) {
                result[5] = "1";
            }
        }

        return result;
    }



    public void addDocument(String pIdShip,  MultipartFile pFile, LocalDate pExpirationDate, Byte pDocumentNumber) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        if(pDocumentNumber == 1) {

            if(!existsBoatRegistration(myShip)) {
               BoatRegistrationEntity myDocument = this.boatRegistrationService.createBoatRegistration(pFile, pExpirationDate);
               myShip.addDocument(myDocument);
               this.shipService.saveShip(myShip);
               this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("1"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 2) {

            if(!existsCertificateNavigability(myShip)) {
                CertificateNavigabilityEntity myDocument = this.certificateNavigabilityService.createCertificateNavigability(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
                this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("2"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 3) {

            if(!existsTechnicalInspection(myShip)) {
                TechnicalInspectionEntity myDocument = this.technicalInspectionService.createTechnicalInspection(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
                this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("3"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 4) {

            if(!existsMandatoryInsurance(myShip)) {
                MandatoryInsuranceEntity myDocument = this.mandatoryInsuranceService.createMandatoryInsurance(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
                this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("4"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 5) {

            if(!existsRadioCommunications(myShip)) {
                RadioCommunicationsEntity myDocument = this.radioCommunicationsService.createRadioCommunications(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
                this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("5"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        else if(pDocumentNumber == 6) {

            if(!existsMinimumSecurityEquipment(myShip)) {
                MinimumSecurityEquipmentEntity myDocument = this.minimumSecurityEquipmentService.createMinimumSecurityEquipment(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
                this.shipAlertService.createShipAlert(pExpirationDate, myShip, Byte.valueOf("6"));
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
    }


    public ShipDocumentResponseDTO getBoatRegistration(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof BoatRegistrationEntity) {

                BoatRegistrationEntity myBoatRegistration = (BoatRegistrationEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public ShipDocumentResponseDTO getCertificateNavigability(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof CertificateNavigabilityEntity) {

                CertificateNavigabilityEntity myBoatRegistration = (CertificateNavigabilityEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public ShipDocumentResponseDTO getTechnicalInspection(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof TechnicalInspectionEntity) {

                TechnicalInspectionEntity myBoatRegistration = (TechnicalInspectionEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public ShipDocumentResponseDTO getMandatoryInsurance(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof MandatoryInsuranceEntity) {

                MandatoryInsuranceEntity myBoatRegistration = (MandatoryInsuranceEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public ShipDocumentResponseDTO getRadioCommunications(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof RadioCommunicationsEntity) {

                RadioCommunicationsEntity myBoatRegistration = (RadioCommunicationsEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public ShipDocumentResponseDTO getMinimumSecurityEquipment(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        for(AbstractDocumentShip document : new ArrayList<>(myShip.getDocumentList())) {

            if(document instanceof MinimumSecurityEquipmentEntity) {

                MinimumSecurityEquipmentEntity myBoatRegistration = (MinimumSecurityEquipmentEntity) document;
                ShipDocumentResponseDTO myDTO = new ShipDocumentResponseDTO(myBoatRegistration.getImage(), this.auxiliarClass.getDate(myBoatRegistration.getExpirationDate()));
                return myDTO;
            }
        }

        return null;
    }

    public void deleteBoatRegistration(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        BoatRegistrationEntity myDocument = this.boatRegistrationService.deleteBoatRegistration(myShip);

        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsBoatRegistration(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public void deleteCertificateNavigability(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        CertificateNavigabilityEntity myDocument = this.certificateNavigabilityService.deleteBoatRegistration(myShip);

        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsCertificateNavigability(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public void deleteTechnicalInspection(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        TechnicalInspectionEntity myDocument = this.technicalInspectionService.deleteBoatRegistration(myShip);

        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsTechnicalInspection(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public void deleteMandatoryInsurance(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        MandatoryInsuranceEntity myDocument = this.mandatoryInsuranceService.deleteBoatRegistration(myShip);

        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsMandatoryInsurance(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public void deleteRadioCommunicationsEntity(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        RadioCommunicationsEntity myDocument = this.radioCommunicationsService.deleteBoatRegistration(myShip);
        
        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsRadioCommunications(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public void deleteMinimumSecurityEquipment(String pIdShip, String pType) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        MinimumSecurityEquipmentEntity myDocument = this.minimumSecurityEquipmentService.deleteBoatRegistration(myShip);
        
        ShipAlertEntity myAlert = this.shipAlertService.getByType(myShip, Byte.parseByte(pType));

        if(existsMinimumSecurityEquipment(myShip)) {

            myShip.removeDocument(myDocument);
            if(myAlert != null) {
                myShip.removeAlert(myAlert);
            }
            this.shipService.saveShip(myShip);
        }

    }

    public String getDownloadBoatRegistration(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.boatRegistrationService.getDownload(myShip);
    }

    public String getDownloadCertificateNavigability(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.certificateNavigabilityService.getDownload(myShip);
    }

    public String getDownloadTechnicalInspection(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.technicalInspectionService.getDownload(myShip);
    }

    public String getDownloadMandatoryInsurance(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.mandatoryInsuranceService.getDownload(myShip);
    }

    public String getDownloadRadioCommunications(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.radioCommunicationsService.getDownload(myShip);
    }

    public String getDownloadMinimumSecurityEquipment(String pIdShip) {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        return this.minimumSecurityEquipmentService.getDownload(myShip);
    }

}
