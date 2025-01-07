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
import com.gomistar.proyecto_gomistar.model.ship.document.TechnicalInspectionEntity;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@Service
public class DocumentShipService {
    
    private final BoatRegistrationService boatRegistrationService;

    private final CertificateNavigabilityService certificateNavigabilityService;

    private final TechnicalInspectionService technicalInspectionService;

    private final ShipService shipService;

    public DocumentShipService(BoatRegistrationService pBoatRegistrationService, ShipService pShipService, CertificateNavigabilityService pCertificateNavigabilityService, TechnicalInspectionService pTechnicalInspectionService) {
        this.boatRegistrationService = pBoatRegistrationService;
        this.shipService = pShipService;
        this.certificateNavigabilityService = pCertificateNavigabilityService;
        this.technicalInspectionService = pTechnicalInspectionService;
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
        if(pDocumentNumber == 2) {

            if(!existsCertificateNavigability(myShip)) {
                CertificateNavigabilityEntity myDocument = this.certificateNavigabilityService.createCertificateNavigability(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
        if(pDocumentNumber == 3) {

            if(!existsTechnicalInspection(myShip)) {
                TechnicalInspectionEntity myDocument = this.technicalInspectionService.createTechnicalInspection(pFile, pExpirationDate);
                myShip.addDocument(myDocument);
                this.shipService.saveShip(myShip);
            }
            else {
                throw new RequestException("P-202", "Ya existe el documento!");
            }
        }
    }
}
