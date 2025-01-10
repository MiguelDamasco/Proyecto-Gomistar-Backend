package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.MinimumSecurityEquipmentRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class MinimumSecurityEquipmentService {
    
    private final MinimumSecurityEquipmentRepository minimumSecurityEquipmentRepository;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public MinimumSecurityEquipmentService(MinimumSecurityEquipmentRepository pMinimumSecurityEquipmentRepository, S3Service pS3Service, AuxiliarClass pAuxiliarClass) {
        this.minimumSecurityEquipmentRepository = pMinimumSecurityEquipmentRepository;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public MinimumSecurityEquipmentEntity createMinimumSecurityEquipment(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {
        
        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        MinimumSecurityEquipmentEntity myDocument = MinimumSecurityEquipmentEntity.builder().image(response.name())
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();

        return this.minimumSecurityEquipmentRepository.save(myDocument);
    }

    public String getDownload(AbstractShip pShip) {

        MinimumSecurityEquipmentEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof MinimumSecurityEquipmentEntity) {
                myDocument = (MinimumSecurityEquipmentEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = "https://" + "descargas-gomistar" + ".s3.us-east-2.amazonaws.com/" + this.auxiliarClass.extractName(image);

        return endImage;
    }

    public MinimumSecurityEquipmentEntity deleteBoatRegistration(AbstractShip pShip) throws IOException {

        MinimumSecurityEquipmentEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof MinimumSecurityEquipmentEntity) {
                myDocument = (MinimumSecurityEquipmentEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = this.auxiliarClass.extractName(image);

        this.s3Service.deletFile(endImage);

        this.s3Service.deletDownloadFile(endImage);

        return myDocument;

    }
}
