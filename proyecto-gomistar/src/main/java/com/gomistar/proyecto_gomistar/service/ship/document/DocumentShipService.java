package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.ship.document.BoatRegistrationEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.TechnicalInspectionEntity;
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

    public DocumentShipService(BoatRegistrationService pBoatRegistrationService, ShipService pShipService, CertificateNavigabilityService pCertificateNavigabilityService, TechnicalInspectionService pTechnicalInspectionService, MandatoryInsuranceService pMandatoryInsuranceService, RadioCommunicationsService pRadioCommunicationsService, MinimumSecurityEquipmentService pMinimumSecurityEquipmentService) {
        this.boatRegistrationService = pBoatRegistrationService;
        this.shipService = pShipService;
        this.certificateNavigabilityService = pCertificateNavigabilityService;
        this.technicalInspectionService = pTechnicalInspectionService;
        this.mandatoryInsuranceService = pMandatoryInsuranceService;
        this.radioCommunicationsService = pRadioCommunicationsService;
        this.minimumSecurityEquipmentService = pMinimumSecurityEquipmentService;
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

    public void addDocument(String pIdShip,  MultipartFile pFile, LocalDate pExpirationDate, Byte pDocumentNumber) throws IOException {

        AbstractShip myShip = this.shipService.getShip(pIdShip);

        if(pDocumentNumber == 1) {

            if(!existsBoatRegistration(myShip)) {
               BoatRegistrationEntity myDocument = this.boatRegistrationService.createBoatRegistration(pFile, pExpirationDate);
               myShip.addDocument(myDocument);
               this.shipService.saveShip(myShip);
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
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
    }
}
